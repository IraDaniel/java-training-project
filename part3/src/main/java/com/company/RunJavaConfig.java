package com.company;


import com.company.beans.Book;
import com.company.beans.Messenger;
import com.company.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RunJavaConfig {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Messenger messenger = (Messenger) context.getBean("messenger");
        messenger.printMessage();

        Book book = (Book) context.getBean("book");
        book.setName("Harry Potter");
        book.print();

    }
}
