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
        assertEqualsDogs(updated, dog);
        Dog actualUpdated = getById(updated.getId()).body().as(Dog.class);
        assertEqualsDogs(updated, actualUpdated);
    }

    @Test
    public void shouldCreate() {
        Dog dog = new Dog("mike", initDate(2017, Calendar.APRIL, 1), 12, 12);
        Dog created = given().body(dog).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post("/").body().as(Dog.class);
        assertEqualCommonParams(created, dog);
        Dog actualCreated = getById(created.getId()).body().as(Dog.class);
        assertEqualCommonParams(created, actualCreated);
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
        return given().body(initTestDog()).accept(ContentType.JSON).contentType(ContentType.JSON)
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
