package com.company.impl;

import com.company.controller.DogController;
import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.exception.DogException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.impl.DogDaoInMemoryImpl.DOG_ALREADY_EXISTS;

/**
 * Created by Ira on 16.04.2018.
 */
public class DogDaoJdbcImpl implements DogDao {
    private static final Logger LOGGER = LogManager.getLogger(DogDaoJdbcImpl.class);

    private Connection connection;
    private PreparedStatement statement;

    public DogDaoJdbcImpl(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public Dog create(Dog dog) {
        if (dog.getUuid() != null && findById(dog.getUuid()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getUuid()), HttpStatus.CONFLICT);
        }
        try {
            String sql = "insert into book values ( ?, ?, ?, ?, ?)";
            dog.setUuid(UUID.randomUUID());
            statement = connection.prepareStatement(sql);
            statement.setString(1, dog.getUuid().toString());
            statement.setString(2, dog.getName());
            statement.setDate(3, Date.valueOf(dog.getBirthDay()));
            statement.setInt(4, dog.getHeight());
            statement.setInt(5, dog.getWeight());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dog;
    }

    public Dog update(Dog dog) {
        if (dog.getUuid() != null && findById(dog.getUuid()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getUuid()), HttpStatus.CONFLICT);
        }
        try {
            String sql = "insert into book values ( ?, ?, ?, ?, ?)";
            dog.setUuid(UUID.randomUUID());
            statement = connection.prepareStatement(sql);
            statement.setString(1, dog.getUuid().toString());
            statement.setString(2, dog.getName());
            statement.setDate(3, Date.valueOf(dog.getBirthDay()));
            statement.setInt(4, dog.getHeight());
            statement.setInt(5, dog.getWeight());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dog;
    }

    public Dog get(UUID dogUuid) {
        return findById(dogUuid);
    }

    public Dog findById(UUID dogUuid) {
        Dog dog = new Dog();
        try {
            String sql = "SELECT uuid, name, birthday, weight, height FROM dog where uuid = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dogUuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                dog = getFromResultSet(resultSet);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dog;
    }

    public Collection<Dog> get() {
        List<Dog> dogs = new ArrayList<Dog>();
        try {
            String sql = "SELECT uuid, name, birthday, weight, height FROM dog";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Dog dog = getFromResultSet(resultSet);
                dogs.add(dog);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogs;
    }

    public Dog delete(UUID dogUuid) {
        try {
            String sql = "delete from book where uuid = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dogUuid.toString());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Dog getFromResultSet(ResultSet resultSet) throws SQLException {
        Dog dog = new Dog();
        dog.setUuid(UUID.fromString(resultSet.getString("uuid")));
        dog.setName(resultSet.getString("name"));
        dog.setBirthDay(resultSet.getDate("birthday").toLocalDate());
        dog.setHeight(resultSet.getInt("weight"));
        dog.setHeight(resultSet.getInt("height"));
        return dog;
    }

}
