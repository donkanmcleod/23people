/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.dao;

import com.demos.restapi.demo.dao.model.Course;
import com.demos.restapi.demo.dao.model.Student;
import com.demos.restapi.demo.dao.service.CourseRepository;
import com.demos.restapi.demo.dao.service.StudentRepository;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


/**
 *
 * @author marcello
 */
@DataJpaTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    
    private final String cname = "thecourse";
    private final String name  = "thename";
    private final String lname = "thelname";
    private final String rut   = "19";
    
    private final Integer age = 58;
    
    
    @Autowired
    private CourseRepository courseRepo;
    
    @Autowired
    private StudentRepository studentRepo;


    @Test
    @Order(3)
    @Rollback
    public void testStudentCreate() {

        Course newCourse = new Course(this.cname);
        
        Course savedCourse = this.courseRepo.save(newCourse);
        
        assertTrue(savedCourse != null);
        assertTrue(savedCourse.getName().equals(this.cname));
        
        Student newStd = new Student(this.rut, this.name, this.lname, this.age, savedCourse);
        
        Student savedStd = this.studentRepo.save(newStd);
        
        assertTrue(savedStd != null);
        assertTrue(savedStd.getName().equals(this.name));
    }    


}
