package ru.kubsu.lab.stand.service;

import ru.kubsu.lab.stand.Sort.SortUsers;
import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.model.SortModel;
import ru.kubsu.lab.stand.model.UserModel;

import java.util.Collection;
import java.util.List;

public interface IUserManager {

    UserModel login (String login, String pass) throws UserAuthException;
    Collection<UserModel> findUsers (String login, String name, String phone, SortModel sortModel);

    boolean updateUser(UserModel userModel);
    boolean addUser (UserModel userModel);

    boolean deleteUser (UserModel usermodel);

    void sort(SortUsers sortUsers);













}
