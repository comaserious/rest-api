package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Schema(description = "회원 관련 정보 DTO")
public class UserDTO {

    @Schema(description = "회원 번호(pk)")
    private int no;

    @Schema(description = "회원 아이디")
    private String id;

    @Schema(description = "회원 비밀번호")
    private String pwd;

    @Schema(description = "회원 이름", example = "홍길동")
    private String name;

    @Schema(description = "가입 날짜")
    private LocalDate enrollDate;
}
