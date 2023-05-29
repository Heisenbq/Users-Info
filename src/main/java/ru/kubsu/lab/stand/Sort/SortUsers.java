package ru.kubsu.lab.stand.Sort;

public class SortUsers {
    public enum Direction{ASC,DESC}
    public enum Field{firstName,middleName,lastName,login,email}

    private Direction direction;
    private Field field;

    public  SortUsers(Direction direction,Field field){
        this.direction=direction;
        this.field=field;
    }
    public SortUsers() {
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
