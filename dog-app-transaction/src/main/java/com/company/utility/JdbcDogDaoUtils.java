package com.company.utility;


import com.company.entity.Dog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcDogDaoUtils {

    public static final String DOG_DOES_NOT_EXIST = "Dog with id %s does not exist";
    private JdbcDogDaoUtils() {
    }

    public static Dog getFromResultSet(ResultSet resultSet) throws SQLException {
        Dog dog = new Dog();
        dog.setId(UUID.fromString(resultSet.getString("id")));
        dog.setName(resultSet.getString("name"));
        dog.setBirthDay(resultSet.getDate("birth_date").toLocalDate());
        dog.setHeight(resultSet.getInt("height"));
        dog.setWeight(resultSet.getInt("weight"));
        return dog;
    }
}
