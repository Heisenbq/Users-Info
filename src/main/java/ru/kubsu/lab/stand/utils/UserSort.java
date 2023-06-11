package ru.kubsu.lab.stand.utils;

import ru.kubsu.lab.stand.model.UserModel;

import java.util.Comparator;

public class UserSort {

    private final Field field;

    private final Direction direction;


    private UserSort(Field field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    private UserSort() {
        this(Field.LOGIN, Direction.ASC);
    }

    private UserSort(Field field) {
        this(field, Direction.ASC);
    }

    public Field getField() {
        return field;
    }


    public Direction getDirection() {
        return direction;
    }


    public Comparator<UserModel> getComparator() {

        Comparator<UserModel> comparator = Comparator.comparing(UserModel::getLogin);

        if (field == Field.NAME) {
            comparator = Comparator.comparing(UserModel::getName, Comparator.nullsLast(Comparator.naturalOrder()));
        } else if (field == Field.SURNAME) {
            comparator = Comparator.comparing(UserModel::getSurname, Comparator.nullsLast(Comparator.naturalOrder()));
        } else if (field == Field.MIDDLE_NAME) {
            comparator = Comparator.comparing(UserModel::getMiddleName, Comparator.nullsLast(Comparator.naturalOrder()));
        } else if (field == Field.EMAIL) {
            comparator = Comparator.comparing(UserModel::getEmail, Comparator.nullsLast(Comparator.naturalOrder()));
        }

        if (direction == Direction.DESC) {
            comparator = comparator.reversed();
        }

        return comparator;

    }

    public static UserSort buildDefault() {
        return new UserSort();
    }

    public static UserSort build(Field field, Direction direction) {
        return new UserSort(field, direction);
    }


    public static UserSort build(Field field) {
        return new UserSort(field);
    }


}
