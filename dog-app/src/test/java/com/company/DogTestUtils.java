package com.company;

import com.company.entity.Dog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class DogTestUtils {

    private DogTestUtils() {
    }

    static void assertDog(Dog actual, Dog expected) {
        Assert.assertNotEquals(actual, null);
        Assert.assertNotEquals(actual.getUuid(), null);
        Assert.assertEquals(actual.getBirthDay(), expected.getBirthDay());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getWeight(), expected.getWeight());
        Assert.assertEquals(actual.getHeight(), expected.getHeight());
    }

    static String asJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
