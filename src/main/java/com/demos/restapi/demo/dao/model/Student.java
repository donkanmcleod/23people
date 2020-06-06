/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.dao.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *
 * @author marcello
 */
@Entity
@Table(name = "students")
public class Student implements Serializable {

    private static final long serialVersionUID = 2L;
    
    @Id
    @Column(name = "st_rut")
    private String id;

    @Column(name = "st_name")
    private String name;

    @Column(name = "st_lastname")
    private String lastName;

    @Column(name = "st_age")
    private Integer age;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    public Student() {
    }
    
    public Student(final String id, final String name, final String lastName, final Integer age) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Student(final String id, final String name, final String lastName, final Integer age, final Course course) {

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
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
        final Student other = (Student) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", lastName=" + lastName + ", age=" + age + ", course=" + course.toString() + '}';
    }
    
}
