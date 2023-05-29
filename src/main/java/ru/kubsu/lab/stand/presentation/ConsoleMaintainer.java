package ru.kubsu.lab.stand.presentation;

import ru.kubsu.lab.stand.Sort.SortByLogin;
import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.model.SortModel;
import ru.kubsu.lab.stand.model.UserModel;
import ru.kubsu.lab.stand.service.IUserManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

        userModel.setLogin("Aaa");
        userModel.setName("Gadjievs");
        userModel.setPass("her");
        userModel.setPhone("33342324");


        // userManager.saveUser(userModel);
        //userManager.deleteUser(userModel);
        userManager.addUser(userModel);
        SortModel sortModel = SortModel.buildDefault();

        sortModel.setField("login");
        sortModel.setDirection(SortModel.Direction.ASC);

        Collection<UserModel> list = userManager.findUsers(null, null,null, sortModel);
        System.out.println(new ArrayList<>(list).get(0).getLogin());







    }


}
