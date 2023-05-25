package ru.kubsu.lab.stand.service;

import ru.kubsu.lab.stand.dao.IUserDao;
import ru.kubsu.lab.stand.exception.UserAuthException;
import ru.kubsu.lab.stand.exception.UserDaoException;
import ru.kubsu.lab.stand.model.UserModel;

import java.util.Collection;
import java.util.List;
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


    @Override
    public void saveUser(UserModel userModel) {
        try {
            userDao.saveUser(userModel);
        } catch (UserDaoException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Collection<UserModel> findUsers(String login, String name, String phone) {


        return userDao.getUserList()
                .stream()
                .filter(
                        userModel -> (login == null || userModel.getLogin().equals(login)) &&
                                (name == null || userModel.getName().equals(name)) &&
                                (phone == null || userModel.getPhone().equals(phone))
                )
                .collect(Collectors.toList());

    }


}
