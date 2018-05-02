package com.company.dao;

import com.company.DogTestUtils;
import com.company.entity.Dog;
import com.company.exception.DogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.company.DogTestUtils.assertEqualCommonParams;
import static com.company.DogTestUtils.assertEqualsDogs;

@ContextConfiguration("classpath:dispatcher-servlet.xml")
@ActiveProfiles("prepared")
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

    private Dog createRandomDog(){
        Dog toCreateDog = DogTestUtils.initRandomDog();
        Dog created = dogDao.create(toCreateDog);
        Assert.assertNotEquals(created, null);
        Assert.assertNotEquals(created.getId(), null);
        assertEqualCommonParams(toCreateDog, created);
        return created;
    }
}
