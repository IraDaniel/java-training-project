package com.company.dao;

import com.company.DogTestUtils;
import com.company.entity.Dog;
import com.company.exception.DogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.*;
import org.testng.*;
import org.testng.annotations.*;

import java.util.*;

import static com.company.DogTestUtils.*;

@ContextConfiguration("classpath:dao-context.xml")
@ActiveProfiles("hibernate")
public class DogDaoJdbcTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private HibernateDogDao dogDao;
    @Autowired
    private HouseDao houseDao;

    @Test
    public void shouldCreate() {
        Dog toCreateDog = DogTestUtils.initRandomDog();
        Dog created = dogDao.create(toCreateDog);
        dogDao.flushAndClear();
        Assert.assertNotEquals(created, null);
        Assert.assertNotEquals(created.getId(), null);
        assertEqualCommonParams(created, toCreateDog);
        Dog found = dogDao.get(created.getId());
        assertEqualsDogs(found, created);
    }

    @Test
    public void shouldDelete() {
        Dog created = createRandomDog();
        dogDao.delete(created.getId());

        Assert.assertThrows(DogNotFoundException.class, () -> dogDao.get(created.getId()));
    }

    @Test
    public void shouldUpdate() {
        Dog randomDog = createRandomDog();
        randomDog.setName("New name");
        Dog updated = dogDao.update(randomDog);
        Dog actualUpdated = dogDao.get(updated.getId());
        assertEqualsDogs(actualUpdated, updated);
    }

    @Test
    public void shouldReturnAllDogs() {
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
    public void testToCheckPreparedStatementCache() {
        int numOfCreatedDogs = 5;
        List<UUID> created = new ArrayList<>(numOfCreatedDogs);
        for (int i = 0; i < numOfCreatedDogs; i++) {
            created.add(createRandomDog().getId());
        }
        for (int i = 0; i < 100; i++) {
            dogDao.get(created.get(0));
        }
        for (UUID uuid : created) {
            Dog dog = dogDao.get(uuid);
            System.out.println(dog.getName());
        }

    }

    private Dog createRandomDog() {
        Dog toCreateDog = DogTestUtils.initRandomDog();
        Dog created = dogDao.create(toCreateDog);
        Assert.assertNotEquals(created, null);
        Assert.assertNotEquals(created.getId(), null);
        assertEqualCommonParams(toCreateDog, created);
        dogDao.flushAndClear();
        return created;
    }
}
