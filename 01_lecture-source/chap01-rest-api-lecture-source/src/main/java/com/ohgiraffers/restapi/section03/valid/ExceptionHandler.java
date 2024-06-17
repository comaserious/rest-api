package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidationException(MethodArgumentNotValidException e){

        String code = "";
        String description = "";
        String detail = "";

        if(e.getBindingResult().hasErrors()){
            detail = e.getBindingResult().getFieldError().getDefaultMessage(); // e.getMessage() 와 동일한 의미
            System.out.println("detail = " + detail);

            // NotNull, NotBlank, Size ...
            String bindingResultCode = e.getBindingResult().getFieldError().getCode();

            switch (bindingResultCode){
                case "NotBlank" :
                    code = "ERROR_CODE_0002";
                    description = "필수 값이 누락되었습니다";
                    break;
                case "Size":
                    code = "ERROR_CODE_0003";
                    description = "글자수를 확인하세요";
                    break;
                case "Pattern":
                    code = "ERROR_CODE_0004";
                    description = "비밀번호를 정확히 입력하세요";
            }
        }

        return new ResponseEntity(new ErrorResponse(code,description,detail),HttpStatus.BAD_REQUEST);

    }
}
