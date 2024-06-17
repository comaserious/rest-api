package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RequestMapping("/response")
@RestController
// @Controller + @ResponseBody
public class ResponseTestController {

    /*문자열 응답 test*/
    @GetMapping("/hello")
    public String helloworld(){

        // 더이상 controller 는 뷰페이지를 반환하지 않는다
        // 다만 url 값에 데이터를 담아서 보낼 뿐이다

        System.out.println("hello world");

        return "hello world!!!!";
    }

    /* 기본 자료형 test*/
    @GetMapping("/random")
    public int getRandomNumber(){

        return (int)(Math.random()*10) +1;
    }

    /* Object 타입 응답*/
    @GetMapping("/message")
    public Message getMessage(){
        return new Message(200,"정상 응답 완료!!" );
    }

    /* List 타입 응답*/
    @GetMapping("/list")
    public List<String> getList(){

        return List.of(new String[]{"햄버거","피자","떡볶이"});

    }

    /* Map 타압 응답*/
    @GetMapping("/map")
    public Map<Integer,String> getMap(){
        Map<Integer,String> messageMap = new HashMap<>();

        messageMap.put(200,"정상 응답완료!!");
        messageMap.put(404,"페이지 찾을 수 없음 .. ");
        messageMap.put(500,"서버 내부 오류 => 개발자의 잘못");

        return messageMap;
    }

    /*필기.
    *  image response
    *  produces 설정을 해주지 않으면 이미지가 텍스트 형식으로 전송된다.
    *  produces 는 response header 의 content-type 에 대한 설정이다*/

    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/images/restapi.png").readAllBytes();
    }

    /* ResponseEntity 를 이용한 응답*/
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity(){

        // ResponseEntity 는 확인해보면 알다싶이 3가 인수를 요구한다
        // HttpStatus 반드시 있어야 한다. 흔히 상태코드를 동반한다
        // Body 있어도 되고 없어도 된다
        // HttpHeaders 있어도 되고 없어도 된다

        return ResponseEntity.ok(new Message(200,"정상 작동"));


    }
}
