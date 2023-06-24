package ru.kubsu.lab.stand.utils;

public enum Field {
    LOGIN, NAME, SURNAME, MIDDLE_NAME, EMAIL,NUMBER;

    private String val;

    public String getVal() {
        return val;
    }

    public Field byVal(String val) {
        this.val = val;
        return this;
    }



}
