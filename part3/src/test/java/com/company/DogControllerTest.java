package com.company;

import com.company.entity.Dog;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.when;

/**
 * Created by Irina_Daniel on 3/27/2018.
 */
public class DogControllerTest {

//    @Test
//    public void testGet() {
//        when().post("dog").body()
//        System.out.println(when().get("dog/1")
//                .then().toString());
//    }

    @Test
    public void RegistrationSuccessful() {
        Dog dog = new Dog();
        dog.setName("Mike");
        RestAssured.baseURI = "dog";
        RequestSpecification request = RestAssured.given();

        request.body(writeAsJson(dog));
        Response response = request.post("/dog");

        int statusCode = response.getStatusCode();
        // Assert.assertEquals(statusCode, "201");
        String successCode = response.jsonPath().get("SuccessCode");
        // Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
    }

    private String writeAsJson(Dog dog) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(dog);
        } catch (IOException e) {

        }
        return "";
    }
}
