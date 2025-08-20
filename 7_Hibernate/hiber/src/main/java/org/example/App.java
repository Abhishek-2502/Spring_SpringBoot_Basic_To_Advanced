package org.example;

import org.example.entities.Student;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
1. Create an entity object (e.g., Student).
2. Load the Hibernate configuration file (hibernate.cfg.xml).
3. Create a SessionFactory from the configuration.
4. Open a session from the SessionFactory.
5. Begin a transaction.
6. Use the session to persist the entity object to the database.
7. Commit the transaction.
8. Close the session.

 */

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        // STEP 1: Create an entity object
        Student student = new Student();
        student.setName("John Doe");
        student.setCollege("Example University");
        student.setPhone("123-456-7890");
        student.setFatherName("Mr. Doe");
        student.setAbout("A brief description about John Doe.");
        student.setActive(true);
        System.out.println("Student object created: " + student);


        // STEP 3: Create a SessionFactory from the Hibernate configuration
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println( "SessionFactory created: " + sessionFactory);


        // STEP 4: Open a session
        System.out.println("Opening session...");
        Session session = sessionFactory.openSession();
        System.out.println("Session opened: " + session.isOpen());


        Transaction transaction=null;
        try {
            // STEP 5: Start a transaction
            transaction = session.beginTransaction();
            // Save the student object to the DB
            session.persist(student);
            // STEP 7: Commit the transaction
            transaction.commit();
            System.out.println("Student saved successfully with ID: " + student.getStudentId());
        } catch (Exception e) {
            if (transaction != null) {
                // Rollback in case of an error
                transaction.rollback();
                System.out.println("Transaction rolled back due to an error.");
            }
            e.printStackTrace();
        } finally {
            // STEP 8: Close the session as it is short-lived
            System.out.println("Closing session...");
            session.close();
        }


    }
}
