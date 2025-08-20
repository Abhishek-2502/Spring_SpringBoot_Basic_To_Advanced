package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {

//        AUTOWIRING CONCEPT
//        Computer -- Laptop
//                 -- Desktop

        System.out.println("------------------Initializing Objects------------------");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println("\n------------------Dev------------------");
//        Dev obj = (Dev) context.getBean("dev");
//        OR
        Dev obj = context.getBean(Dev.class);
        obj.build();
    }
}
