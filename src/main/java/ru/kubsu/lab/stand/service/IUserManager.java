package ru.kubsu.lab.stand.service;

import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.model.UserModel;

import java.util.List;

public interface IUserManager {

    UserModel login (String login, String pass) throws UserAuthException;
    List<UserModel> findUsers (String login, String name, String phone);

    void saveUser (UserModel userModel);





}
