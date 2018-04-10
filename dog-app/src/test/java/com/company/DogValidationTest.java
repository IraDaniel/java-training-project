package com.company;


import com.company.entity.Dog;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.company.DogTestUtils.initDate;


public class DogValidationTest {

    private static Validator validator;

    @BeforeMethod
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testIncorrectBirthDay() {
        Dog dog = new Dog();
        dog.setBirthDay(initDate(2019, 2, 12));
        dog.setHeight(2);
        dog.setWeight(3);
        dog.setName("name");

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "birthDay");
    }

    @Test
    public void testNameShouldBeNotNull() {
        Dog dog = new Dog();
        dog.setBirthDay(initDate(2017, 2, 12));
        dog.setHeight(2);
        dog.setWeight(3);

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "name");
    }

    @Test
    public void testWeightAndHeightShouldBeGreaterThan() {
        Dog dog = new Dog();
        dog.setBirthDay(initDate(2017, 2, 12));
        dog.setHeight(-10);
        dog.setWeight(-20);
        dog.setName("name");


        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 2);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "height");
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "weight");
    }
}
