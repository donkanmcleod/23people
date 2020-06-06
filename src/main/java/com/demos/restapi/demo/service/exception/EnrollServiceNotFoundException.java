/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.service.exception;

/**
 *
 * @author marcello
 */
public class EnrollServiceNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>EnrollServiceException</code> without
     * detail message.
     */
    public EnrollServiceNotFoundException() {
    }

    /**
     * Constructs an instance of <code>EnrollServiceException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EnrollServiceNotFoundException(String msg) {
        super(msg);
    }

    public EnrollServiceNotFoundException(DTOConvertException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
