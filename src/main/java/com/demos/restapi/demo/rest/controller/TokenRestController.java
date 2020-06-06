/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.rest.controller;

import com.demos.restapi.demo.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author marcello
 */
@RestController
public class TokenRestController {
    
    @Autowired
    @Qualifier("JWTTokenService")
    private TokenService tokenService;


    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll()")
    public String getAToken() {
        
        return this.tokenService.generateToken();
    }

}
