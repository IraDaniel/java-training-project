package com.company;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:dispatcher-servlet.xml"})
public class ContextTestConfiguration {
}
