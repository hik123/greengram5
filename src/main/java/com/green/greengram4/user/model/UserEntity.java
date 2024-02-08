package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserEntity {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    private String firebaseToken;
    private String role;
    private String createdAt;
    private String updatedAt;
}
