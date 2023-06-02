package ru.kubsu.lab.stand.presentation;

import ru.kubsu.lab.stand.model.SortModel;
import ru.kubsu.lab.stand.model.UserModel;
import ru.kubsu.lab.stand.service.IUserManager;

import java.util.*;
import java.util.function.Function;

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


        UserModel userModel = new UserModel();

        userModel.setLogin("Akhmed2");
        userModel.setName("Gadjievs");
        userModel.setPass("her");
        userModel.setPhone("33342324");


        // userManager.saveUser(userModel);
        //userManager.deleteUser(userModel);

        SortModel sortModel = SortModel.buildDefault();

        sortModel.setField(SortModel.Field.NAME);
        sortModel.setDirection(SortModel.Direction.ASC);

        Collection<UserModel> userModelCollection = userManager.findUsers(null, null, null, sortModel);

        System.out.println(1);


    }

    public void test () {

        Collection<UserModel> userModelCollection = userManager.findUsers(null, null, null, null);

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
