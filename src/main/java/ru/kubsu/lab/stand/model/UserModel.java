package ru.kubsu.lab.stand.model;

import java.util.Objects;

public class UserModel {
    //just information fields about users and getters/setters for fields
    private String login;
    private String pass;
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private String phone;


    public UserModel(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public UserModel() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(login, userModel.login) && Objects.equals(pass, userModel.pass) && Objects.equals(name, userModel.name) && Objects.equals(surname, userModel.surname) && Objects.equals(middleName, userModel.middleName) && Objects.equals(email, userModel.email) && Objects.equals(phone, userModel.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, pass, name, surname, middleName, email, phone);
    }
}
