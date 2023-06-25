package ru.kubsu.lab.stand;

import ru.kubsu.lab.stand.dao.IUserDao;
import ru.kubsu.lab.stand.dao.UserDaoFileStorage;
import ru.kubsu.lab.stand.presentation.ConsoleMaintainer;
import ru.kubsu.lab.stand.service.IUserManager;
import ru.kubsu.lab.stand.service.SimpleUserManager;
import ru.kubsu.lab.stand.service.mail.Sender;

public class UsersInfoApp {

    public static void main(String[] args) {

        System.out.println("Start Application Users Info Manager 1.0.645-7");

        IUserDao userDao = new UserDaoFileStorage();
        IUserManager userManager = new SimpleUserManager(userDao);
        ConsoleMaintainer consoleMaintainer = new ConsoleMaintainer(userManager);
        consoleMaintainer.run();

    }

}