/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.dao;

import com.demos.restapi.demo.dao.model.Course;
import com.demos.restapi.demo.dao.service.CourseRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;


/**
 *
 * @author marcello
 */
@DataJpaTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseRepositoryTest {
    
    @Autowired
    private CourseRepository courseRepo;
    
    private final String testingName1 = "rest api 101";
    private final String testingName2 = "rest api 201";
    
    
    

    @Test
    @Order(2)
    @Rollback
    public void testCourseCreate() {

        Course newCourse = new Course(testingName1);
        
        Course savedCourse = this.courseRepo.save(newCourse);
        
        assertTrue(savedCourse != null);
        assertTrue(savedCourse.getName().equals(testingName1));
    }    

    @Test
    @Order(3)
    @Rollback
    public void testCourseUpdate() {

        Course newCourse = new Course(testingName1);
        
        Course savedCourse = this.courseRepo.save(newCourse);
        
        assertTrue(savedCourse != null);
        assertTrue(savedCourse.getName().equals(testingName1));
        
        
        savedCourse.setName(testingName2);
        
        Course savedCourse2 = this.courseRepo.save(savedCourse);
        
        assertTrue(savedCourse2 != null);
        assertTrue(savedCourse2.getName().equals(testingName2));
    }    

    @Test
    @Order(4)
    @Rollback
    public void testCourseGetById() {

        Course newCourse = new Course(testingName1);
        
        Course savedCourse = this.courseRepo.save(newCourse);
        
        assertTrue(savedCourse != null);
        assertTrue(savedCourse.getName().equals(testingName1));
        
        
        Optional<Course> result = this.courseRepo.findById(savedCourse.getId());
        
        assertTrue(result.isPresent());
        
        Course savedCourse2 = result.get();
        
        assertTrue(savedCourse2.getName().equals(testingName1));
    }    

    @Test
    @Order(5)
    @Rollback
    public void testCourseGetByName() {

        Course newCourse = new Course(testingName1);
        
        Course savedCourse = this.courseRepo.save(newCourse);
        
        assertTrue(savedCourse != null);
        
        Course course = this.courseRepo.findByName(testingName1);
        
        assertTrue(course != null);
        assertTrue(course.getName().equals(testingName1));
    }    

    @Test
    @Order(6)
    @Rollback
    public void testCoursePageable() {

        try {
                for (int i = 0; i < 100; i++) {
                
                    String iName = testingName1 + "_" + i;
                    
                    Course newCourse = new Course(iName);

                    Course savedCourse = this.courseRepo.save(newCourse);

                    assertTrue(savedCourse != null);
                    assertTrue(savedCourse.getName().equals(iName));
                }
            

                Pageable paging = PageRequest.of(3, 10, Sort.unsorted());

                Page<Course> pagedResult = this.courseRepo.findAll(paging);
         
                List<Course> entities = pagedResult.getContent();

                
                assertTrue( entities.get(0).getName().equals(testingName1 + "_30") );
                assertTrue( entities.get(9).getName().equals(testingName1 + "_39") );
                
            
                paging = PageRequest.of(30, 10, Sort.unsorted());

                pagedResult = this.courseRepo.findAll(paging);
         
                entities = pagedResult.getContent();

                
                assertTrue( entities.isEmpty() );

        } catch (Exception ex) { 
        
            fail("Exception reached: " + ex);
        }
    }    

}
