package com.company.config;

import com.company.beans.Book;
import com.company.beans.Messenger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApplicationConfig {

    @Bean
    public Messenger messenger(){
        return new Messenger();
    }

    @Bean
    @Scope("prototype")
    public Book book(){
        return new Book();
    }
}
