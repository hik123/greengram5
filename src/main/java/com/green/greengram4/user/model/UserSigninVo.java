package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSigninVo {
    private final int result;
    private int iuser;
    private String nm;
    private String pic;
    private String firebaseToken;
    private String accessToken;
}