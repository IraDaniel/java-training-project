package com.company;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Configuration
@ImportResource({"classpath:dispatcher-servlet.xml"})
public class ContextTestConfiguration {
}
