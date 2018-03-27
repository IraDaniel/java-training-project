package com.company;

import com.company.beans.Book;
import com.company.beans.Messenger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Messenger obj = context.getBean("messenger", Messenger.class);
        obj.printMessage();

        Book book = context.getBean("book", Book.class);
        book.setName("Harry Potter");
        book.print();
    }
}
