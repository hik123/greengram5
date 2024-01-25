package com.green.greengram4.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor //json 갔다가 객체화 될 수 있어서 추가? 기본생성자 필요
@AllArgsConstructor
public class MyPrincipal {
    private int iuser;
}
