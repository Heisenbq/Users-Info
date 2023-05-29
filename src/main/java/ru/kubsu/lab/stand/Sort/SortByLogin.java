package ru.kubsu.lab.stand.Sort;

import ru.kubsu.lab.stand.model.UserModel;

import java.util.Comparator;

public class SortByLogin implements Comparator<UserModel> {
    @Override
    public int compare(UserModel userModel1, UserModel userModel2) {
        return userModel1.getLogin().compareTo(userModel2.getLogin());
    }
}
