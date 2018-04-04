package com.company;

import com.company.entity.Dog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class DogTestUtils {

    static final UUID UUID_TO_FIND = new UUID(1L, 1L);

    private DogTestUtils() {
    }

    static Date initDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c.getTime();
    }

    static void assertDog(Dog actual, Dog expected) {
        Assert.assertNotEquals(actual, null);
        Assert.assertNotEquals(actual.getId(), null);
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
