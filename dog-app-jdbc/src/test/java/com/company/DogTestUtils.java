package com.company;

import com.company.entity.Dog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;

import java.time.LocalDate;


public class DogTestUtils {

    private DogTestUtils() {
    }


    static Dog initRandomDog() {
        Dog dog = new Dog();
        dog.setName(RandomStringUtils.random(4));
        dog.setBirthDay(initDate(2017, 2, 12));
        dog.setHeight(RandomUtils.nextInt(1, 20));
        dog.setWeight(RandomUtils.nextInt(1, 20));
        return dog;
    }

    static LocalDate initDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    static void assertEqualCommonParams(Dog actual, Dog expected) {
        Assert.assertNotEquals(actual, null);
        Assert.assertNotEquals(actual.getId(), null);
        Assert.assertEquals(actual.getBirthDay(), expected.getBirthDay());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getWeight(), expected.getWeight());
        Assert.assertEquals(actual.getHeight(), expected.getHeight());
    }

    static void assertEqualsDogs(Dog actual, Dog expected) {
        Assert.assertNotEquals(actual, null);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getBirthDay(), expected.getBirthDay());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getWeight(), expected.getWeight());
        Assert.assertEquals(actual.getHeight(), expected.getHeight());
    }

    static String asJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;
    }
}
