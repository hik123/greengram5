package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserSignupProcDto {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;
}
