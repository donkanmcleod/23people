/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service;

import com.demos.restapi.demo.service.exception.DTOConvertException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;


/**
 *
 * @author marcello
 */
public abstract class BaseEntityBuilder<T, D> {
    
    /**
     * Creates a DTO object from model.
     *
     * @param domain the domain
     * @param clazz the clazz
     * @return T
     */
    public T convertToDTO(D domain, Class<T> clazz) throws DTOConvertException {
    
        if (domain == null) throw new DTOConvertException("Type '" + clazz.getName() + "' provided is null.");

        try {
                T dto = clazz.newInstance();

                BeanUtils.copyProperties(domain, dto);
                
                
                return dto;
            
        } catch (InstantiationException | IllegalAccessException ex) {

            throw new DTOConvertException(ex);
        }
    }
    
    /**
     * Convierte una lista completa de objetos de dominio a su equivalente DTO.
     *
     * @param domainList the domain list
     * @param clazz the clazz
     * @return the list
     */
    public List<T> convertToDTOList(List<D> domainList, Class<T> clazz) throws DTOConvertException {
    
    	if (domainList == null) throw new DTOConvertException("Type list '" + clazz.getName() + "' provided is null.");
        
        List<T> dtoList = new ArrayList<T>();
        
        try {
                domainList.forEach((entity) -> {

                    try {
                            dtoList.add( convertToDTO(entity, clazz) );
                    
                    } catch (DTOConvertException ex) { }
                });
                
        } catch (Exception ex) {

            throw new DTOConvertException(ex);
        }

        if (domainList.size() != dtoList.size()) throw new DTOConvertException("Could not convert the whole list of entities.");
        

        return dtoList;
    }
    
    /**
     * Convierte un objeto de dominio a su equivalente DTO.
     *
     * @param dto the dto
     * @param clazz the clazz
     * @return D
     */
    public D convertToDomain(T dto, Class<D> clazz) throws DTOConvertException {
    
    	if (dto == null) throw new DTOConvertException("Type '" + clazz.getName() + "' provided is null.");
    	
        try {
                D domain = clazz.newInstance();

                BeanUtils.copyProperties(dto, domain);
                
                
                return domain;
            
        } catch (InstantiationException | IllegalAccessException ex) {

            throw new DTOConvertException(ex);
        }
    }
    
    /**
     * Convierte una colección completa de objetos de dominio a su equivalente DTO.
     *
     * @param dtoList the dto list
     * @param clazz the clazz
     * @return the list
     */
    public List<D> convertToDomainList(List<T> dtoList, Class<D> clazz) throws DTOConvertException {
    
    	if (dtoList == null) throw new DTOConvertException("Type list '" + clazz.getName() + "' provided is null.");

        List<D> domainList = new ArrayList<D>();
        
        try {
                dtoList.forEach((dto) -> {

                    try {
                            domainList.add( convertToDomain(dto, clazz) );
                    
                    } catch (DTOConvertException ex) { }
                });
                
        } catch (Exception ex) {

            throw new DTOConvertException(ex);
        }

        if (domainList.size() != dtoList.size()) throw new DTOConvertException("Could not convert the whole list of dto.");

        
        return domainList;
    }

}
