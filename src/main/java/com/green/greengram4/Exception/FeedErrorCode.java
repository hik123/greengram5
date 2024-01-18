package com.green.greengram4.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FeedErrorCode implements ErrorCode {
    IMPOSSIBLE_REG_COMMENT(HttpStatus.BAD_REQUEST, "댓글을 등록할 수 없습니다."),
    PICS_MORE_THEN_ONE(HttpStatus.BAD_REQUEST, "사진은 1장 이상을 선택하셔야 합니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
