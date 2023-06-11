package ru.kubsu.lab.stand.utils;

import ru.kubsu.lab.stand.model.UserModel;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class UserFilter {

    private final Set<Field> fields;

    private UserFilter() {
        this.fields = new HashSet<>();
    }

    public UserFilter addField(Field field) {
        if (field.getVal() == null) return this;
        fields.add(field);
        return this;
    }

    public Predicate<UserModel> getPredicate() {

        Predicate<UserModel> base = userModel -> true;

        for (Field field : fields) {

            if (field == Field.LOGIN) {
                base = base.and(userModel -> userModel.getLogin() != null && userModel.getLogin().contains(field.getVal()));
            }
            if (field == Field.NAME) {
                base = base.and(userModel -> userModel.getName() != null && userModel.getName().contains(field.getVal()));
            }
            if (field == Field.MIDDLE_NAME) {
                base = base.and(userModel -> userModel.getMiddleName() != null && userModel.getMiddleName().contains(field.getVal()));
            }
            if (field == Field.SURNAME) {
                base = base.and(userModel -> userModel.getSurname() != null && userModel.getSurname().contains(field.getVal()));
            }
            if (field == Field.EMAIL) {
                base = base.and(userModel -> userModel.getEmail() != null && userModel.getEmail().contains(field.getVal()));
            }


        }

        return base;

    }


    public static UserFilter buildDefault() {
        return new UserFilter();
    }


    public boolean isEmpty() {
        return fields.isEmpty();
    }


}
