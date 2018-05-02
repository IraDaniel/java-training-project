package com.company.controller;


import com.company.DogTestUtils;
import com.company.entity.Dog;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.company.DogTestUtils.initRandomDog;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

@ImportResource({"classpath:dispatcher-servlet.xml"})
@ActiveProfiles("default")
@WebAppConfiguration
public class DogApiControllerTest {

    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "http://localhost:8080/dog-app/dog";
    }

    @Test
    public void shouldReturnAllDogs() {
        Response response = get();
        assertResponseStatus(response, HttpStatus.SC_OK);
    }

    @Test
    public void shouldUpdate(){
        Dog dog = createRandomDog();
        dog.setName("Test name update");
        Dog updated = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().put("/").body().as(Dog.class);
        DogTestUtils.assertEqualsDogs(updated, dog);
        Dog actualUpdated = getById(updated.getId()).body().as(Dog.class);
        DogTestUtils.assertEqualsDogs(updated, actualUpdated);
    }

    @Test
    public void shouldCreate() {
        Dog dog = initRandomDog();
        Dog created = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post("/").body().as(Dog.class);
        Assert.assertNotEquals(created, null);
        Assert.assertNotEquals(created.getId(), null);
        DogTestUtils.assertEqualCommonParams(created, dog);
        Dog actualCreated = getById(created.getId()).body().as(Dog.class);
        Assert.assertNotEquals(actualCreated, null);
        Assert.assertNotEquals(actualCreated.getId(), null);
        DogTestUtils.assertEqualsDogs(created, actualCreated);
    }

    @Test
    public void shouldRemove() {
        Dog dog = createRandomDog();
        when().delete("/{id}", dog.getId());
        Response response = getById(dog.getId());
        assertResponseStatus(response, HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void shouldThrowExceptionWhenFindDog() {
        UUID randomUuid = UUID.randomUUID();
        Response response = getById(randomUuid);
        assertResponseStatus(response, HttpStatus.SC_NOT_FOUND);
    }

    private static Dog createRandomDog(){
        return given().body(initRandomDog()).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post("/").body().as(Dog.class);
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
