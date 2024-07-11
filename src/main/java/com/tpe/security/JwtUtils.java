package com.tpe.security;


import com.tpe.security.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret = "sboot";

    private long jwtExpiration = 8640000;//24*60*60*1000 ms (1 day)

    public String generateToken(Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime()+jwtExpiration)).
                signWith(SignatureAlgorithm.HS512,jwtSecret).
                compact();
    }


}
