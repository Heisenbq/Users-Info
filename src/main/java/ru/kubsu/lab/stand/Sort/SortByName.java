package ru.kubsu.lab.stand.Sort;

import ru.kubsu.lab.stand.model.UserModel;

import java.util.Comparator;

public class SortByName implements Comparator<UserModel> {
    @Override
    public int compare(UserModel userModel1, UserModel userModel2) {
        try {
            return userModel1.getName().compareTo(userModel2.getName());
        } catch( NullPointerException e){
            System.out.println("” пользовател€ c логином "+userModel1.getLogin() +" им€ не известно!");
            return -1;
        }
    }
}
