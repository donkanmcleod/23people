/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service.impl;

import com.demos.restapi.demo.dao.model.Course;
import com.demos.restapi.demo.dao.model.Student;
import com.demos.restapi.demo.dao.service.CourseRepository;
import com.demos.restapi.demo.dao.service.StudentRepository;
import com.demos.restapi.demo.service.CourseBuilder;
import com.demos.restapi.demo.service.EnrollService;
import com.demos.restapi.demo.service.StudentBuilder;
import com.demos.restapi.demo.service.dto.CourseDTO;
import com.demos.restapi.demo.service.dto.StudentDTO;
import com.demos.restapi.demo.service.exception.DTOConvertException;
import com.demos.restapi.demo.service.exception.EnrollServiceException;
import com.demos.restapi.demo.service.exception.EnrollServiceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 *
 * @author marcello
 */
@Service("EnrollingService")
public class EnrollingServiceImpl implements EnrollService {

    @Autowired
    CourseRepository courseRepo;
    
    @Autowired
    CourseBuilder courseBuilder;
    
    @Autowired
    StudentRepository studentRepo;
    
    @Autowired
    StudentBuilder studentBuilder;

    


    @Override
    public CourseDTO createCoruse(CourseDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException {
        
        try {
                Course e = this.courseRepo.findByName( dto.getName() );
                
                if (e != null ) throw new EnrollServiceException("A course with name '" + dto.getName() + "' already exists.");
                
            
                Course entity = this.courseBuilder.convertToDomain(dto, Course.class);
                
                Course savedEntity = this.courseRepo.save(entity);
                
                CourseDTO returnDTO = this.courseBuilder.convertToDTO(savedEntity, CourseDTO.class);
                
                
                return returnDTO;
                
        } catch (DTOConvertException ex) {
            
            throw new EnrollServiceException(ex);
        }
    }
    
    @Override
    public CourseDTO getCourseByName(String name) throws EnrollServiceException, EnrollServiceNotFoundException {
    
        Course entity = this.courseRepo.findByName(name);
        
        if (entity == null) throw new EnrollServiceNotFoundException("Course with name '" + name + "' not found.");
        
        CourseDTO dto;
        try {
                dto = this.courseBuilder.convertToDTO(entity, CourseDTO.class);

                
                return dto;
                
        } catch (DTOConvertException ex) {

            throw new EnrollServiceException(ex);
        }
    }

    @Override
    public CourseDTO updateCoruse(Integer id, CourseDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException {

        try {
                if (!this.courseRepo.existsById( id )) throw new EnrollServiceNotFoundException("No course found for id provided '" + id + "'. Can't update.");
                

                Course entity = this.courseBuilder.convertToDomain(dto, Course.class);
                
                Course savedEntity = this.courseRepo.save(entity);
                
                CourseDTO returnDTO = this.courseBuilder.convertToDTO(savedEntity, CourseDTO.class);
                
                
                return returnDTO;
                
        } catch (DTOConvertException ex) {
            
            throw new EnrollServiceException(ex);
        }
    }

    @Override
    public void deleteCoruse(Integer id) throws EnrollServiceException, EnrollServiceNotFoundException {

        Optional<Course> courseEntity = this.courseRepo.findById( id );
        
        if (!courseEntity.isPresent()) throw new EnrollServiceNotFoundException("No course found for id provided '" + id + "'");
        
        
        this.courseRepo.delete( courseEntity.get() );
    }

    @Override
    public CourseDTO getCourseById(Integer id) throws EnrollServiceException, EnrollServiceNotFoundException {

        Optional<Course> courseEntity = this.courseRepo.findById( id );
        
        if (!courseEntity.isPresent()) throw new EnrollServiceNotFoundException("No course found for id provided '" + id + "'");
        
        CourseDTO dto;
        try {
                dto = this.courseBuilder.convertToDTO(courseEntity.get(), CourseDTO.class);
             

                return dto;

        } catch (DTOConvertException ex) {
            
            throw new EnrollServiceException(ex);
        }
    }
    
    @Override
    public List<CourseDTO> getAllCourses() throws EnrollServiceException, EnrollServiceNotFoundException {

        List<Course> courseList = (List<Course>) this.courseRepo.findAll();
        
        if (courseList.isEmpty()) throw new EnrollServiceNotFoundException("No records found.");
        
        
        try {
                List<CourseDTO> dtoList = this.courseBuilder.convertToDTOList(courseList, CourseDTO.class);
                
                
                return dtoList;
            
        } catch (DTOConvertException ex) {

            throw new EnrollServiceException(ex);
        }
    }
    
    @Override
    public Page<CourseDTO> getAllCoursesByPage(final int pageSize, final int pageNumber) throws EnrollServiceException, EnrollServiceNotFoundException {

        try {
                Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
                        
                Page<Course> pagedResult = this.courseRepo.findAll(paging);

                if (!pagedResult.hasContent()) throw new EnrollServiceNotFoundException("No records found.");
         

                List<Course> entities = pagedResult.getContent();

                List<CourseDTO> dtoList = this.courseBuilder.convertToDTOList(entities, CourseDTO.class);

                
                Page<CourseDTO> dtoPagedResult = new PageImpl<CourseDTO>(dtoList, paging, pagedResult.getTotalElements());

                
                return dtoPagedResult;
            
        } catch (DTOConvertException ex) {

            throw new EnrollServiceException(ex);
        }
    }

    






    @Override
    public StudentDTO createStudent(StudentDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException {

        try {
                Optional<Course> courseEntity = this.courseRepo.findById( dto.getCourse().getId() );
                
                if (!courseEntity.isPresent()) throw new EnrollServiceNotFoundException("Unconsistent state of related course id provided '" + dto.getCourse().getId() + "'");
                
            
                Student student = this.studentBuilder.convertToDomain(dto, Student.class);
                student.setCourse( courseEntity.get() );
                Student savedEntity = this.studentRepo.save(student);
                
                
                StudentDTO returnDTO = this.studentBuilder.convertToDTO(savedEntity, StudentDTO.class);
                CourseDTO  courseDTO = this.courseBuilder.convertToDTO(savedEntity.getCourse(), CourseDTO.class);

                returnDTO.setCourse(courseDTO);
                
                
                return returnDTO;
                
        } catch (DTOConvertException ex) {
            
            throw new EnrollServiceException(ex);
        }
    }

    @Override
    public StudentDTO updateStudent(StudentDTO dto) throws EnrollServiceException, EnrollServiceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteStudent(String id) throws EnrollServiceException, EnrollServiceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StudentDTO getStudentById(String id) throws EnrollServiceException, EnrollServiceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<StudentDTO> getStudentsByCourse(Integer courseId) throws EnrollServiceException, EnrollServiceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
