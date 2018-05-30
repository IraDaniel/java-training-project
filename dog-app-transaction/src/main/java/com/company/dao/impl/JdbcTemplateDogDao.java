package com.company.dao.impl;


import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.exception.DogNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.utility.JdbcDogDaoUtils.*;

public class JdbcTemplateDogDao implements DogDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateDogDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID());
        String sql = "insert into dog (id, name, birth_date, height, weight) values ( ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, statement -> {
                    statement.setString(1, dog.getId().toString());
                    statement.setString(2, dog.getName());
                    statement.setDate(3, Date.valueOf(dog.getBirthDay()));
                    statement.setInt(4, dog.getHeight());
                    statement.setInt(5, dog.getWeight());
                }
        );
        return dog;
    }

    public Dog update(Dog dog) {
        String sql = "update dog set name =  ?, birth_date =  ?, height =  ?, weight = ? where id = ?";

        PreparedStatementSetter statementSetter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement statement) throws SQLException {
                statement.setString(1, dog.getName());
                statement.setDate(2, Date.valueOf(dog.getBirthDay()));
                statement.setInt(3, dog.getHeight());
                statement.setInt(4, dog.getWeight());
                statement.setObject(5, dog.getId());
            }
        };
        jdbcTemplate.update(sql, statementSetter);
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
        String sql = "SELECT id, name, birth_date, weight, height FROM dog where id = ?";
        List<Dog> dogs = jdbcTemplate.query(sql, statement -> statement.setObject(1, dogUuid), new DogMapper());
        return CollectionUtils.isEmpty(dogs) ? null : dogs.get(0);
    }

    public Collection<Dog> get() {
        String sql = "SELECT id, name, birth_date, weight, height FROM dog";
        return jdbcTemplate.query(sql, new DogMapper());
    }

    public void delete(UUID dogUuid) {
        String sql = "delete from dog where id = ?";
        jdbcTemplate.update(sql, statement -> statement.setObject(1, dogUuid));
    }

    private static class DogMapper implements RowMapper<Dog> {

        @Override
        public Dog mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dog result = new Dog();
            result.setId((UUID) rs.getObject(DOG_ID));
            result.setName(rs.getString(DOG_NAME));
            result.setBirthDay(rs.getDate(DOG_BIRTH_DATE).toLocalDate());
            result.setHeight(rs.getInt(DOG_HEIGHT));
            result.setWeight(rs.getInt(DOG_WEIGHT));
            return result;
        }

    }
}
