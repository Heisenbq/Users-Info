package ru.kubsu.lab.stand.service;

import ru.kubsu.lab.stand.Sort.SortByLogin;
import ru.kubsu.lab.stand.Sort.SortUsers;
import ru.kubsu.lab.stand.dao.IUserDao;
import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.exception.UserDaoException;
import ru.kubsu.lab.stand.model.SortModel;
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
            System.out.println("Нету его");
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
    public List<UserModel> findUsers(String login, String name, String phone, SortModel sortModel) {

        return userDao.getUserList()
                .stream()
                .filter(
                        userModel -> (login == null || userModel.getLogin().equals(login)) &&
                                (name == null || userModel.getName().equals(name)) &&
                                (phone == null || userModel.getPhone().equals(phone))
                )
//                .sorted(Comparator.comparing(UserModel::getLogin))
                .collect(Collectors.toList());
    }

    @Override
    public void sort(SortUsers sortUsers) {
//        List list=new ArrayList<>(findUsers(null,null,null,null));
//        Collections.sort(list,new SortByLogin());

    }
}

