package ru.kubsu.lab.stand.presentation;

import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.model.UserModel;
import ru.kubsu.lab.stand.service.IUserManager;

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



//        UserModel userModel = new UserModel();
//
//        userModel.setLogin("rusik1");
//        userModel.setName("Ruslan1212321");
//        userModel.setPass("123123123");
//        userModel.setPhone("33342324");
//
//
//        userManager.saveUser(userModel);


        List<UserModel> list = userManager.findUsers(null, null,null);
        System.out.println(1);






    }


}
