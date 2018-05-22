package com.company;


import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:app-context.xml"})
@ActiveProfiles("default")
public class TestConfiguration extends AbstractTestNGSpringContextTests {
}
