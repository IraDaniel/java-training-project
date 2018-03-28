package com.company;


import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class DogApiControllerTest {

    private static final String URL = "http://localhost:8080/dog-app";

    @Test
    public void testGet(){
        given().accept(ContentType.JSON).get(URL + "/dog").then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testPost(){
        Dog dog = new Dog();
        dog.setName("Mike");
        Dog created = given().body(dog).contentType(ContentType.JSON).when().post(URL + "/dog").body().as(Dog.class);
    }
}
