/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.List;
import org.junit.Ignore;

/**
 *
 * @author marcello
 */
@Ignore
public class TestingHelpers {

    public static String asJsonString(final Object obj) {
        
        try {
                return new ObjectMapper().writeValueAsString(obj);
                
        } catch (Exception e) {

            return "";
        }
    }
    
    public static Object asDTO(final String jsonString, final Class clazz) {
        
        try {
                return new ObjectMapper().readValue(jsonString, clazz);
                
        } catch (Exception e) {

            return null;
        }
    }
    
    public static <T> List<T> asDTOList(Class<T> clazz, final String jsonString) {
        
        try {
                ObjectMapper objectMapper = new ObjectMapper();
                TypeFactory typeFactory = objectMapper.getTypeFactory();
                
                List<T> dtoList = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, clazz));    
                
                
                return dtoList;

        } catch (Exception e) {

            return null;
        }
    
    }
    
}
