package com.ohgiraffers.restapi.section03.valid;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    /*@Valid - 유효성 검증*/
    /*필기.
    *  @Null : null 만 허용한다
    *  @NotNull : null 을 허용하지 않는다. " ", "" 은 허용
    *  @NotEmpty : null 과 "" 을 허용하지 않는다, " " 허용한다.
    *  @NotBlank : null, "" , " "  을 모두 허용하지 않는다
    *  @Email : 이메일 형식을 검사한다 . 다만 "" 는 허용한다.
    *  @Pattern*(regexp = ) : 정규식 검사시에 사용한다
    *  ex> regexp="[a-z1-8]{6-10}", message = "비밀번호는 영어 숫자 포함해서 6~10자리 이내로"
    *  @Size(min= , max= ) : 길이를 제한할 때 사용한다
    *  @Min(value = ) : value 이상의 값을 받을 때 사용한다.
    *  @Max(value = ) : value 이하의 값을 받을 때 사용한다.
    *  @Positive : 값을 양수로 제한
    *  @PositiveOrZero : 양수와 0 만 가능하다
    *  @Negative 는 반대이다
    *  @Future : 현재보다 미래 날짜
    *  @Past : 현재보다 과거 날짜
    *  @AssertTrue : true 여부 확인 , null 은 체크 하지 않음  */

    private int no;

    @NotNull(message = "아이디는 반드시 입력되어야 합니다")
    @NotBlank(message = "아아디는 공백을 허용하지 않습니다")
    private String id;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,10}$", message = "대소문자 영어 숫자 포함 8~10 글자여야 합니다")
    private String pwd;

    @NotNull(message = "이름은 반드시 입력되어야 합니다")
    @Size(min = 2, message = "이름은 두 글자  이상이어야 합니다")
    private String name;

    @Past
    private LocalDate enrollDate;


}
