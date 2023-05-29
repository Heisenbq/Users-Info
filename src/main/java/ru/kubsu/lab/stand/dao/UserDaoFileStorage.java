package ru.kubsu.lab.stand.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kubsu.lab.stand.exception.UserDaoException;
import ru.kubsu.lab.stand.model.UserModel;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class UserDaoFileStorage implements IUserDao {


    private final File file;


    public UserDaoFileStorage() {
        System.out.println("Start User DAO File storage");
        file = new File("D:/projects/lab/users-information/users.json");

    }

    @Override
    public UserModel getUser(String login) {
        return getDataFromFile().get(login);
    }


    @Override
    public Collection<UserModel> getUserList() {
        return this.getDataFromFile().values();
    }


    @Override
    public boolean isExistUser(String login) {
        return getDataFromFile().containsKey(login);
    }

    @Override
    public UserModel saveUser(UserModel userModel) throws UserDaoException {

        Map<String, UserModel> userModelMap = getDataFromFile();

        UserModel saveUserModel = userModelMap.get(userModel.getLogin());

        if (saveUserModel == null || !saveUserModel.equals(userModel)) {

            userModelMap.put(userModel.getLogin(), userModel);

            if (!saveDataInFile(userModelMap)) {
                throw new UserDaoException("Ошибка сохранения");
            }

            String message = saveUserModel != null
                    ? String.format("Обновление данных по пользователю - %s", saveUserModel.getLogin())
                    : String.format("Добавлен новый пользователь - %s", userModel.getLogin());

            System.out.println(message);

        } else {
            System.out.println("Changes by user not detected!!!");
        }

        return userModel;

    }


    @Override
    public void deleteUser(UserModel userModel) throws UserDaoException {// пока что только по ключу мапы,т.е. по логину
        Map<String, UserModel> userModelMap = getDataFromFile();

        UserModel deleteUserModel=userModelMap.get(userModel.getLogin());// удаляемый пользак

        if (deleteUserModel==null){
            System.out.println("Такого пользователя не существует.");
        }else{
            userModelMap.remove(deleteUserModel.getLogin());
            if (!saveDataInFile(userModelMap)){
                throw new UserDaoException("Ошибка сохранения");
            }
            System.out.println("Пользователь "+deleteUserModel.getLogin() + " удален.");
        }
    }


    private boolean saveDataInFile(Map<String, UserModel> userModelMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, userModelMap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Map<String, UserModel> getDataFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, UserModel.class);
        try {
            return objectMapper.readValue(file, javaType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new HashMap<>();
        }
    }


}
