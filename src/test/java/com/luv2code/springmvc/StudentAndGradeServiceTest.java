package com.luv2code.springmvc;

import com.luv2code.springmvc.Service.StudentAndGradeService;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.studentDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;


    @Autowired
    private StudentAndGradeService service;

    @Autowired
    private studentDao studentdao;


    @BeforeEach
    public void setupDatabase(){
        jdbc.execute("insert into student(id, firstname, lastname, email_address)"+
                "values (1, 'Eric', 'Roby','eric.roby@luv2code.com')");
    }

    @Test
    public void createStudentService(){

        service.createStudent("chad","Darby","chad.darby@luv2code.com");

        CollegeStudent student = studentdao.findByEmailAddress("chad.darby@luv2code.com");

        Assertions.assertEquals("chad.darby@luv2code.com",student.getEmailAddress(),"find by email");
    }

    @Test
    public void isStudentNullCheck(){
        Assertions.assertTrue(service.checkIfStudetnIsNull(1));

        Assertions.assertFalse(service.checkIfStudetnIsNull(0));
    }

    @Test
    public void deleteStudentService(){
        Optional<CollegeStudent> deletedCollegeStudent = studentdao.findById(1);

        Assertions.assertTrue(deletedCollegeStudent.isPresent(),"Return True");

        service.deleteStudent(1);

        deletedCollegeStudent = studentdao.findById(1);
        Assertions.assertFalse(deletedCollegeStudent.isPresent(),"return false");
    }

    @Sql("/insertData.sql")
    @Test
    public void getGradebookService(){
        Iterable<CollegeStudent>iterableCollegeStudents = service.getGradebook();
        List<CollegeStudent> collegeStudents = new ArrayList<>();
        for(CollegeStudent collegeStudent: iterableCollegeStudents){
            collegeStudents.add(collegeStudent);
        }

        Assertions.assertEquals(5,collegeStudents.size());
    }



    @AfterEach
    public void setupAfterTraction(){
        jdbc.execute("DELETE FROM student");
    }

}
