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
public class DTOConvertException extends Exception {

    /**
     * Creates a new instance of <code>EnrollServiceException</code> without
     * detail message.
     */
    public DTOConvertException() {
    }

    /**
     * Constructs an instance of <code>EnrollServiceException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DTOConvertException(String msg) {
        super(msg);
    }

    public DTOConvertException(Exception ex) {
        super(ex);
    }
}
