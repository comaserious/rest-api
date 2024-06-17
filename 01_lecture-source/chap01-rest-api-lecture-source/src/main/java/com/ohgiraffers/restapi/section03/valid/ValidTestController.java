package com.ohgiraffers.restapi.section03.valid;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
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

@RestController
@RequestMapping("/valid")
public class ValidTestController {

    private List<UserDTO> users;

    public ValidTestController(){
        users = new ArrayList<>();

        users.add(new UserDTO(1,"user01","pass01","너구리", LocalDate.now()));
        users.add(new UserDTO(2,"user02","pass02","코알라", LocalDate.now()));
        users.add(new UserDTO(3,"user03","pass03","개구리", LocalDate.now()));
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) throws UserNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        List<UserDTO> foundUserList = users.stream().filter(user -> user.getNo()== userNo).collect(Collectors.toList());

        UserDTO foundUser = null;

        if (foundUserList.size()>0){
            foundUser = foundUserList.get(0);
        }else{
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다");
        }

        Map<String,Object> responeMap = new HashMap<>();
        responeMap.put("user",foundUser);

        return ResponseEntity.ok(new ResponseMessage(200,"조회 성공!!",responeMap));

    }

    @PostMapping("/users")
    public ResponseEntity<?> regist(@Valid @RequestBody UserDTO newUser){

        System.out.println("newUser = " + newUser);

        return ResponseEntity.created(URI.create("/valid/users/"+newUser.getNo())).build();
    }

}
