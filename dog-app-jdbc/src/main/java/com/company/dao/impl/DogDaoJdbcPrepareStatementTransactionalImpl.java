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

import static com.company.dao.impl.DogDaoInMemoryImpl.DOG_ALREADY_EXISTS;
import static com.company.dao.impl.DogDaoInMemoryImpl.DOG_DOES_NOT_EXIST;
import static com.company.utility.JdbcDogDaoUtils.getFromResultSet;


public class DogDaoJdbcPrepareStatementTransactionalImpl implements DogDao{
    private DataSource dataSource;

    public DogDaoJdbcPrepareStatementTransactionalImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Dog create(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        String sql = "insert into dog (id, name, birth_date, height, weight) values ( ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
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
        } catch (SQLException sqlExc) {
            rollBack(connection);
        } finally {
            closeConnection(connection);
        }
        return dog;
    }

    public Dog update(Dog dog) {
        if (dog.getId() != null && findById(dog.getId()) == null) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dog.getId()));
        }
        String sql = "update dog set name =  ?, birth_date =  ?, height =  ?, weight = ? where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        Dog dog = findById(dogUuid);
        if (dog == null) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dogUuid));
        }
        return dog;
    }

    private Dog findById(UUID dogUuid) {
        Dog dog = null;
        String sql = "SELECT id, name, birth_date, weight, height FROM dog where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, dogUuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.first()) {
                    dog = getFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not find dog with id [%s]", dogUuid), e);
        }
        return dog;
    }

    public Collection<Dog> get() {
        List<Dog> dogs = new ArrayList<>();
        String sql = "SELECT id, name, birth_date, weight, height FROM dog";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Dog dog = getFromResultSet(resultSet);
                dogs.add(dog);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dogs;
    }

    public void delete(UUID dogUuid) {
        String sql = "delete from dog where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, dogUuid);
            int modifyColumnCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DogSqlException(String.format("Could not delete dog with id [%s]", dogUuid), e);
        }
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exc) {
                throw new DogSqlException("Problem with close connection", exc);
            }
        }
    }

    private void rollBack(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                throw new DogSqlException("Problem with rollBack changes", exc);
            }
        }
    }
}
