/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.jwt.filter;

import com.demos.restapi.demo.jwt.model.UserPrincipal;
import com.demos.restapi.demo.jwt.service.TokenService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 *
 * @author marcello
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final TokenService tokenService;

    
    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeaderIsInvalid(authorizationHeader)) {
            
            filterChain.doFilter(httpServletRequest, httpServletResponse);

            return;
        }

        UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        
        return authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        
        String token = authorizationHeader.replace("Bearer ", "");
        UserPrincipal userPrincipal = tokenService.parseToken(token);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (userPrincipal.isAdmin())
            authorities.add(new SimpleGrantedAuthority("ROLE_ENROLLING"));


        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
    
}
