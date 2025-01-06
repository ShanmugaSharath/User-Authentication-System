package com.jwtproject.jwtdemo.util;

import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.jwtproject.jwtdemo.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Jwtutil {
    
    private static final String SECRET = "this_is_a_very_secure_and_long_secret_key_for_jwt_signing";
    
    
    private static long expiryDuration=60*60;// 1 hrs duration

    long millitime = System.currentTimeMillis(); // current time change to millisecond
    long expirytime= millitime + expiryDuration * 1000; //
    Date d = new Date();
    Date d1 = new Date(expirytime);



   // generate token

    public String generatetoken(User user)
    {
        //claims
       Claims c=Jwts.claims().setIssuer(user.getId().toString())
                             .setIssuedAt(d)
                             .setExpiration(d1);

        c.put("type", user.getUserType());
        c.put("name",user.getName());
        c.put("emailId",user.getEmailId());


        //generete jwt using claims



       // error showing
       //Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
       Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());

       String s=Jwts.builder().setClaims(c).signWith(key).compact();
       return s;


    }
  
  



public void verify(String autho) throws Exception {
    try {
        // Create a SecretKey from the string secret key
        Key key = new SecretKeySpec(SECRET.getBytes(), "HMACSHA512");

        // Use the parser builder to parse and validate the JWT
        Jwts.parserBuilder()
             .setSigningKey(key)  // Set the SecretKey for signing
             .build()
             .parseClaimsJws(autho);  // Validate the JWT token
    } catch (JwtException e) {
        // Log the detailed error
        System.out.println("JWT token validation error: " + e.getMessage());
        // Re-throw a more specific exception with a message
        throw new Exception("Invalid JWT Token: " + e.getMessage());
    }
}




}
    
   