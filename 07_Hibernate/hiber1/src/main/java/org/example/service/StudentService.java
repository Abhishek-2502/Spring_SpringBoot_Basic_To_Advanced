package org.example.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entities.Student;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class StudentService {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // Method to save a student
    public void saveStudent(Student student) {
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
            System.out.println("Student saved successfully with ID: " + student.getStudentId());
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            System.err.println("Error saving student: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    // Method to retrieve a student by ID
    public Student getStudentById(long studentId) {
        Session session = sessionFactory.openSession();

        try {
            Student student = session.find(Student.class, studentId);
            if (student != null) {
                System.out.println("Student retrieved: " + student);
                return student;
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving student: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    // Method to update a student
    public Student updateStudent(Student student) {
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
            System.out.println("Student updated successfully: " + student);
            return student;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            System.err.println("Error updating student: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    // Method to delete a student by ID
    public Student deleteStudent(long studentId) {
        Session session = sessionFactory.openSession();

        try {
            Transaction transaction = session.beginTransaction();
            Student student = session.find(Student.class, studentId);
            if (student != null) {
                session.remove(student);
                transaction.commit();
                System.out.println("Student deleted successfully: " + student);
                return student;
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            System.err.println("Error deleting student: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }


    /* HQL can be used for more complex queries.
    // DB Independent queries.*/
    // Method to retrieve all students
    public List<Student> getAllStudentsHQL() {
        Session session = sessionFactory.openSession();
        String queryHQL = "FROM Student"; // HQL query to retrieve all students
        List<Student> students = null;

        try {
            Query<Student> query = session.createQuery(queryHQL, Student.class);
            students = query.getResultList();
            System.out.println("All students retrieved: " + students);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving all students: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return students;
    }

    // Method to retrieve students by name
    public Student getStudentsByName(String name) {
        Session session = sessionFactory.openSession();
        String queryHQL = "FROM Student WHERE name = :name"; // HQL query
        Student students = null;
        try {
            Query<Student> query = session.createQuery(queryHQL, Student.class);
            query.setParameter("name", name);
            students = query.uniqueResult();
            System.out.println("Students retrieved by name: " + students);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving students by name: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return students;
    }



    // Criteria API can be used for more complex queries.
    // Method to retrieve students from the same college using Criteria API
    public List<Student> getStudentsByCollege(String college) {
        Session session = sessionFactory.openSession();
        List<Student> students = null;
        try {
            // Create a CriteriaBuilder from the session
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            // Create a CriteriaQuery for Student
            CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
            // Define the root of the query (the entity to be queried)
            Root<Student> root = criteriaQuery.from(Student.class);
            // Specify the selection and the where clause
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("college"), college));
            // Create a query from the criteria query
            Query<Student> query = session.createQuery(criteriaQuery);
            // Execute the query and get the result list
            students = query.getResultList();
            System.out.println("Students retrieved from college " + college + ": " + students);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving students by college: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return students;
    }



    /* Pagination is used to retrieve a subset of results from a larger dataset.
    This is useful for displaying results in pages, especially when dealing with large datasets.*/
    // Method to retrieve all students using Pagination
    public List<Student> getAllStudentsPaginated(int pageNumber, int pageSize) {
        Session session = sessionFactory.openSession();
        List<Student> students = null;
        try {
            // HQL query to retrieve all students
            String queryHQL = "FROM Student";
            Query<Student> query = session.createQuery(queryHQL, Student.class);
            // Set the starting index for pagination
            query.setFirstResult((pageNumber - 1) * pageSize);
            // Set the maximum number of results
            query.setMaxResults(pageSize);
            students = query.getResultList();
            System.out.println("All students retrieved (Paginated): " + students);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error retrieving all students (Paginated): " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return students;
    }

}
