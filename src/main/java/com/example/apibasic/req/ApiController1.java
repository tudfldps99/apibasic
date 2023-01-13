// 2023-01-13
package com.example.apibasic.req;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller     // 클라이언트가 보낸 요청을 받을 수 있음
@ResponseBody
@Slf4j      // 로그 라이브러리
@RequestMapping("/say")     // 접두사 통일 ex -  /say/hello
public class ApiController1 {

    // 요청을 받아서 처리할 메서드 (기본적으로 public)
    // 클라이언트 요청 시에는 http://localhost:8181/say/hello 와 같이 요청
    @RequestMapping(value = "/hello", method={RequestMethod.GET, RequestMethod.POST})
    public String hello(HttpServletRequest request) {
//        System.out.println("hello spring! - " + request.getMethod());
        // Slf4j 사용
        log.trace("trace 로그");
        log.debug("debug 로그");
        log.info("hello spring! - {}", request.getMethod());
        log.warn("warn 로그");
        log.error("error 로그");

        return "";
    }

    // GET 요청만 따로 처리
//    @RequestMapping(value="/greet", method=RequestMethod.GET)
    @GetMapping("/greet")
    public String greet() {
        log.info("/greet GET 요청!");
        return "";
    }

    @PostMapping("/greet")
    public String greet2() {
        log.info("/greet POST 요청!");
        return "";
    }

    // 요청 헤더 읽기
    @GetMapping("/header")
    public String header(HttpServletRequest request) {
        String host = request.getHeader("HOST");
        String accept = request.getHeader("Accept");
        String pet = request.getHeader("pet");

        log.info("===== header info =====");
        log.info("# headers : {}", host);
        log.info("# accept : {}", accept);
        log.info("# pet : {}", pet);

        return "";
    }
}
