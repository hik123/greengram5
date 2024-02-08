package com.green.greengram4.security;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor //json 갔다가 객체화 될 수 있어서 추가? 기본생성자 필요
@AllArgsConstructor
public class MyPrincipal {
    private int iuser;

    @Builder.Default
    private List<String> roles = new ArrayList();
}
