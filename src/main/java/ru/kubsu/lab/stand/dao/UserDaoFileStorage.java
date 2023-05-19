package ru.kubsu.lab.stand.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ru.kubsu.lab.stand.exception.UserDaoException;
import ru.kubsu.lab.stand.model.UserModel;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoFileStorage implements IUserDao {


    private final File file;


    public UserDaoFileStorage() {
        System.out.println("Start User DAO File storage");
        file = new File("D:/projects/lab/users-information/users.json");

    }

    @Override
    public UserModel getUser(String login) {

        List<UserModel> userModelList = getDataFromFile();

        return userModelList
                .stream()
                .filter(userModel -> userModel.getLogin().equals(login))
                .findFirst()
                .orElse(null);

    }


    @Override
    public List<UserModel> getUserList() {
        return this.getDataFromFile();
    }

    @Override
    public UserModel saveUser(UserModel userModel) throws UserDaoException {

        List<UserModel> userModelList = getDataFromFile();
        if (userModelList.remove(userModel)) {
            System.out.println("Обновление данных по пользователю " + userModel.getLogin());
        } else {
            System.out.println("Добавлен новый пользователь " + userModel.getLogin());
        }

        userModelList.add(userModel);

        if (!saveDataInFile(userModelList)) {
            throw new UserDaoException("Ошибка сохранения");
        }

        return userModel;

    }


    @Override
    public void deleteUser(UserModel userModel) throws UserDaoException {

    }


    private boolean saveDataInFile(List<UserModel> userModelList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, userModelList);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private List<UserModel> getDataFromFile() {

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, UserModel.class);

        try {
            return objectMapper.readValue(file, collectionType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }


}
