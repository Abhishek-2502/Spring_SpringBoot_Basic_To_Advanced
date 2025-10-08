package org.example.util;


import org.example.entities.Certificate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


// Provides SessionFactory and utility methods for Hibernate
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static{
        try {
            if(sessionFactory == null) {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Certificate.class)  // Same as <mapping class="org.example.entities.Certificate"/> in hibernate.cfg.xml
                        .buildSessionFactory();

            }

        } catch (Exception e) {
            throw new RuntimeException("Error creating SessionFactory"+ e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
