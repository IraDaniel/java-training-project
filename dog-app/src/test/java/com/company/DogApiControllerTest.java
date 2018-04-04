package com.company;


import com.company.entity.Dog;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.UUID;

import static com.company.DogTestUtils.UUID_TO_FIND;
import static com.company.DogTestUtils.assertDog;
import static com.company.DogTestUtils.initDate;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

@ImportResource({"classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DogApiControllerTest {

    private static final String URL = "http://localhost:8080/dog-app/dog";

    @Before
    public void init(){

    }

    @Test
    public void testGet() {
        given().accept(ContentType.JSON).get(URL).then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldCreateDog() {
        Dog dog = new Dog("mike", initDate(2017, Calendar.APRIL, 1), 12, 12);
        Dog created = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(URL).body().as(Dog.class);
        assertDog(created, dog);

      //  when().get(URL, created.getId()).thenReturn().getBody().as(Dog.class);
    }

    @Test
    public void testRemove() {
       when().delete(URL, UUID.randomUUID()).thenReturn();
    }

    @Test
    public void testGetById() {
        Dog dog = given().accept(ContentType.JSON).get(URL + "/{id}", UUID_TO_FIND).body().as(Dog.class);
        assertDog(dog, new Dog(new UUID(1L, 1L), "to_find_puppy", initDate(2015, Calendar.JANUARY, 10), 12, 12));
    }

    @Test
    public void shouldThrowExceptionWhenFindDog() {
        UUID randomUuid = UUID.randomUUID();
        given().accept(ContentType.JSON).get(URL + "/{id}", randomUuid).then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        //        when().get(URL, randomUuid).then().assertThat()
//                .statusCode(HttpStatus.SC_NOT_FOUND)
//                .contentType(ContentType.JSON);
    }
}
