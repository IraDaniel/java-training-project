package com.company.dao;

import com.company.DogTestUtils;
import com.company.entity.Dog;
import com.company.exception.DogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.company.DogTestUtils.assertEqualCommonParams;
import static com.company.DogTestUtils.assertEqualsDogs;

@ImportResource(locations = {"classpath:dispatcher-servlet.xml", "classpath:dao-context.xml"})
public class DogDaoJdbcTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DogDao dogDao;

    @Test
    public void shouldCreate(){
        Dog toCreateDog = DogTestUtils.initRandomDog();
        Dog created = dogDao.create(toCreateDog);
        Assert.assertNotEquals(created, null);
        Assert.assertNotEquals(created.getId(), null);
        assertEqualCommonParams(created, toCreateDog);
        Dog found = dogDao.get(created.getId());
        assertEqualsDogs(found, created);
    }

    @Test
    public void shouldDelete(){
        Dog created = createRandomDog();
        dogDao.delete(created.getId());

        Assert.assertThrows(DogNotFoundException.class, () -> dogDao.get(created.getId()));
    }

    @Test
    public void shouldUpdate(){
        Dog randomDog = createRandomDog();
        randomDog.setName("New name");
        Dog updated = dogDao.update(randomDog);
        Dog actualUpdated = dogDao.get(updated.getId());
        assertEqualsDogs(actualUpdated, updated);
    }

    @Test
    public void shouldReturnAllDogs(){
        Collection<Dog> dogs = dogDao.get();
        Assert.assertNotNull(dogs);
        Assert.assertFalse(dogs.isEmpty());
    }

    @Test
    public void shouldAllowSQLInjections() {
        Dog dog = createRandomDog();
        String sqlInjectionName = "SQL Injection', -";
        dog.setName(sqlInjectionName);
        Dog update = dogDao.update(dog);
        Dog actualUpdated = dogDao.get(update.getId());
        Assert.assertEquals(actualUpdated.getName(), sqlInjectionName);
        assertEqualsDogs(actualUpdated, update);
    }

    @Test
    public void testToCheckPreparedStatementCache(){
        int numOfCreatedDogs = 5;
        List<UUID> created = new ArrayList<>(numOfCreatedDogs);
        for(int i = 0; i < numOfCreatedDogs; i++){
            created.add(createRandomDog().getId());
        }
        for(int i = 0; i < 100; i++){
            dogDao.get(created.get(0));
        }
        for(UUID uuid: created){
            Dog dog = dogDao.get(uuid);
            System.out.println(dog.getName());
        }

    }

    private Dog createRandomDog(){
        Dog toCreateDog = DogTestUtils.initRandomDog();
        Dog created = dogDao.create(toCreateDog);
        Assert.assertNotEquals(created, null);
        Assert.assertNotEquals(created.getId(), null);
        assertEqualCommonParams(toCreateDog, created);
        return created;
    }
}
