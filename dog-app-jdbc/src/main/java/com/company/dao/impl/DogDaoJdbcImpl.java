package com.company.dao.impl;


import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.exception.DogException;
import com.company.exception.DogNotFoundException;
import com.company.exception.DogSqlException;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.utility.JdbcDogDaoUtils.DOG_ALREADY_EXISTS;
import static com.company.utility.JdbcDogDaoUtils.DOG_DOES_NOT_EXIST;
import static com.company.utility.JdbcDogDaoUtils.getFromResultSet;

public class DogDaoJdbcImpl implements DogDao {

    private DataSource dataSource;

    public DogDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dog> findAll() {
        List<Dog> dogs = new ArrayList<>();
        String sql = "SELECT id, name, birth_date, weight, height FROM dog";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                Dog dog = getFromResultSet(resultSet);
                dogs.add(dog);
            }
        } catch (SQLException e) {
            throw new DogSqlException("Cannot find dogs", e);
        }
        return dogs;
    }

    public Dog create(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        dog.setId(UUID.randomUUID());
        String sql = String.format("insert into dog (id, name, birth_date, height, weight) values ('%s', '%s', '%s', %d, %d)",
                dog.getId().toString(), dog.getName(), Date.valueOf(dog.getBirthDay()), dog.getHeight(), dog.getWeight());
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DogSqlException("Could not create dog", e);
        }
        return dog;
    }

    public Dog update(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) == null) {
            throw new DogException(String.format(DOG_DOES_NOT_EXIST, dog.getId()), HttpStatus.NOT_FOUND);
        }
        String sql = String.format("update dog set name =  '%s', birth_date =  '%s', height =  %d, weight = %d where id = '%s'",
                dog.getName(), Date.valueOf(dog.getBirthDay()), dog.getHeight(), dog.getWeight(), dog.getId());
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not update dog with id [%s]", dog.getId()), e);
        }
        return dog;
    }

    public Dog get(UUID dogUuid) {
        Dog dog = findById(dogUuid);
        if (dog == null) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dogUuid));
        }
        return dog;
    }

    public Dog findById(UUID dogUuid) {
        Dog dog = null;
        String sql = String.format("SELECT id, name, birth_date, weight, height FROM dog where id = '%s'", dogUuid);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.first()) {
                dog = getFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not find dog with id [%s]", dogUuid), e);
        }
        return dog;
    }

    public Collection<Dog> get() {
        return findAll();
    }

    public void delete(UUID dogUuid) {
        String sql = String.format("delete from dog where id = '%s'", dogUuid);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not delete dog with id [%s]", dogUuid), e);
        }
    }

    private String wrapInQuotesIfNecessary(Object param){
        return param != null ? String.format("'%s'", param) : null;
    }
}
