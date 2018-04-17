package com.company.dao.impl;


import com.company.entity.Dog;
import com.company.exception.DogException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.dao.impl.DogDaoInMemoryImpl.DOG_ALREADY_EXISTS;

public class DogDaoJdbcImpl {
    private static final Logger LOGGER = LogManager.getLogger(DogDaoJdbcImpl.class);

    private DataSource dataSource;

    public DogDaoJdbcImpl() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        this.dataSource = builder.setType(EmbeddedDatabaseType.H2).addScript("classpath:sql/shema.sql").build();
    }

    public Dog create(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        String sql = "insert into book values ( ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            dog.setId(UUID.randomUUID());
            statement.setString(1, dog.getId().toString());
            statement.setString(2, dog.getName());
            statement.setDate(3, Date.valueOf(dog.getBirthDay()));
            statement.setInt(4, dog.getHeight());
            statement.setInt(5, dog.getWeight());
            statement.executeUpdate(sql);

        } catch (SQLException e) {
        }
        return dog;
    }

    public Dog update(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        String sql = "insert into book values ( ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            dog.setId(UUID.randomUUID());
            statement.setString(1, dog.getId().toString());
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
        String sql = "SELECT uuid, name, birthday, weight, height FROM dog where uuid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        String sql = "SELECT uuid, name, birthday, weight, height FROM dog";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        String sql = "delete from book where uuid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dogUuid.toString());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Dog getFromResultSet(ResultSet resultSet) throws SQLException {
        Dog dog = new Dog();
        dog.setId(UUID.fromString(resultSet.getString("uuid")));
        dog.setName(resultSet.getString("name"));
        dog.setBirthDay(resultSet.getDate("birthday").toLocalDate());
        dog.setHeight(resultSet.getInt("weight"));
        dog.setHeight(resultSet.getInt("height"));
        return dog;
    }
}
