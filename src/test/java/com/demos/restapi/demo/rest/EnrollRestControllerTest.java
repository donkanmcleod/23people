/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.rest;

import com.demos.restapi.demo.DemoApplication;
import com.demos.restapi.demo.TestingHelpers;
import com.demos.restapi.demo.jwt.service.TokenService;
import com.demos.restapi.demo.service.dto.CourseDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author marcello
 */
@Transactional
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)
public class EnrollRestControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    @Qualifier("JWTTokenService")
    private TokenService tokenService; 
    
    
    private static CourseDTO courseDto;
    
    private static final String testingName1 = "testing name 1";
    private static final String testingName2 = "testing name 2";
    
    private String token;
            
    
    
    
    @BeforeAll
    public void Setup() {
        
        token = tokenService.generateToken();
    }
    

    @Test
    @Commit
    @Order(1)
    public void courseCreateCourse() {
    
        CourseDTO dto = new CourseDTO(testingName1);
        String dtoString = TestingHelpers.asJsonString(dto);
        
        try {
                MvcResult result = 
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .post("/enrolling/courses")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( dtoString )
                        .accept(MediaType.APPLICATION_JSON)
                        
                )
                .andExpect( status().is(201) )
                .andReturn()
                    ;

                String jsonResponse = result.getResponse().getContentAsString();
                
                courseDto = (CourseDTO) TestingHelpers.asDTO(jsonResponse, CourseDTO.class);
                
                assertTrue( courseDto.getName().equals(testingName1) );
                
        } catch (Exception ex) {

            fail("Exception reached: " + ex);
        }
    }

    @Test
    @Commit
    @Order(2)
    public void courseUpdateCourse() {
    
        courseDto.setName(testingName2);
        String dtoString = TestingHelpers.asJsonString(courseDto);
        
        try {
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .put("/enrolling/courses/" + courseDto.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( dtoString )
                        .accept(MediaType.APPLICATION_JSON)
                        
                ).andExpect( status().is(200) );

        } catch (Exception ex) {

            fail("Exception reached: " + ex);
        }
    }

    @Test
    @Commit
    @Order(3)
    public void courseGetCourseById() {
    
        try {
                MvcResult result = 
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .get("/enrolling/courses/" + courseDto.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        
                )
                .andExpect( status().is(200) )
                .andReturn()
                    ;

                String jsonResponse = result.getResponse().getContentAsString();
                
                courseDto = (CourseDTO) TestingHelpers.asDTO(jsonResponse, CourseDTO.class);
                
                assertTrue( courseDto.getName().equals(testingName2) );

        } catch (Exception ex) {

            fail("Exception reached: " + ex);
        }
    }

    @Test
    @Commit
    @Order(4)
    public void courseGetAllCourses() {
    
        try {
                MvcResult result = 
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .get("/enrolling/courses/all")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        
                )
                .andExpect( status().is(200) )
                .andReturn()
                    ;

                String jsonResponse = result.getResponse().getContentAsString();
                
                List<CourseDTO> list = TestingHelpers.asDTOList(CourseDTO.class, jsonResponse);

                assertTrue( list.size() > 0 );

        } catch (Exception ex) {

            fail("Exception reached: " + ex);
        }
    }

    @Test
    @Commit
    @Order(5)
    public void courseDeleteCourse() {
    
        String dtoString = TestingHelpers.asJsonString(courseDto);
        
        try {
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .delete("/enrolling/courses/" + courseDto.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( dtoString )
                        .accept(MediaType.APPLICATION_JSON)
                        
                ).andExpect( status().is(200) );

        } catch (Exception ex) {

            Logger.getLogger(EnrollRestControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            
            fail("Exception reached: " + ex);
        }
    }

    @Test
    @Order(6)
    @Rollback
    public void courseGetAllCourseByPage() {
    
        String[] testingDtoList = new String[100];

        for (int i = 0; i < 100; i++) {
        
            CourseDTO tmpDto = new CourseDTO(testingName1 + "_" + i);
            
            testingDtoList[i] = TestingHelpers.asJsonString(tmpDto);
        }
        
        try {
                for (int i = 0; i < 100; i++) {
                    
                    mvc.perform(

                        MockMvcRequestBuilders
                            .post( "/enrolling/courses" )
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( testingDtoList[i] )
                            .accept( MediaType.APPLICATION_JSON )
                    );
                }
                
                
                MvcResult result = 
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .get("/enrolling/courses?pageNumber=30&pageSize=10")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect( status().is(200) )
                .andReturn()
                    ;

                String jsonResponse = result.getResponse().getContentAsString();
                
                List<CourseDTO> list = TestingHelpers.asDTOList(CourseDTO.class, jsonResponse);

                assertTrue( list.isEmpty() );
                
                
                result = 
                mvc.perform(
                    
                    MockMvcRequestBuilders
                        .get("/enrolling/courses?pageNumber=3&pageSize=10")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect( status().is(200) )
                .andReturn()
                    ;

                jsonResponse = result.getResponse().getContentAsString();
                
                list = TestingHelpers.asDTOList(CourseDTO.class, jsonResponse);

                assertTrue( list.get(0).getName().equals(testingName1 + "_30") );
                assertTrue( list.get(9).getName().equals(testingName1 + "_39") );
            

        } catch (Exception ex) {

            fail("Exception reached: " + ex);
        }
    }

}
