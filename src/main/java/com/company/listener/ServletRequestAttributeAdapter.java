package com.company.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;


public class ServletRequestAttributeAdapter implements ServletRequestAttributeListener {

    public void attributeAdded(ServletRequestAttributeEvent var1){
        System.out.println("attributeAdded");
    }

    public void attributeRemoved(ServletRequestAttributeEvent var1){
        System.out.println("attributeRemoved");
    }

    public void attributeReplaced(ServletRequestAttributeEvent var1){
        System.out.println("attributeReplaced");
    }
}
