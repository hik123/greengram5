package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSigninProcVo {
    private int iuser;
    private String upw;
    private String nm;
    private String pic;
}
