package com.ohgiraffers.restapi.section05.swagger;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* @Tag : API 들의 그룹을 짓기 위한 어노테이션*/
@Tag(name = "Spring Boot Swagger 연동(USER 기능)")
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {

    private List<UserDTO> users;

    public SwaggerTestController(){
        users = new ArrayList<>();

        users.add(new UserDTO(1,"user01","pass01","코끼리", LocalDate.now()));
        users.add(new UserDTO(2,"user02","pass02","개구리", LocalDate.now()));
        users.add(new UserDTO(3,"user03","pass03","코알라", LocalDate.now()));
    }

    /* @Operation
    *   해당하는 api 의 정보를 제공하는 annotation
    *   속성
    *   summary : 해당 api 의 간단한 요약을 제공한다.
    *   description : 해당 api 의 상세한 설명을 제공한다.*/

    @Operation(summary = "전체 회원 조회", description = "우리 사이트의 전체 회원 목록 조회")
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        Map<String,Object> responeMap = new HashMap<>();
        responeMap.put("users",users);

        ResponseMessage responseMessage = new ResponseMessage(200,"정상 조회",responeMap);

        return new ResponseEntity<>(responseMessage,headers, HttpStatus.OK);
    }


    @Operation(summary = "회원번호로 회원 조회", description = "회원번호를 통한 해당하는 회원 정보를 조회한다."
            ,parameters = {@Parameter(name = "userNo", description = "사용자 화면에서 넘어오는 사용자 번호(pk)")})
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));

        UserDTO foundUser =
                users.stream().filter(user -> user.getNo()==userNo).collect(Collectors.toList()).get(0);
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("user",foundUser);

        /*메소드 체이닝*/
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200,"전송됨?",responseMap));
    }

    @Operation(summary = "신규회원 등록")
    @PostMapping("/users")
    public ResponseEntity<?> regist(@RequestBody UserDTO newUser){

        System.out.println("newUser = " + newUser);

        int lastUserNo = users.get(users.size()-1).getNo();

        newUser.setNo(lastUserNo+1);
        newUser.setEnrollDate(LocalDate.now());
        users.add(newUser);

        // 201 상태코드(create ) => 등록 관련 (생성)
        return ResponseEntity.created(URI.create("/entity/users/"+newUser.getNo())).build();
    }

    /* 수정 */
    @Operation(summary = "회원 정보 수정")
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO, @PathVariable int userNo){

        UserDTO foundUser =
                users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);

        foundUser.setId(userDTO.getId());
        foundUser.setPwd(userDTO.getPwd());
        foundUser.setName(userDTO.getName());

        return ResponseEntity.created(URI.create("/entity/users/"+foundUser.getNo())).build();
    }

    /* 삭제 */
    @Operation(summary = "회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원정보 삭제 성공"),
            @ApiResponse(responseCode = "400" ,description = "잘못 입력된 파라미터")
    })
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){
        UserDTO foundUser =
                users.stream().filter(user->user.getNo()==userNo).collect(Collectors.toList()).get(0);

        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }
}
