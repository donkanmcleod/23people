/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.jwt.service;

import com.demos.restapi.demo.jwt.model.User;
import com.demos.restapi.demo.jwt.model.UserPrincipal;


/**
 *
 * @author marcello
 */
public interface TokenService {
    
    String generateToken();

    String generateToken(User user);

    UserPrincipal parseToken(String token);

}
