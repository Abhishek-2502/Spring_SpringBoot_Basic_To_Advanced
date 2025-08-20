package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    public static void main( String[] args )
    {
//       Dev obj = new Dev();
//       obj.build();



        System.out.println("------------------Initializing Objects------------------");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//        User should create resources folder and inside it spring.xml.
//        This file container the config for files whose object user wants.
//        System.out.println("\n------------------Dev------------------");
//        Dev obj = (Dev) context.getBean("dev");
//        obj.build();




        System.out.println("\n------------------Dev1------------------");
        Dev1 obj1 = (Dev1) context.getBean("dev1");
        System.out.println(obj1.age);
        System.out.println(obj1.age1);

        obj1.age2 = 18;
        System.out.println(obj1.age2);

        System.out.println(obj1.getAge3());
        obj1.setAge3(28);
        System.out.println(obj1.getAge3());

        // Dependency Injection through spring.xml (Setter Injection)
        System.out.println(obj1.getAge4());

//        obj1.build();   //Will through NullPointerException


        System.out.println("\n------------------Dev2------------------");
        Dev2 obj2 = (Dev2) context.getBean("dev2");
        // Dependency Injection through spring.xml (Constructor Injection)
        System.out.println(obj2.getAge());

        obj2.build();


    }
}
