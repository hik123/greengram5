package com.green.greengram4.Exception;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {  //에러 발생했을때 리턴
    private final String code;
    private final String message;
}
