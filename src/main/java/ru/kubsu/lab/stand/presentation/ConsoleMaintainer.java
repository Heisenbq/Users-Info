package ru.kubsu.lab.stand.presentation;

import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.model.*;
import ru.kubsu.lab.stand.service.IUserManager;
import ru.kubsu.lab.stand.service.mail.Sender;
import ru.kubsu.lab.stand.utils.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleMaintainer {

    private static void writeText(){
        System.out.println("a. Посмотреть список пользователей\n" +
                "b. Добавить пользователя\n" +
                "c. Удалить пользователя\n" +
                "d. изменить пользователя\n" +
                "e. Отправить сообщение на email пользователю\n" +
                "f. Найти пользователей по отсортированному выбранному полю\n" +
                "g. Выход");
    }


    private static void changeField(IUserManager userManager,ArrayList<UserModel> arrayList,String s,int numArr){
        numArr=numArr-1;
        Scanner input=new Scanner(System.in);
        switch (s){
            case ("1"):{
                System.out.print("Введите новое имя: ");
                String name=input.next();
                while (StringUtilss.hasDigits(name)){
                    System.out.print("Ввод ФNО только с помощью букв, введите имя заново: ");
                    name=input.next();
                }
                name=name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
                UserModel updateUser=arrayList.get(numArr);
                updateUser.setName(name);
                userManager.updateUser(updateUser);
                break;
            }
            case ("2"):{
                System.out.print("Введите новую фамилию: ");
                String surname=input.next();
                while (StringUtilss.hasDigits(surname)){
                    System.out.print("Ввод ФNО только с помощью букв, введите имя заново: ");
                    surname=input.next();
                }
                surname=surname.substring(0,1).toUpperCase()+surname.substring(1).toLowerCase();
                UserModel updateUser=arrayList.get(numArr);
                updateUser.setSurname(surname);
                userManager.updateUser(updateUser);
                break;
            }
            case ("3"):{
                System.out.print("Введите новое отчество: ");
                String middlename=input.next();
                while (StringUtilss.hasDigits(middlename)){
                    System.out.print("Ввод ФNО только с помощью букв, введите имя заново: ");
                    middlename=input.next();
                }
                middlename=middlename.substring(0,1).toUpperCase()+middlename.substring(1).toLowerCase();
                UserModel updateUser=arrayList.get(numArr);
                updateUser.setMiddleName(middlename);
                userManager.updateUser(updateUser);
                break;
            }
            case ("4"):{
                System.out.print("Введите новый email: ");
                String email=input.next().trim();
                String regex="^[A-Za-z0-9+_.-]+@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher=pattern.matcher(email);
                while(!matcher.matches()){
                    System.out.print("Неверный формат ввода email, введите заново: ");
                    email=input.next();
                    matcher=pattern.matcher(email);
                }
                UserModel updateUser=arrayList.get(numArr);
                updateUser.setEmail(email);
                userManager.updateUser(updateUser);
                break;
            }
            case ("5"):{
                System.out.print("Введите новый номер телефона: ");
                String regex="(^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$)";
                Pattern pattern = Pattern.compile(regex);
                String numPhone=input.next();
                Matcher matcher = pattern.matcher(numPhone);
                while (!matcher.matches()){
                    System.out.print("Неверный формат ввода номера телефона, введите заново: ");
                    numPhone=input.next();
                    matcher=pattern.matcher(numPhone);
                    if (numPhone.replace("-","").replace("(","").replace(")","").replace("+","").length()!=11){
                        matcher=pattern.matcher("0");
                    }
                }
                numPhone=numPhone.replace("-","");
                numPhone=numPhone.replace("(","");
                numPhone=numPhone.replace(")","");
                String correctNumPhone="+7"+"-("+numPhone.substring(1,4)+")-"+numPhone.substring(4,7)+"-"+numPhone.substring(7,9)+"-"+numPhone.substring(9,11);
                UserModel updateUser=arrayList.get(numArr);
                updateUser.setPhone(correctNumPhone);
                userManager.updateUser(updateUser);
            }
            default:{
                System.out.println("Нераспознанная команда!");
            }
        }
    }




    private final IUserManager userManager;

    public ConsoleMaintainer(IUserManager userManager) {
        this.userManager = userManager;
    }


    public void run() {

        writeText();
        Scanner input=new Scanner(System.in);
        Collection<UserModel> userModelCollection;
        String action;
        int exitFromCycle=1;
        while (exitFromCycle==1){
            action=input.next();
            switch (action){
                case ("a"):{
                    System.out.println("Список всех пользователей.\n\n"+"Логин\t\t\tПароль\t\t\tName\t\t\tSurname\t\t\tОтчество\t\t\tНомер тел.\t\t\tEmail\n");
                    UserFilter userFilter=UserFilter.buildDefault();
                    UserSort userSort=UserSort.buildDefault();
                    userModelCollection =userManager.findUsers(userFilter,userSort);
                    String tab3="\t\t\t";
                    userModelCollection.stream()
                            .map(userModel -> userModel.getLogin()+tab3 +userModel.getPass()+tab3 +userModel.getName()+tab3+userModel.getSurname()
                               +tab3+userModel.getMiddleName() +tab3+userModel.getPhone()+tab3+userModel.getEmail())
                            .forEach(System.out::println);
                    System.out.println();
                    writeText();
                    System.out.println();
                    break;
                }
                case ("b"): {
                    System.out.print("Создайте логин для нового аккаунта: ");
                    UserModel userModel = new UserModel(input.next(), null);
                    while (!userManager.addUser(userModel)) {
                        System.out.print("Пользователь с данным логином уже существует введите другой логин: ");
                        userModel.setLogin(input.next());
                    }

                    System.out.print("Введите фамилию: ");
                    userModel.setSurname(input.next());
                    while (StringUtilss.hasDigits(userModel.getSurname())){
                        System.out.print("Ввод ФNО только с помощью букв, введите фамилию заново: ");
                        userModel.setSurname(input.next());
                    }
                    userModel.setSurname(userModel.getSurname().substring(0,1).toUpperCase()+userModel.getSurname().substring(1).toLowerCase());

                    System.out.print("Введите имя: ");
                    userModel.setName(input.next());
                    while (StringUtilss.hasDigits(userModel.getName())){
                        System.out.print("Ввод ФNО только с помощью букв, введите имя заново: ");
                        userModel.setName(input.next());
                    }
                    userModel.setName(userModel.getName().substring(0,1).toUpperCase()+userModel.getName().substring(1).toLowerCase());

                    System.out.print("Введите отчество: ");
                    userModel.setMiddleName(input.next());
                    while (StringUtilss.hasDigits(userModel.getMiddleName())){
                        System.out.print("Ввод ФNО только с помощью букв, введите отчество заново: ");
                        userModel.setMiddleName(input.next());
                    }
                    userModel.setMiddleName(userModel.getMiddleName().substring(0,1).toUpperCase()+userModel.getMiddleName().substring(1).toLowerCase());

                    String regex="(^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$)";
                    Pattern pattern = Pattern.compile(regex);
                    System.out.print("Введите номер телефона, без пробелов: ");
                    String numPhone=input.next();
                    Matcher matcher = pattern.matcher(numPhone);
                    while (!matcher.matches()){
                        System.out.print("Неверный формат ввода номера телефона, введите заново: ");
                        numPhone=input.next();
                        matcher=pattern.matcher(numPhone);

                        if (numPhone.replace("-","").replace("(","").replace(")","").replace("+","").length()!=11){
                            matcher=pattern.matcher("0");
                        }

                    }

                    numPhone=numPhone.replace("-","");
                    numPhone=numPhone.replace("(","");
                    numPhone=numPhone.replace(")","");
                    String correctNumPhone="+7"+"-("+numPhone.substring(1,4)+")-"+numPhone.substring(4,7)+"-"+numPhone.substring(7,9)+"-"+numPhone.substring(9,11);
                    userModel.setPhone(correctNumPhone);

                    regex="^[A-Za-z0-9+_.-]+@(.+)$";
                    pattern = Pattern.compile(regex);
                    System.out.print("Введите email: ");
                    String email=input.next().trim();
                    matcher=pattern.matcher(email);
                    while(!matcher.matches()){
                        System.out.print("Неверный формат ввода email, введите заново: ");
                        email=input.next();
                        matcher=pattern.matcher(email);
                    }
                    userModel.setEmail(email);


                    System.out.print("Введите пароль для нового пользователя без пробелов(пароль должен содержать не менее 8 символов, мимнимум 1 заглавную букву, 1 строчную, 1 цифру и 1 спец. символ: ");
                    regex="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                    pattern=Pattern.compile(regex);
                    String pass=input.next();
                    matcher= pattern.matcher(pass);
                    while (!matcher.matches()){
                        System.out.print("Пароль введен некорректно, введите пароль заново: ");
                        pass=input.next();
                        matcher= pattern.matcher(pass);
                    }
                    userModel.setPass(pass.hashCode());
                    userManager.updateUser(userModel);
                    System.out.println();
                    writeText();
                    System.out.println();
                    break;
                }
                case ("c"):{
                    System.out.println("1-Удалить пользователя по логину");
                    System.out.println("2-Удалить пользователя по фамилии-имени");
                    System.out.println("3-Удалить пользователя по номеру телефона");
                    switch (input.next().trim()){
                        case("1"):{
                            System.out.print("Введите логин: ");
                            String login=input.next().trim();
                            System.out.print("Введите пароль: ");
                            try{
                                UserModel userModel=new UserModel(login,input.next().trim().hashCode());
                                userManager.login(userModel.getLogin(),userModel.getPass());
                                userManager.deleteUser(userModel);
                            }catch (UserAuthException e){
                                System.out.println( e.getMessage());
                            }
                            break;
                        }
                        case("2"):{
                            System.out.print("Введите фамилию имя через пробел: ");
                            UserFilter userFilter=UserFilter.buildDefault();
                            String currentFieldStr=input.next().trim();
                            currentFieldStr=currentFieldStr.substring(0,1).toUpperCase()+currentFieldStr.substring(1).toLowerCase();
                            userFilter.addField(Field.SURNAME.byVal(currentFieldStr));

                            currentFieldStr=input.next().trim();
                            currentFieldStr=currentFieldStr.substring(0,1).toUpperCase()+currentFieldStr.substring(1).toLowerCase();
                            userFilter.addField(Field.NAME.byVal(currentFieldStr));

                            UserSort userSort=UserSort.build(Field.LOGIN);
                            userManager.findUsers(userFilter,userSort);
                            ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                            if (array.size()==0){System.out.println("Пользователь не найден ");break;}
                            System.out.println("Какого именно пользователя вы хотите удалить: ");
                            int i=0;
                            array.stream().
                                    map(userModel -> (i+1)+" - "+userModel.getLogin()+" "+userModel.getName()+" "+userModel.getSurname()+" "+userModel.getMiddleName()).forEach(System.out::println);
                            System.out.print("Введите номер пользователя которого хотите удалить: ");
                            int num=input.nextInt();
                            System.out.print("Введите пароль: ");
                            try{
                                UserModel userModel=new UserModel(array.get(num-1).getLogin(),input.next().trim().hashCode());
                                userManager.login(userModel.getLogin(),userModel.getPass());
                                userManager.deleteUser(userModel);
                            }catch (UserAuthException e){
                                System.out.println( e.getMessage());
                            }
                            break;
                        }
                        case("3"):{
                            System.out.print("Введите номер телефона: ");
                            UserFilter userFilter=UserFilter.buildDefault();
                            userFilter.addField(Field.NUMBER.byVal(input.next().trim()));
                            UserSort userSort=UserSort.build(Field.LOGIN);
                            ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                            if (array.size()==0) {System.out.print("Пользователя с таким номером телефона не существует");break;}
                            System.out.print("Введите пароль от акканута "+ array.get(0).getLogin()+": ");
                            try {
                                UserModel userModel=new UserModel(array.get(0).getLogin(),input.next().trim().hashCode());
                                userManager.login(userModel.getLogin(),userModel.getPass());

                            }catch (UserAuthException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        default:
                            System.out.println("Неопределенный оператор!");
                    }
                    System.out.println();
                    writeText();
                    System.out.println();
                    break;
                }
                case ("d"):{
                    System.out.println("1-Nзменить пользователя по логину");
                    System.out.println("2-Nзменить пользователя по фамилии-имени");
                    System.out.println("3-Nзменить пользователя по номеру телефона");
                    switch (input.next().trim()){
                        case("1"):{
                            System.out.print("Введите логин ");
                            String login=input.next().trim();
                            System.out.print("Введите пароль ");
                            Integer pass=input.next().hashCode();
                            try {
                                userManager.login(login,pass);
                                UserFilter userFilter=UserFilter.buildDefault();
                                UserSort userSort=UserSort.buildDefault();
                                userFilter.addField(Field.LOGIN.byVal(login));
                                ArrayList<UserModel> arrayList=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                                System.out.println("Какое поле вы хотите изменить: ");
                                System.out.print(
                                        "1 - Nмя\n" +
                                                "2 - Фамилия\n" +
                                                "3 - Отчество\n" +
                                                "4 - Email\n" +
                                                "5 - Телефон\n");
                                changeField(userManager,arrayList,input.next(),1);
                            }catch (UserAuthException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case("2"):{
                            System.out.print("Введите фамилию имя через пробел: ");
                            UserFilter userFilter=UserFilter.buildDefault();
                            String currentFieldStr=input.next().trim();
                            currentFieldStr=currentFieldStr.substring(0,1).toUpperCase()+currentFieldStr.substring(1).toLowerCase();
                            userFilter.addField(Field.SURNAME.byVal(currentFieldStr));

                            currentFieldStr=input.next().trim();
                            currentFieldStr=currentFieldStr.substring(0,1).toUpperCase()+currentFieldStr.substring(1).toLowerCase();
                            userFilter.addField(Field.NAME.byVal(currentFieldStr));

                            UserSort userSort=UserSort.build(Field.LOGIN);
                            userManager.findUsers(userFilter,userSort);
                            ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                            if (array.size()==0){System.out.println("Пользователь не найден ");break;}
                            System.out.println("Какого именно пользователя вы хотите изменить: ");
                            int i=0;
                            array.stream().
                                    map(userModel -> (i+1)+" - "+userModel.getLogin()+" "+userModel.getName()+" "+userModel.getSurname()+" "+userModel.getMiddleName()).forEach(System.out::println);
                            System.out.print("Введите номер пользователя которого хотите изменить: ");
                            int num=input.nextInt();
                            System.out.print("Введите пароль: ");
                            try{
                                UserModel userModel=new UserModel(array.get(num-1).getLogin(),input.next().trim().hashCode());
                                userManager.login(userModel.getLogin(),userModel.getPass());
                                System.out.println("Какое поле вы хотите изменить: ");
                                System.out.print(
                                        "1 - Nмя\n" +
                                                "2 - Фамилия\n" +
                                                "3 - Отчество\n" +
                                                "4 - Email\n" +
                                                "5 - Телефон\n");
                                changeField(userManager,array,input.next(),num);
                            }catch (UserAuthException e){
                                System.out.println( e.getMessage());
                            }
                            break;
                        }
                        case("3"):{
                            System.out.print("Введите номер телефона: ");
                            UserFilter userFilter=UserFilter.buildDefault();
                            String numPhone=input.next().trim();
                            numPhone=numPhone.replace("-","").replace("+","").replace("(","").replace(")","");
                            numPhone="+7"+"-("+numPhone.substring(1,4)+")-"+numPhone.substring(4,7)+"-"+numPhone.substring(7,9)+"-"+numPhone.substring(9,11);
                            userFilter.addField(Field.NUMBER.byVal(numPhone));
                            UserSort userSort=UserSort.build(Field.LOGIN);
                            ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                            if (array.size()==0) {System.out.print("Пользователя с таким номером телефона не существует");break;}
                            System.out.print("Введите пароль от акканута "+ array.get(0).getLogin()+": ");
                            try {
                                UserModel userModel=new UserModel(array.get(0).getLogin(),input.next().trim().hashCode());
                                userManager.login(userModel.getLogin(),userModel.getPass());
                                System.out.println("Какое поле вы хотите изменить: ");
                                System.out.print(
                                        "1 - Nмя\n" +
                                        "2 - Фамилия\n" +
                                        "3 - Отчество\n" +
                                        "4 - Email\n" +
                                        "5 - Телефон\n");
                                changeField(userManager,array,input.next(),1);
                            }catch (UserAuthException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        default:
                            System.out.println("Неопределенный оператор!");
                    }
                    System.out.println();
                    writeText();
                    System.out.println();
                    break;
                }
                case ("e"):{
                    System.out.println("1-Отправить по логину");
                    System.out.println("2-Отправить по фамилии-имени");
                    System.out.println("3-Отправить по номеру телефона");
                    switch (input.next().trim()){
                        case ("1"):{
                            System.out.print("Введите логин: ");
                            String login=input.next().trim();
                            ArrayList<UserModel> arrayList=new ArrayList<>(userManager.findUsers(UserFilter.buildDefault().addField(Field.LOGIN.byVal(login)),UserSort.buildDefault()));
                            if (arrayList.size()==0){
                                System.out.println("Такого пользователя не существует!");
                                break;
                            }
                            String email=arrayList.get(0).getEmail();
                            Sender sender=new Sender();
                            System.out.print("Введите текст что хотите отправить (конец предложения после пробела $$)): ");
                            String text="";
                            String check=input.next();
                            while (!check.trim().equals("$$")){
                                text=text+" "+check;
                                check=input.next().trim().trim();
                            }
                            sender.send(email,"fromApplication",text);
                            break;
                        }
                        case ("2"):{
                            System.out.print("Введите фамилию имя через пробел: ");
                            UserFilter userFilter=UserFilter.buildDefault();
                            String currentFieldStr=input.next().trim();
                            currentFieldStr=currentFieldStr.substring(0,1).toUpperCase()+currentFieldStr.substring(1).toLowerCase();
                            userFilter.addField(Field.SURNAME.byVal(currentFieldStr));

                            currentFieldStr=input.next().trim();
                            currentFieldStr=currentFieldStr.substring(0,1).toUpperCase()+currentFieldStr.substring(1).toLowerCase();
                            userFilter.addField(Field.NAME.byVal(currentFieldStr));

                            UserSort userSort=UserSort.buildDefault();
                            userManager.findUsers(userFilter,userSort);
                            ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                            if (array.size()==0){System.out.println("Пользователь не найден ");break;}
                            System.out.println("Какому именно пользователю вы хотите отправить письмо: ");
                            int i=0;
                            array.stream().
                                    map(userModel -> (i+1)+" - "+userModel.getLogin()+" "+userModel.getName()+" "+userModel.getSurname()+" "+userModel.getMiddleName()).forEach(System.out::println);
                            System.out.print("Введите номер пользователя, которому хотите отправить письмо: ");
                            Integer num=input.nextInt();
                            num-=1;
                            Sender sender=new Sender();
                            System.out.print("Введите текст что хотите отправить (конец предложения после пробела $$)): ");
                            String text="";
                            String check=input.next();
                            while (!check.trim().equals("$$")){
                                text=text+" "+check;
                                check=input.next().trim().trim();
                            }
                            sender.send(array.get(num).getEmail(),"fromApplication",text);
                            break;
                        }
                        case ("3"):{
                            System.out.print("Введите номер телефона: ");
                            UserFilter userFilter=UserFilter.buildDefault();
                            String numPhone=input.next().trim();
                            numPhone=numPhone.replace("-","").replace("+","").replace("(","").replace(")","");
                            numPhone="+7"+"-("+numPhone.substring(1,4)+")-"+numPhone.substring(4,7)+"-"+numPhone.substring(7,9)+"-"+numPhone.substring(9,11);
                            userFilter.addField(Field.NUMBER.byVal(numPhone));
                            UserSort userSort=UserSort.build(Field.LOGIN);
                            ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                            if (array.size()==0) {System.out.print("Пользователя с таким номером телефона не существует");break;}
                            System.out.println("Какому именно пользователю вы хотите отправить письмо: ");
                            int i=0;
                            array.stream().
                                    map(userModel -> (i+1)+" - "+userModel.getLogin()+" "+userModel.getName()+" "+userModel.getSurname()+" "+userModel.getMiddleName()).forEach(System.out::println);
                            System.out.print("Введите порядковый номер пользователя, которому хотите отправить письмо: ");
                            Integer num=input.nextInt();
                            num-=1;
                            Sender sender=new Sender();
                            System.out.print("Введите текст что хотите отправить (конец предложения после пробела $$)): ");
                            String text="";
                            String check=input.next();
                            while (!check.trim().equals("$$")){
                                text=text+" "+check;
                                check=input.next().trim().trim();
                            }
                            sender.send(array.get(num).getEmail(),"fromApplication",text);
                            break;
                        }
                    }
                    System.out.println();
                    writeText();
                    System.out.println();
                    break;
                }
                case ("f"):{
                    System.out.println("По какому полю будет производится сортировка: ");
                    System.out.println("1 - Логин\n" +
                            "2 - Nмя\n" +
                            "3 - Фамилия\n" +
                            "4 - Отчество\n" +
                            "5 - Email\n");
                    UserFilter userFilter=UserFilter.buildDefault();
                    Field field=Field.LOGIN;
                    switch (input.next().trim()){
                        case ("1"):{
                            field=Field.LOGIN;
                            break;
                        }
                        case ("2"):{
                            field=Field.NAME;
                            break;
                        }
                        case ("3"):{
                            field=Field.SURNAME;
                            break;
                        }
                        case ("4"):{
                            field=Field.MIDDLE_NAME;
                            break;
                        }
                        case ("5"):{
                            field=Field.EMAIL;
                            break;
                        }
                        default:{
                            System.out.println("Неопределеный оператор, сортировка будет произведена по логину!");
                            field=Field.LOGIN;
                        }
                    }
                    System.out.println("Отсортировать по: ");
                    System.out.print("1 - Возрастанию\n" +
                            "2 - Убыванию\n");
                    Direction direction=Direction.ASC;
                    switch (input.next()){
                        case ("1"):{
                            direction=Direction.ASC;
                            break;
                        }
                        case ("2"):{
                            direction=Direction.DESC;
                            break;
                        }
                        default:{
                            System.out.println("Неопределеный оператор, сортировка будет произведена по возрастанию!");
                            direction=Direction.ASC;
                        }
                    }
                    UserSort userSort=UserSort.build(field,direction);
                    System.out.println("\nПользователи отсортированы: ");
                    String tab3="\t\t\t";
                    System.out.println("Логин\t\t\tПароль\t\t\tName\t\t\tSurname\t\t\tОтчество\t\t\tНомер тел.\t\t\tEmail\n");
                    ArrayList<UserModel> arrayList=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                    arrayList.stream()
                            .map(userModel -> userModel.getLogin()+tab3 +userModel.getPass()+tab3 +userModel.getName()+tab3+userModel.getSurname()
                                    +tab3+userModel.getMiddleName() +tab3+userModel.getPhone()+tab3+userModel.getEmail())
                            .forEach(System.out::println);
                    System.out.println();
                    writeText();
                    System.out.println();
                    break;
                }
                case ("g"):{
                    exitFromCycle=0;
                    break;
                }
                default: {
                    System.out.println("Не является управляющей командой, введите нужную команду");
                    System.out.println();
                    writeText();
                    System.out.println();
                }
            }
       }
    }
}
