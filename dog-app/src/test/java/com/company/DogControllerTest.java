package com.company;

import com.company.entity.Dog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.company.DogTestUtils.asJsonString;
import static com.company.DogTestUtils.assertDog;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(locations = {"classpath:test-config.xml", "classpath:h2-context.xml"})
@WebAppConfiguration
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
    public void shouldGetById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/dog/" + new UUID(1L, 1L))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Dog actual = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Dog.class);
        assertDog(actual, new Dog(new UUID(1L, 1L), "to_find_puppy", LocalDate.of(2013, Calendar.DECEMBER, 10), 12, 12));
    }

    @Test
    public void shouldRemove() throws Exception {
        mockMvc.perform(delete("/dog/{id}", new UUID(2, 2)))
                .andExpect(status().isOk());
        Assert.assertEquals(getById(new UUID(2, 2)).getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldCreate() throws Exception{
        Dog dog = new Dog("mike", LocalDate.of(2017, Calendar.APRIL, 1), 12, 12);
        MvcResult mvcResult = mockMvc.perform(post("/dog").contentType(APPLICATION_JSON).content(asJsonString(dog)))
                .andExpect(status().isOk()).andReturn();
        Dog created = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Dog.class);
        assertDog(created, dog);
        MvcResult mvcFindResult = getById(created.getUuid());
        Assert.assertEquals(mvcFindResult.getResponse().getStatus(), HttpStatus.OK.value());
        Dog found = new ObjectMapper().readValue(mvcFindResult.getResponse().getContentAsString(), Dog.class);
        assertDog(created, found);
    }

    @Test
    public void shouldUpdate() {

    }

    private MvcResult getById(UUID uuid) throws Exception{
        return mockMvc.perform(get("/dog/" + uuid).contentType(APPLICATION_JSON)).andReturn();
    }
}
