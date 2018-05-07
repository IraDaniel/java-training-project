package com.company.dao.impl;


import com.company.dao.DogDao;
import com.company.dao.JdbcConnectionHolder;
import com.company.entity.Dog;
import com.company.exception.DogSqlException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.utility.JdbcDogDaoUtils.getFromResultSet;

public class JdbcDogDao implements DogDao {

    private JdbcConnectionHolder connectionHolder;

    public JdbcDogDao(JdbcConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    public Dog create(Dog dog) {
        String sql = "insert into dog (id, name, birth_date, height, weight) values ( ?, ?, ?, ?, ?)";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            dog.setId(UUID.randomUUID());
            statement.setString(1, dog.getId().toString());
            statement.setString(2, dog.getName());
            statement.setDate(3, Date.valueOf(dog.getBirthDay()));
            statement.setInt(4, dog.getHeight());
            statement.setInt(5, dog.getWeight());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DogSqlException("Could not create dog", e);
        }
        return dog;
    }

    public Dog update(Dog dog) {
        String sql = "update dog set name =  ?, birth_date =  ?, height =  ?, weight = ? where id = ?";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dog.getName());
            statement.setDate(2, Date.valueOf(dog.getBirthDay()));
            statement.setInt(3, dog.getHeight());
            statement.setInt(4, dog.getWeight());
            statement.setObject(5, dog.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not update dog with id [%s]", dog.getId()), e);
        }
        return dog;
    }

    public Dog get(UUID dogUuid) {
        return findById(dogUuid);
    }

    private Dog findById(UUID dogUuid) {
        Dog dog = null;
        String sql = "SELECT id, name, birth_date, weight, height FROM dog where id = ?";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, dogUuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.first()) {
                    dog = getFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Cannot find dog by id: %s", dogUuid), e);
        }

        return dog;
    }

    public Collection<Dog> get() {
        List<Dog> dogs = new ArrayList<>();
        String sql = "SELECT id, name, birth_date, weight, height FROM dog";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Dog dog = getFromResultSet(resultSet);
                dogs.add(dog);
            }
        } catch (SQLException e) {
            throw new DogSqlException("Cannot find dogs", e);
        }

        return dogs;
    }

    public void delete(UUID dogUuid) {
        String sql = "delete from dog where id = ?";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, dogUuid);
            int modifyColumnCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Cannot delete dog with id [%s]", dogUuid), e);
        }
    }

}
