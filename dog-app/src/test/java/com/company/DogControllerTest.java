package com.company;

import com.company.entity.Dog;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.UUID;

import static com.company.DogTestUtils.asJsonString;
import static com.company.DogTestUtils.initDate;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextTestConfiguration.class)
@WebAppConfiguration
public class DogControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .build();
    }

    @Test
    public void shouldReturnAllDogs() throws Exception {
        mockMvc.perform(get("dog")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetById() throws Exception {
        mockMvc.perform(get("dog/" + new UUID(1L, 1L))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRemove() throws Exception {
        mockMvc.perform(delete("dog/{id}", new UUID(1, 1)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreate() throws Exception{
        Dog dog = new Dog("mike", initDate(2017, Calendar.APRIL, 1), 12, 12);
        mockMvc.perform(post("/program")
                .content(asJsonString(dog))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("mike"))
                .andExpect(jsonPath("$.birthDay").exists());
    }

    @Test
    public void shouldUpdate() {

    }

}
