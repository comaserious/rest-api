package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException e){
        String code = "ERROR_CODE_0001";
        String description = "회원 정보 조회에 실패하였습니다";
        String detail = e.getMessage() ;

        ErrorResponse errorResponse = new ErrorResponse(code,description,detail);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND );

    }
}
