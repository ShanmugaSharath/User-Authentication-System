package com.jwtproject.jwtdemo.service;

import com.jwtproject.jwtdemo.dto.LoginDTO;
//import com.jwtproject.jwtdemo.controller.APIResponse;
import com.jwtproject.jwtdemo.dto.SignUpRequestDTO;
import com.jwtproject.jwtdemo.entity.User;
import com.jwtproject.jwtdemo.repo.UserRepository;
import com.jwtproject.jwtdemo.util.Jwtutil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwtproject.jwtdemo.common.APIResponse;
@Service
public class LoginService {

 @Autowired
 private UserRepository userrepo;

 @Autowired
 private Jwtutil j;


    public APIResponse signUp(SignUpRequestDTO s)
     {
         APIResponse apiresponse=new APIResponse();
       
       //validation
       if (s.getEmailId() == null || !isValidEmail(s.getEmailId())) {
               apiresponse.setError("Invalid email address");
               apiresponse.setStatus(400); // Bad Request
               return apiresponse;
           }
       
           // Validate password
           if (s.getPassword() == null || !isValidPassword(s.getPassword())) {
               apiresponse.setError("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number");
               apiresponse.setStatus(400); // Bad Request
               return apiresponse;
           }
       
              
             User u=new User();
             u.setName(s.getName());
             u.setEmailId(s.getEmailId());
             u.setIsActive(Boolean.TRUE);
             u.setGender(s.getGender());
             u.setPhoneNumber(s.getPhoneNumber());
             u.setPassword(s.getPassword());
       
       
             User savedUser = userrepo.save(u);
       if (savedUser != null) {
           apiresponse.setData("User created successfully");
       } else {
           apiresponse.setError("Failed to create user");
           apiresponse.setStatus(500);
       }
       
               return apiresponse;
           }
       
       
           
           public APIResponse  login(LoginDTO l) {

        // validation
        
        // verify user exits email and password
        User u=userrepo.findByEmailIdIgnoreCaseAndPassword(l.getEmailId(),l.getPassword());
        APIResponse a=new APIResponse();
        if(u==null)
        {
            a.setError("worng email and password");
            return a;
        }
        
        String token=j.generatetoken(u);

        
        
        // in data creating one object and send the token becausce dont send directly to data to token
        
        Map<String,Object> d=new HashMap<>();
        d.put("AcessToken:",token);
        
        a.setData(d);
        return a;
    }

        
    
    
    
    
    private boolean isValidEmail(String emailId) {
        Pattern p=Pattern.compile("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9+-_.]+$");
        Matcher m=p.matcher(emailId);
        return m.matches();
    }
    private boolean isValidPassword(String password) {
        Pattern p=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
        Matcher m=p.matcher(password);
        return m.matches();
    }






}
