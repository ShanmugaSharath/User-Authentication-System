package com.jwtproject.jwtdemo.entity;

import java.time.LocalDateTime;

import com.jwtproject.jwtdemo.common.Constant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private String emailId;
    private String phoneNumber;
    private String userType=Constant.user_type.normal;
    private String password;
    private Boolean isActive = true;
    private Integer loginCount = 0;
    private String ssoType;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime loginAt;

    @PrePersist
    public void onSave(){
       // LocalDateTime c= new LocalDateTime();
        LocalDateTime c = LocalDateTime.now();
       
            this.createdAt=c;
        
        this.updateAt=c;
    }
    @PostPersist
    public void onUpdate(){
       // LocalDateTime c= new LocalDateTime();
        LocalDateTime c = LocalDateTime.now();
       
           // this.createdAt=c;
        
        this.updateAt=c;
    }


    
}
