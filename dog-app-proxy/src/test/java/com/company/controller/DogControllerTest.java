package com.company.controller;

import com.company.DogTestUtils;
import com.company.TestConfiguration;
import com.company.entity.Dog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:app-context.xml"})
@ActiveProfiles("default")
public class DogControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void shouldReturnAllDogs() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/dog")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<?> actual = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldRemove() throws Exception {
        Dog dog = createRandomDog();
        mockMvc.perform(delete("/dog/{id}", dog.getId())).andExpect(status().isOk());
        assertResponseStatus(getById(dog.getId()), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldCreate() throws Exception {
        Dog toCreateDog = DogTestUtils.initRandomDog();

        MockHttpServletResponse mvcResponse = create(toCreateDog);
        assertResponseStatus(mvcResponse, HttpStatus.OK);
        Dog created = convert(mvcResponse.getContentAsString());
        DogTestUtils.assertEqualCommonParams(created, toCreateDog);

        MockHttpServletResponse foundDogResponse = getById(created.getId());
        assertResponseStatus(foundDogResponse, HttpStatus.OK);
        Dog found = convert(foundDogResponse.getContentAsString());
        DogTestUtils.assertEqualCommonParams(created, found);
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateDogWhichNotExist() throws Exception {
        Dog toUpdateDog = DogTestUtils.initRandomDog();
        toUpdateDog.setId(UUID.randomUUID());
        mockMvc.perform(put("/dog").contentType(APPLICATION_JSON)
                .content(DogTestUtils.asJsonString(toUpdateDog)))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldUpdate() throws Exception {
        Dog dog = createRandomDog();
        dog.setName("Test update name");
        MockHttpServletResponse response = mockMvc.perform(put("/dog").contentType(APPLICATION_JSON)
                .content(DogTestUtils.asJsonString(dog)))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Dog updated = convert(response.getContentAsString());
        DogTestUtils.assertEqualCommonParams(updated, dog);
        MockHttpServletResponse foundDogResponse = getById(updated.getId());
        assertResponseStatus(foundDogResponse, HttpStatus.OK);
        Dog found = convert(foundDogResponse.getContentAsString());
        DogTestUtils.assertEqualCommonParams(updated, found);
    }

    private MockHttpServletResponse getById(UUID uuid) throws Exception {
        return mockMvc.perform(get("/dog/" + uuid).contentType(APPLICATION_JSON))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse create(Dog dog) throws Exception {
        return mockMvc.perform(post("/dog").contentType(APPLICATION_JSON)
                .content(DogTestUtils.asJsonString(dog)))
                .andReturn().getResponse();
    }

    private static Dog convert(String dogInString) throws Exception {
        return new ObjectMapper().readValue(dogInString, Dog.class);
    }

    private static void assertResponseStatus(MockHttpServletResponse response, HttpStatus expectedStatus) {
        Assert.assertEquals(response.getStatus(), expectedStatus.value());
    }

    private Dog createRandomDog() throws Exception {
        Dog toCreateDog = DogTestUtils.initRandomDog();
        MockHttpServletResponse mvcResponse = create(toCreateDog);
        assertResponseStatus(mvcResponse, HttpStatus.OK);
        return convert(mvcResponse.getContentAsString());
    }

}
