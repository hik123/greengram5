package com.green.greengram4.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode{
    NOT_EXIST_USER_ID(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."),
    VALID_PASSWORD(HttpStatus.NOT_FOUND, "비밀번호를 확인해 주세요."),
    NEED_SIGNIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND,
            "refresh-token이 없습니다.");


    private final HttpStatus httpStatus; // HttpStatus spring으로 import
    private final String message;
    

    }
