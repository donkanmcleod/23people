/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 *
 * @author marcello
 */
public class CourseDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    
    private Integer id;

    @NotEmpty(message = "{validation.course.name.notempty}")
    @Size(min = 1, max = 32, message = "{validation.course.name.size}")
    private String name;


    public CourseDTO() {
    }
    
    public CourseDTO(final String name) {

        this.name = name;
    }

    public CourseDTO(final Integer code, final String name) {

        this.id = code;
        this.name = name;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        int hash = 3;
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
        final CourseDTO other = (CourseDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name=" + name + '}';
    }

}
