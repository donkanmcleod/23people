/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service;

import com.demos.restapi.demo.service.dto.CourseDTO;
import com.demos.restapi.demo.service.dto.StudentDTO;
import com.demos.restapi.demo.service.exception.EnrollServiceException;
import com.demos.restapi.demo.service.exception.EnrollServiceNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;


/**
 *
 * @author marcello
 */
public interface EnrollService {
    
    public CourseDTO createCoruse(CourseDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public CourseDTO getCourseByName(String name) throws EnrollServiceException, EnrollServiceNotFoundException;

    public CourseDTO updateCoruse(Integer id, CourseDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public void deleteCoruse(Integer id) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public CourseDTO getCourseById(Integer id) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public List<CourseDTO> getAllCourses() throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public Page<CourseDTO> getAllCoursesByPage(final int pageSize, final int pageNumber) throws EnrollServiceException, EnrollServiceNotFoundException;




    public StudentDTO createStudent(StudentDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public StudentDTO updateStudent(StudentDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public void deleteStudent(String id) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public StudentDTO getStudentById(String id) throws EnrollServiceException, EnrollServiceNotFoundException;
    
    public List<StudentDTO> getStudentsByCourse(Integer courseId) throws EnrollServiceException, EnrollServiceNotFoundException;

}
