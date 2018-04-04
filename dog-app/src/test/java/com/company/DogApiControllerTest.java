package com.company;


import com.company.entity.Dog;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.UUID;

import static com.company.DogTestUtils.*;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

@ImportResource({"classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
public class DogApiControllerTest {

    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "http://localhost:8080/dog-app/dog";
    }

    @Test
    public void testGet() {
        Response response = get();
        assertResponseStatus(response, HttpStatus.SC_OK);
    }

    @Test
    public void shouldCreateDog() {
        Dog dog = new Dog("mike", initDate(2017, Calendar.APRIL, 1), 12, 12);
        Dog created = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post("/").body().as(Dog.class);
        assertDog(created, dog);
    }

    @Test
    public void testRemove() {
       when().delete("/{id}", UUID.randomUUID()).thenReturn();
    }

    @Test
    public void testGetById() {
        Dog dog = getById(new UUID(1L, 1L)).body().as(Dog.class);
        assertDog(dog, new Dog(new UUID(1L, 1L), "to_find_puppy", initDate(2015, Calendar.JANUARY, 10), 12, 12));
    }

    @Test
    public void shouldThrowExceptionWhenFindDog() {
        UUID randomUuid = UUID.randomUUID();
        Response response = getById(randomUuid);
        assertResponseStatus(response, HttpStatus.SC_NOT_FOUND);
    }

    private static Response getById(UUID uuid){
        return given().accept(ContentType.JSON).get("/{id}", uuid);
    }

    private static Response get(){
        return given().accept(ContentType.JSON).get("/");
    }

    private static void assertResponseStatus(Response response, int expectedStatus){
        response.then().assertThat().statusCode(expectedStatus);
    }
}
