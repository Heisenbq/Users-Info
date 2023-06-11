package ru.kubsu.lab.stand.service;


import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.utils.UserFilter;
import ru.kubsu.lab.stand.utils.UserSort;
import ru.kubsu.lab.stand.model.UserModel;

import java.util.Collection;

public interface IUserManager {

    UserModel login (String login, String pass) throws UserAuthException;
    Collection<UserModel> findUsers (UserFilter userFilter, UserSort userSort);

    boolean updateUser(UserModel userModel);
    boolean addUser (UserModel userModel);

    boolean deleteUser (UserModel usermodel);















}
