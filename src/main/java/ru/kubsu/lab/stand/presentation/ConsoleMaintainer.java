package ru.kubsu.lab.stand.presentation;

import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.model.*;
import ru.kubsu.lab.stand.service.IUserManager;
import ru.kubsu.lab.stand.utils.Direction;
import ru.kubsu.lab.stand.utils.Field;
import ru.kubsu.lab.stand.utils.UserFilter;
import ru.kubsu.lab.stand.utils.UserSort;

import javax.swing.*;
import java.util.*;
import java.util.function.Function;


public class ConsoleMaintainer {

    private static void writeText(){
        System.out.println("a. Посмотреть список пользователей\n" +
                "b. Добавить пользователя\n" +
                "c. Удалить пользователя\n" +
                "d. изменить пользователя\n" +
                "g. Отсортировать по выбранному полю\n" +
                "h. Выход");
    }

    private final IUserManager userManager;

    public ConsoleMaintainer(IUserManager userManager) {
        this.userManager = userManager;
    }


    public void run() {


//        String login = "her";
//        String pass = "12345";
//
//        try {
//
//            UserModel userModel = userManager.login(login, pass);
//
//            System.out.println("Пользователь "+userModel.toString()+ " найден");
//
//        } catch (UserAuthException e) {
//            System.out.println(e.getMessage());
//        }

/*

        UserModel userModel = new UserModel();

        userModel.setLogin("Akhmed2");
        userModel.setName("Gadjievs");
        userModel.setPass("her");
        userModel.setPhone("33342324");*/
/*        UserFilter userFilter =
                UserFilter.buildDefault();

        UserSort userSort = UserSort.buildDefault();

        Collection<UserModel> userModelCollection = userManager.findUsers(userFilter, userSort);*/

        writeText();
        Scanner input=new Scanner(System.in);
        Collection<UserModel> userModelCollection;
        String action=input.next();
        switch (action){
            case ("a"):{
                System.out.println("Список всех пользователей.\n\n"+"Логин\t\t\tПароль\t\t\tName\t\t\tSurname\t\t\tОтчество\t\t\tНомер тел.\t\t\tEmail\n");
                UserFilter userFilter=UserFilter.buildDefault();
                UserSort userSort=UserSort.buildDefault();
                userModelCollection =userManager.findUsers(userFilter,userSort);
                String tab3="\t\t\t";
                userModelCollection.stream()
                        .map(userModel -> userModel.getLogin()+tab3 +userModel.getPass().hashCode()+tab3 +userModel.getName()+tab3+userModel.getSurname()
                           +tab3+userModel.getMiddleName() +tab3+userModel.getPhone()+tab3+userModel.getEmail())
                        .forEach(System.out::println);
                break;
            }
            case ("b"): {
                System.out.print("Создайте логин для нового аккаунта: ");
                UserModel userModel = new UserModel(input.next(), null);
                while (!userManager.addUser(userModel)) {
                    System.out.print("Введите другой логин: ");
                    userModel.setLogin(input.next());
                }
                System.out.print("Введите пароль для нового пользователя: ");
                userModel.setPass(input.next());
                System.out.print("Введите ФNО через пробел: ");
                userModel.setSurname(input.next());
                userModel.setName(input.next());
                userModel.setMiddleName(input.next());
                System.out.print("Введите номер телефона: ");
                userModel.setPhone(input.next());
                System.out.print("Введите email: ");
                userModel.setEmail(input.next());
                userManager.updateUser(userModel);
                break;
            }
            case ("c"):{
                System.out.println("1-Удалить пользователя по логину");
                System.out.println("2-Удалить пользователя по фамилии-имени");
                System.out.println("3-Удалить пользователя по номеру телефона");
                switch (input.nextInt()){
                    case(1):{
                        System.out.print("Введите логин ");
                        String login=input.next().trim();
                        System.out.print("Введите пароль ");
                        try{
                            UserModel userModel=new UserModel(login,input.next().trim());
                            userManager.login(userModel.getLogin(),userModel.getPass());
                            userManager.deleteUser(userModel);
                        }catch (UserAuthException e){
                            System.out.println( e.getMessage());
                        }
                        break;
                    }
                    case(2):{
                        System.out.print("Введите фамилию имя через пробел: ");
                        UserFilter userFilter=UserFilter.buildDefault();
                        userFilter.addField(Field.SURNAME.byVal(input.next().trim()));
                        userFilter.addField(Field.NAME.byVal(input.next().trim()));
                        UserSort userSort=UserSort.build(Field.LOGIN);
                        userManager.findUsers(userFilter,userSort);
                        ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                        if (array.size()==0) break;
                        System.out.println("Какого именно пользователя вы хотите удалить: ");
                        int i=0;
                        array.stream().
                                map(userModel -> (i+1)+" - "+userModel.getLogin()+" "+userModel.getName()+" "+userModel.getSurname()+" "+userModel.getMiddleName()).forEach(System.out::println);
                        System.out.print("Введите номер пользователя которого хотите удалить: ");
                        int num=input.nextInt();
                        System.out.print("Введите пароль: ");
                        try{
                            UserModel userModel=new UserModel(array.get(num-1).getLogin(),input.next().trim());
                            userManager.login(userModel.getLogin(),userModel.getPass());
                            userManager.deleteUser(userModel);
                        }catch (UserAuthException e){
                            System.out.println( e.getMessage());
                        }
                        break;
                    }
                    case(3):{
                        System.out.print("Введите номер телефона: ");
                        UserFilter userFilter=UserFilter.buildDefault();
                        userFilter.addField(Field.NUMBER.byVal(input.next().trim()));
                        UserSort userSort=UserSort.build(Field.LOGIN);
                        ArrayList<UserModel> array=new ArrayList<>(userManager.findUsers(userFilter,userSort));
                        array.get(0);

                    }

                }
                break;
            }

        }
        System.out.println(1);
    }
}
