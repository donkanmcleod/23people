/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service;

import com.demos.restapi.demo.dao.model.Student;
import com.demos.restapi.demo.service.dto.StudentDTO;
import org.springframework.stereotype.Service;


/**
 *
 * @author marcello
 */
@Service
public class StudentBuilder extends BaseEntityBuilder<StudentDTO, Student> {
    
}
