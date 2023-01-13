package com.example.apibasic.res;

import com.example.apibasic.req.ApiController2;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@Controller
//@ResponseBody
@RestController     // @Controller 와 @ResponseBody 합침
@Slf4j
public class ApiController3 {

    // @responseBody 를 사용하지 않으면 --> server 에서 500 error
    // templates/hello.html 생성 (view template)
    // 문제) 클라이언트가 웹브라우저가 아닐 때는 사용 불가
    // 방법) @ResponseBody 사용 : JSON 으로 넘김
    // 결과)hello 만 출력 됨 (Content-Type : text/plain) ---> res2
    // 이유) jackson 라이브러리때문에 view template 을 이용하지 않음
    @GetMapping("/res1")
    public String res1() {
        log.info("/res1 GET!!");
        return "hello";
    }

    // (Content-Type : application/json)
    //@GetMapping(value = "/res2", produces="application/json")
    @GetMapping("/res2")    // @GetMapping 내부에 위의 내용이 생략되어있음
    public List<String> res2() {
        log.info("/res2 GET!!");
        return List.of("짜장면", "볶음밥", "탕수육");
    }

    @GetMapping(value = "/res3", produces="application/json")
    public ApiController2.OrderInfo res3() {
        log.info("/res3 GET!!");

        ApiController2.OrderInfo orderInfo = new ApiController2.OrderInfo();
        orderInfo.setOrderNo(12L);
        orderInfo.setGoodsName("세탁기");
        orderInfo.setGoodsAmount(2);

        return orderInfo;
    }

    // 사원 목록 정보
    @GetMapping("/employees")
    public List<Employee> empList() {
        return List.of(
                new Employee("홍길동", "영업부", LocalDate.of(2019, 12, 11)),
                new Employee("박영희", "구매부", LocalDate.of(2010, 2, 4)),
                new Employee("김수호", "개발부", LocalDate.of(2022, 4, 23))
        );
    }
    @Setter @Getter @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class Employee {
        private String empName;
        private String deptName;
        private LocalDate hireDate;
    }

    // 응답시에 응답 헤더정보와 응답 상태코드를 조작하려면 ResponseEntity 객체 사용
    @GetMapping("/res4")
    public ResponseEntity<?> res4(String nick) {        // return type [[ ResponseEntity ]] 으로 고정
        if (nick == null || nick.equals("")) {
            return ResponseEntity.badRequest().build();
        }
//        return ResponseEntity.ok(nick + " 님 잘했어요");

        Employee employee = Employee.builder()
                .empName(nick)
                .deptName("영업부")
                .hireDate(LocalDate.of(2019, 12, 11))
                .build();
//        return ResponseEntity
//                .ok()
//                .body(employee);

        // 응답 헤더 생성 (custom header)
        HttpHeaders headers = new HttpHeaders();
        headers.set("department", "business");
        headers.set("blah-blah", "haha");

        return ResponseEntity
//                .status(200)        // ok 대신 status 값 넣어도 됨
//                .status(HttpStatus.OK)
                .ok()
                .headers(headers)       // 헤더 조작할 부분 있으면 작성
                .body(employee);
    }
}
