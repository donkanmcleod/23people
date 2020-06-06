/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.dao.service;

import com.demos.restapi.demo.dao.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 *
 * @author marcello
 */
public interface CourseRepository extends PagingAndSortingRepository<Course, Integer> {
    
    /**
     * Finds a course filtering by its name.
     * 
     * @param name
     * @return  A course matching the criteria in their name.
     */
    public Course findByName(String name);

}
