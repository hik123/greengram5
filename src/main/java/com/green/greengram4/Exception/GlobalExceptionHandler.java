package com.green.greengram4.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{ //extends ResponseEntityExceptionHandler {  // 여기에 맞는 에러가 있으면 throw 잡음
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("IllegalArgumentException", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException", e);
        /*
        List<String> errors = new ArrayList();
        for(FieldError lfe : e.getBindingResult().getFieldErrors()) {
            errors.add(lfe.getDefaultMessage());
        }
        */

        List<String> errors = e.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(lfe -> lfe.getDefaultMessage())

                                .collect(Collectors.toList());    //stream().map()사이즈 똑같  stream().fillter()사이즈 달라질수ㅜ다
        String errStr = "[" + String.join(",", errors) + "]";
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, errors.toString());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.warn("handleException", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestApiException.class)  //커스텀한 에러메시지
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.warn("handleRestApiException", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode
            , String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null
                        ? makeErrorResponse(errorCode)
                        : makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse (ErrorCode errorCode) {
        return makeErrorResponse(errorCode, errorCode.getMessage());
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {  //에러 코드 메시지 바꿔서 쓰고싶을때 사용
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }
}
