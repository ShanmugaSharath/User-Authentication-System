package com.jwtproject.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jwtproject.jwtdemo.common.APIResponse;
import com.jwtproject.jwtdemo.dto.LoginDTO;
import com.jwtproject.jwtdemo.dto.SignUpRequestDTO;
import com.jwtproject.jwtdemo.service.LoginService;
import com.jwtproject.jwtdemo.util.Jwtutil;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginservice;

    @Autowired
    private Jwtutil jwt;
    //string v=jwt.SECRET

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp (@RequestBody SignUpRequestDTO s)
    {
       APIResponse apiresponse = loginservice.signUp(s);

       return ResponseEntity.status(apiresponse.getStatus()).body(apiresponse);
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginDTO l)
    {
         APIResponse a= loginservice.login(l);
         return ResponseEntity.status(a.getStatus()).body(a);
    }


   
   
        @GetMapping("/privateApi")
public ResponseEntity<APIResponse> privateApi(@RequestHeader(value = "autho", defaultValue = "") String tokenverify) {
    APIResponse apiResponse = new APIResponse();
    
    // Log the received token
    System.out.println("Token: " + tokenverify);
    
    // Check if the token is empty
    if (tokenverify.isEmpty()) {
        apiResponse.setData("No token provided");
        apiResponse.setStatus(400); // Bad request if no token is provided
        return ResponseEntity.status(400).body(apiResponse);
    } else {
        try {
            // Verify the token
            jwt.verify(tokenverify);
            apiResponse.setData("This is User got permission ");
            apiResponse.setStatus(200); // OK status for successful verification
        } catch (Exception e) {
            // Log and handle exception
            e.printStackTrace();
            apiResponse.setData("Invalid JWT token");
            apiResponse.setStatus(401); // Unauthorized status for invalid token
        }
    }
    
    // Return the API response with the appropriate status
    return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
}

   

}
