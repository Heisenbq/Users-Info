package ru.kubsu.lab.stand.model;

import java.util.Comparator;

public class SortModel {

    public enum Direction {ASC, DESC};

    private String field;

    private Direction direction;



    public SortModel(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public SortModel() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public  <T> Comparator<T> getComparator() {
        return null;
    }

    public static SortModel buildDefault() {
        return new SortModel();
    }


}
