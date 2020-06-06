/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service;

import com.demos.restapi.demo.service.dto.CourseDTO;
import com.demos.restapi.demo.service.exception.EnrollServiceException;
import com.demos.restapi.demo.service.exception.EnrollServiceNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 *
 * @author marcello
 */
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnrollServiceTest {

    @Autowired
    @Qualifier("EnrollingService")
    private EnrollService enrollSrv;
    
    
    @Test
    @Order(1)
    @Rollback
    public void testCreateCourse() {
    
        String testName = "temporal name";
        
        try {
                CourseDTO dto = new CourseDTO();
                dto.setName( testName );
            
                CourseDTO newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
    }
    
    @Test
    @Order(2)
    @Rollback
    public void testGetCourseByName() {
    
        String testName = "temporal name";
        
        try {
                CourseDTO dto = new CourseDTO();
                dto.setName( testName );
            
                CourseDTO newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                CourseDTO dto2 = this.enrollSrv.getCourseByName( testName );
                
                assertTrue(dto2 != null);
                assertTrue(dto2.getName().equals(testName));
                
                
        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
    }
    
    @Test
    @Order(3)
    @Rollback
    public void testUpdateCourse() {
    
        String testName = "temporal name";
        String newName  = "new name";
        
        try {
                CourseDTO dto = new CourseDTO();
                dto.setName( testName );
            
                CourseDTO newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                CourseDTO dto2 = this.enrollSrv.getCourseByName( testName );
                
                assertTrue(dto2 != null);
                assertTrue(dto2.getName().equals(testName));
                
                dto2.setName( newName );

                this.enrollSrv.updateCoruse(dto2.getId(), dto2);

                CourseDTO dto3 = this.enrollSrv.getCourseByName( newName );
                
                assertTrue(dto3 != null);
                assertTrue(dto3.getName().equals(newName));
                
        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
    }
    
    @Test
    @Order(4)
    @Rollback
    public void testDeleteCourse() {
    
        String testName = "temporal name";
        Integer id = 0;
        
        try {
                CourseDTO dto = new CourseDTO();
                dto.setName( testName );
            
                CourseDTO newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                CourseDTO dto2 = this.enrollSrv.getCourseById( newDto.getId() );
                
                assertTrue(dto2 != null);
                assertTrue(dto2.getName().equals(testName));
                
                id = dto2.getId();
                
        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
        
        try {
                this.enrollSrv.deleteCoruse( id );

                this.enrollSrv.getCourseByName( testName );
                
                fail("Should not reach this line...");
                
        } catch (EnrollServiceNotFoundException e) {
            
        } catch (EnrollServiceException ex) {
            
            fail("Exception reached: " + ex.getMessage());
        }
    }
    
    @Test
    @Order(5)
    @Rollback
    public void testGetCourseById() {
    
        String testName = "temporal name";
        Integer id = 0;
        
        try {
                CourseDTO dto = new CourseDTO();
                dto.setName( testName );
            
                CourseDTO newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                CourseDTO dto2 = this.enrollSrv.getCourseById( newDto.getId() );
                
                assertTrue(dto2 != null);
                assertTrue(dto2.getName().equals(testName));
                
                id = dto2.getId();
                
        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
        
        try {
                this.enrollSrv.getCourseById(id);
                
        } catch (EnrollServiceNotFoundException | EnrollServiceException e) {
            
            fail("Exception reached: " + e.getMessage());
        }
    }
    
    @Test
    @Order(6)
    @Rollback
    public void testGetAllCoursesPageable() {
    
        String testingName1 = "temporal name 1";
        
        try {
                for (int i = 0; i < 100; i++) {
                
                    String iName = testingName1 + "_" + i;
                    
                    CourseDTO newCourse = new CourseDTO(iName);

                    CourseDTO savedCourse = this.enrollSrv.createCoruse(newCourse);

                    assertTrue(savedCourse != null);
                    assertTrue(savedCourse.getName().equals(iName));
                }
                

                List<CourseDTO> pagedResult = this.enrollSrv.getAllCoursesByPage(10, 3);
         
                assertTrue( pagedResult.get(0).getName().equals(testingName1 + "_30") );
                assertTrue( pagedResult.get(9).getName().equals(testingName1 + "_39") );
                
            
                pagedResult = this.enrollSrv.getAllCoursesByPage(10, 30);
         
                assertTrue( pagedResult.isEmpty() );

        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
    }
    
    @Test
    @Order(7)
    @Rollback
    public void testGetAllCourses() {
    
        String testName1 = "temporal name 1";
        String testName2 = "temporal name 2";
        String testName3 = "temporal name 3";
        
        try {
                CourseDTO dto = new CourseDTO();
                dto.setName( testName1 );
            
                CourseDTO newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                dto = new CourseDTO();
                dto.setName( testName2 );
            
                newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                dto = new CourseDTO();
                dto.setName( testName3 );
            
                newDto = this.enrollSrv.createCoruse(dto);
                
                assertTrue(newDto != null);
                
                
                List<CourseDTO> list = this.enrollSrv.getAllCourses();

                assertTrue( list.size() == 3 );
                

        } catch (EnrollServiceNotFoundException | EnrollServiceException ex) {
            
            Logger.getLogger(EnrollServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex.getMessage());
        }
    }

}
