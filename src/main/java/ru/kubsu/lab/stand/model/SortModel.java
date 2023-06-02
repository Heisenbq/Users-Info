package ru.kubsu.lab.stand.model;

import java.util.Comparator;

public class SortModel {

    public enum Direction {ASC, DESC}

    public enum Field {LOGIN, NAME, SURNAME, MIDDLE_NAME, EMAIL}


    private Field field;

    private Direction direction;


    public SortModel(Field field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public SortModel() {
        this.field = Field.LOGIN;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Comparator<UserModel> getComparator() {

        Comparator<UserModel> comparator = Comparator.comparing(UserModel::getLogin);

        if (field == Field.NAME) {
            comparator = Comparator.comparing(UserModel::getName);
        } else if (field == Field.SURNAME) {
            comparator = Comparator.comparing(UserModel::getSurname);
        } else if (field == Field.MIDDLE_NAME) {
            comparator = Comparator.comparing(UserModel::getMiddleName);
        } else if (field == Field.EMAIL) {
            comparator = Comparator.comparing(UserModel::getEmail);
        }

        if (direction == Direction.DESC) {
            comparator = comparator.reversed();
        }

        return comparator;

    }

    public static SortModel buildDefault() {
        return new SortModel();
    }


}
