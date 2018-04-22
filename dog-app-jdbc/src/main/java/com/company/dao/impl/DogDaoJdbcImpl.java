package com.company.dao.impl;


import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.exception.DogException;
import com.company.exception.DogSqlException;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.dao.impl.DogDaoInMemoryImpl.DOG_ALREADY_EXISTS;

public class DogDaoJdbcImpl implements DogDao {

    private DataSource dataSource;

    public DogDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dog> findAll() {
        List<Dog> dogs = new ArrayList<>();
        String sql = "SELECT id, name, birth_date, weight, height FROM dog";
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Dog dog = getFromResultSet(resultSet);
                dogs.add(dog);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dogs;
    }

    public Dog create(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        dog.setId(UUID.randomUUID());
        String sql = "insert into dog values ( " + dog.getId() + ", %s, %s, %d, %d)";
        sql = String.format(sql, dog.getName(), Date.valueOf(dog.getBirthDay()), dog.getHeight(), dog.getWeight());
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DogSqlException("Could not create dog", e);
        }
        return dog;
    }

    public Dog update(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        String sql = "update dog set name =  %s, birth_date =  %s, height =  %d, weight = %d where id = %s";
        sql = String.format(sql, dog.getName(), Date.valueOf(dog.getBirthDay()), dog.getHeight(), dog.getWeight(), dog.getId());
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not update dog with id [%s]", dog.getId()), e);
        }
        return dog;
    }

    public Dog get(UUID dogUuid) {
        return findById(dogUuid);
    }

    public Dog findById(UUID dogUuid) {
        Dog dog = new Dog();
        String sql = "SELECT id, name, birth_date, weight, height FROM dog where id = " + dogUuid;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.first()) {
                dog = getFromResultSet(resultSet);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not find dog with id [%s]", dogUuid), e);
        }
        return dog;
    }

    public Collection<Dog> get() {
        return findAll();
    }

    public void delete(UUID dogUuid) {
        String sql = "delete from dog where id = " + dogUuid;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not delete dog with id [%s]", dogUuid), e);
        }
    }

    private Dog getFromResultSet(ResultSet resultSet) throws SQLException {
        Dog dog = new Dog();
        dog.setId(UUID.fromString(resultSet.getString("id")));
        dog.setName(resultSet.getString("name"));
        dog.setBirthDay(resultSet.getDate("birth_date").toLocalDate());
        dog.setWeight(resultSet.getInt("weight"));
        dog.setHeight(resultSet.getInt("height"));
        return dog;
    }
}
