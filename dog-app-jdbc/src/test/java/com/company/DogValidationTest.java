package com.company;


import com.company.entity.Dog;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.company.DogTestUtils.initRandomDog;

public class DogValidationTest {

    private static Validator validator;

    @BeforeMethod
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void birthdayShouldBeInThePast() {
        Dog dog = initRandomDog();
        dog.setBirthDay(DogTestUtils.initDate(2019, 2, 12));

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "birthDay");
    }

    @Test
    public void nameShouldBeNotNull() {
        Dog dog = initRandomDog();
        dog.setName(null);

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "name");
    }

    @Test
    public void nameShouldNotBeEmpty() {
        Dog dog = initRandomDog();
        dog.setName("");

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "name");
    }

    @Test
    public void nameShouldHasSizeLessThan100() {
        Dog dog = initRandomDog();
        dog.setName(StringUtils.repeat("a", 101));

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "name");
    }

    @Test
    public void weightShouldBeGreaterThanZero() {
        Dog dog = initRandomDog();
        dog.setWeight(-10);

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "weight");
    }

    @Test
    public void heightShouldBeGreaterThanZero() {
        Dog dog = initRandomDog();
        dog.setHeight(-10);

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
        Assert.assertEquals(violations.iterator().next().getPropertyPath().toString(), "height");
    }
}
