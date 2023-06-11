package ru.kubsu.lab.stand.presentation;

import ru.kubsu.lab.stand.model.*;
import ru.kubsu.lab.stand.service.IUserManager;
import ru.kubsu.lab.stand.utils.Direction;
import ru.kubsu.lab.stand.utils.Field;
import ru.kubsu.lab.stand.utils.UserFilter;
import ru.kubsu.lab.stand.utils.UserSort;

import java.util.*;


public class ConsoleMaintainer {


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
        userModel.setPhone("33342324");
*/


        // userManager.saveUser(userModel);
        //userManager.deleteUser(userModel);

        UserFilter userFilter =
                UserFilter.buildDefault()
                        .addField(Field.NAME.byVal(""))
                        .addField(Field.LOGIN.byVal("Akhmed"));

        UserSort userSort = UserSort.build(Field.EMAIL, Direction.DESC);

        Collection<UserModel> userModelCollection = userManager.findUsers(userFilter, userSort);

        System.out.println(1);

    }

    public void test() {

        Collection<UserModel> userModelCollection = userManager.findUsers(null, null);

        List<UserModel> userModelList = new ArrayList<>(userModelCollection);

/*        Comparator<UserModel> comparator = new Comparator<UserModel>() {
            @Override
            public int compare(UserModel o1, UserModel o2) {
                return o1.getLogin().compareTo(o2.getLogin());
            }
        };
        */

        Comparator<UserModel> userModelComparator1 =
                (o1, o2) -> o1.getLogin().compareTo(o2.getLogin());


//        Function<UserModel, String> fi = new Function<UserModel, String>() {
//            @Override
//            public String apply(UserModel userModel) {
//                return userModel.getLogin();
//            }
//        };


        Comparator<UserModel> userModelComparator2 =
                Comparator.comparing(UserModel::getLogin);


        userModelList.sort(userModelComparator2);


        System.out.println(1);

    }


}
