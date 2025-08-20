package org.example;

import org.example.entities.Student;
import org.example.service.StudentService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    StudentService studentService = new StudentService();

    @Test
    public void getStudentByIdTest() {
        long studentId = 1; // Assuming a student with ID 1 exists
        Student student = studentService.getStudentById(studentId);
        System.out.println("Student Name: "+student.getName());
        System.out.println("Student Certificates: "+student.getCertificates().size());
    }

    @Test
    public void getStudentByCollegeTest(){
        String collegeName = "Example University"; // Assuming this college exists
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getStudentsByCollege(collegeName);
        System.out.println("Students from " + collegeName + ": " + students.size());

    }

    @Test
    public void getAllStudentsPaginatedTest(){
        int pageNumber = 1; // First page
        int pageSize = 1; // Number of students per page
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudentsPaginated(pageNumber, pageSize);
        System.out.println("Total Students: " + students.size());
    }

}
