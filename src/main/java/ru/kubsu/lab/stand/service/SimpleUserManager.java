package ru.kubsu.lab.stand.service;


import ru.kubsu.lab.stand.dao.IUserDao;
import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.exception.UserDaoException;
import ru.kubsu.lab.stand.utils.UserFilter;
import ru.kubsu.lab.stand.utils.UserSort;
import ru.kubsu.lab.stand.model.UserModel;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleUserManager implements IUserManager {

    private final IUserDao userDao;

    public SimpleUserManager(IUserDao userDao) {
        System.out.println("Start Simple User Manager");
        this.userDao = userDao;
    }

    @Override
    public UserModel login(String login, String pass) throws UserAuthException {

        UserModel userModel = userDao.getUser(login);

        if (userModel == null) {
            throw new UserAuthException("Пользователь не найден.");
        }

        if (userModel.getPass().equals(pass)) {
            return userModel;
        } else {
            throw new UserAuthException("Неверный пароль.");
        }

    }

//
//    @Override
//    public void saveUser(UserModel userModel) {
//        try {
//            userDao.saveUser(userModel);
//        } catch (UserDaoException e) {
//            System.out.println(e.getMessage());
//        }
//    }


    @Override
    public boolean updateUser(UserModel userModel) {

        if (!userDao.isExistUser(userModel.getLogin())) {
            System.out.println("Пользователя с логином " + userModel.getLogin() + " уже существует!");
            return false;
        }

        try {
            userDao.saveUser(userModel);
            return true;
        } catch (UserDaoException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


    @Override
    public boolean addUser(UserModel userModel) {
        if (userDao.isExistUser(userModel.getLogin())) {
            System.out.println("Пользователь с логином " + userModel.getLogin() + " уже существует.");
            return false;
        }
        try {
            userDao.saveUser(userModel);
            return true;
        } catch (UserDaoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    @Override
    public boolean deleteUser(UserModel usermodel) {
        try {
            userDao.deleteUser(usermodel);
            return true;
        } catch (UserDaoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Collection<UserModel> findUsers(UserFilter userFilter, UserSort userSort) {


        return userDao.getUserList()
                .stream()
                .filter(userFilter.getPredicate())
                .sorted(userSort.getComparator())
                .collect(Collectors.toList());

    }

}

