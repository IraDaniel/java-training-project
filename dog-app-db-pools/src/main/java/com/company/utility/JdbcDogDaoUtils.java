package com.company.utility;


import com.company.entity.Dog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcDogDaoUtils {

    public static final String DOG_DOES_NOT_EXIST = "Dog with id %s does not exist";
    public static final String DOG_ALREADY_EXISTS = "Dog with id %s already exists";
    public static final String DOG_ID = "id";
    public static final String DOG_NAME = "name";
    public static final String DOG_BIRTH_DATE = "birth_date";
    public static final String DOG_HEIGHT = "height";
    public static final String DOG_WEIGHT = "weight";

    private JdbcDogDaoUtils() {
    }

    public static Dog getFromResultSet(ResultSet resultSet) throws SQLException {
        Dog dog = new Dog();
        dog.setId(UUID.fromString(resultSet.getString(DOG_ID)));
        dog.setName(resultSet.getString(DOG_NAME));
        dog.setBirthDay(resultSet.getDate(DOG_BIRTH_DATE).toLocalDate());
        dog.setHeight(resultSet.getInt(DOG_HEIGHT));
        dog.setWeight(resultSet.getInt(DOG_WEIGHT));
        return dog;
    }
}
