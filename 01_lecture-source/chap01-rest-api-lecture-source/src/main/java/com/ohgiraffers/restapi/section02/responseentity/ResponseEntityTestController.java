package com.ohgiraffers.restapi.section02.responseentity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {

    /*필기.
    *  ResponseEntity 란?
    *  결과 데이터(body 에 담는다)와 HTTP 상태 코드를 직접 제어할 수 있는 클래스이다.
    *  HttpStatus, HttpHeaders, HttpBody 를 포함 하고 있다.*/

    private List<UserDTO> users;

    public ResponseEntityTestController(){
        users = new ArrayList<>();

        users.add(new UserDTO(1,"user01","pass01","너구리", LocalDate.now()));
        users.add(new UserDTO(2,"user02","pass02","개구리", LocalDate.now()));
        users.add(new UserDTO(3,"user03","pass03","코끼리", LocalDate.now()));

    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        Map<String,Object> responeMap = new HashMap<>();
        responeMap.put("users",users);

        ResponseMessage responseMessage = new ResponseMessage(200,"정상 조회",responeMap);

        return new ResponseEntity<>(responseMessage,headers, HttpStatus.OK);
    }

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
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){
        UserDTO foundUser =
                users.stream().filter(user->user.getNo()==userNo).collect(Collectors.toList()).get(0);

        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }
}
