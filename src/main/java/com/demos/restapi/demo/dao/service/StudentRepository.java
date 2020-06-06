/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.dao.service;

import com.demos.restapi.demo.dao.model.Student;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author marcello
 */
public interface StudentRepository extends CrudRepository<Student, String> {
    
}
