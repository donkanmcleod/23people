/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author marcello
 */
public class StudentDTO implements Serializable {
    
    private static final long serialVersionUID = 2L;
    

    @NotEmpty(message = "{validation.student.id.notempty}")
    @Size(min = 2, max = 9, message = "{validation.student.id.size}")
    private String id;

    @NotEmpty(message = "{validation.student.name.notempty}")
    @Size(min = 1, max = 32, message = "{validation.student.name.size}")
    private String name;

    @NotEmpty(message = "{validation.student.lastName.notempty}")
    @Size(min = 1, max = 32, message = "{validation.student.lastName.size}")
    private String lastName;

    @Min(15)
    @Max(99)
    private Integer age;

    @NotNull(message = "{validation.student.course.notnull}")
    private CourseDTO course;


    public StudentDTO() {
    }
    
    public StudentDTO(final String id, final String name, final String lastName, final Integer age) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public StudentDTO(final String id, final String name, final String lastName, final Integer age, final CourseDTO course) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.course = course;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentDTO other = (StudentDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", lastName=" + lastName + ", age=" + age + ", courseDTO=" + course.toString() + '}';
    }

}
