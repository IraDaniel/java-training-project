package com.company;

import com.company.beans.Book;
import com.company.beans.Messenger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Ira on 21.03.2018.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Messenger obj = (Messenger) context.getBean("messenger");
        obj.printMessage();

        Book book = (Book) context.getBean("book");
        book.setName("Harry Potter");
        book.print();
    }
}
