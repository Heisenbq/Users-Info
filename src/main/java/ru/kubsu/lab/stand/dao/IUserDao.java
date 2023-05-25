package ru.kubsu.lab.stand.dao;

import ru.kubsu.lab.stand.exception.UserDaoException;
import ru.kubsu.lab.stand.model.UserModel;

import java.util.Collection;
import java.util.List;

public interface IUserDao {


    UserModel getUser(String login);

    Collection<UserModel> getUserList();

    UserModel saveUser(UserModel userModel) throws UserDaoException;

    void deleteUser(UserModel userModel) throws UserDaoException;


    private void tet () {

    }

}
