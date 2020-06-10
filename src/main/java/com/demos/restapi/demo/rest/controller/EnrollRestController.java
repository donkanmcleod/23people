/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.rest.controller;

import com.demos.restapi.demo.service.EnrollService;
import com.demos.restapi.demo.service.dto.CourseDTO;
import com.demos.restapi.demo.service.dto.StudentDTO;
import com.demos.restapi.demo.service.exception.EnrollServiceException;
import com.demos.restapi.demo.service.exception.EnrollServiceNotFoundException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author marcello
 */
@CrossOrigin
@RestController
@RequestMapping("/enrolling")
public class EnrollRestController {
    
    @Autowired
    private EnrollService enrollSrv;
    
    /**
     *
     * @param course
     * @return
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/courses")
    @PreAuthorize("hasRole('ROLE_ENROLLING')")
    public CourseDTO crateCourse(@Valid @RequestBody CourseDTO course) throws EnrollServiceException, EnrollServiceNotFoundException {
        
        return this.enrollSrv.createCoruse(course);
    }

    /**
     *
     * @param course
     * @param id
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/courses/{id}")
    @PreAuthorize("hasRole('ROLE_ENROLLING')")
    public void updateCourse(@Valid @RequestBody CourseDTO course, @Valid @PathVariable Integer id) throws EnrollServiceException, EnrollServiceNotFoundException {
        
        this.enrollSrv.updateCoruse(id, course);
    }

    /**
     *
     * @param id
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/courses/{id}")
    @PreAuthorize("hasRole('ROLE_ENROLLING')")
    public void deleteCourse(@Valid @PathVariable Integer id) throws EnrollServiceException, EnrollServiceNotFoundException {
        
        this.enrollSrv.deleteCoruse(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/courses/{id}")
    @PreAuthorize("hasRole('ROLE_ENROLLING')")
    public CourseDTO getCourseById(@Valid @PathVariable Integer id) throws EnrollServiceException, EnrollServiceNotFoundException {
        
        return this.enrollSrv.getCourseById(id);
    }

    /**
     *
     * @return
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/courses/all")
    @PreAuthorize("hasRole('ROLE_ENROLLING')")
    public List<CourseDTO> getAllCourses() throws EnrollServiceException, EnrollServiceNotFoundException {
        
        return this.enrollSrv.getAllCourses();
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @return
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/courses", produces = { "application/json" })
    @PreAuthorize("hasRole('ROLE_ENROLLING')")
    public Page<CourseDTO> getAllCoursesByPage(@NotNull @RequestParam(defaultValue = "0") final Integer pageNumber, @NotNull @RequestParam(defaultValue = "10") final Integer pageSize) 
    throws EnrollServiceException, EnrollServiceNotFoundException {
        
        return this.enrollSrv.getAllCoursesByPage(pageSize, pageNumber);
    }

    
    
    
    /**
     *
     * @param student
     * @return
     * @throws EnrollServiceException
     * @throws EnrollServiceNotFoundException
     */
    @PostMapping(path = "/students")
    public StudentDTO crateStudent(@Valid @RequestBody StudentDTO student) throws EnrollServiceException, EnrollServiceNotFoundException {
        
        return this.enrollSrv.createStudent(student);
    }

}
