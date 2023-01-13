// 2023-01-13
package com.example.apibasic.req;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller     // 클라이언트가 보낸 요청을 받을 수 있음
@ResponseBody
@Slf4j      // 로그 라이브러리
public class ApiController2 {

    // 요청 파라미터 읽기
    @GetMapping("/param1")
    public String param1(
            // 파라미터 키 값("username")과 변수명(username)이 같으면 아노테이션 생략 가능
            //@RequestParam("username") String username,
            String username,
            @RequestParam("age") int userAge
    ) {
        log.info("/param1?username={}&age={} GET!!", username, userAge);
        log.info("내 이름은 {} 이고, 나이는 {} 세다!!", username, userAge);
        return "";
    }

    // 파라미터로 들어오는 데이터가 10개라면? 10개 변수 모두 나열은 좋지 않음 --> 커맨드 객체
    @GetMapping("/param2")
    public String param2(OrderInfo orderInfo) {
        log.info("/param2?orderNo={}&goodsName={}&goodsAmount={}"
                , orderInfo.getOrderNo()
                , orderInfo.getGoodsName()
                , orderInfo.getGoodsAmount()
        );
        log.info("주문 정보 : {}", orderInfo);
        return "";
    }

    // 요청 바디 읽기 - @RequestBody      (주소창에 있으면 @RequestParam)
    @PostMapping("/req-body")
    public String reqBody(@RequestBody OrderInfo orderInfo) {
        log.info("========= 주문 정보 ==========");
        log.info("# 주문번호: {}", orderInfo.getOrderNo());
        log.info("# 상품명: {}", orderInfo.getGoodsName());
        log.info("# 수량: {}", orderInfo.getGoodsAmount());
        return "";
    }

    // 커맨드 객체 (주의사항 : 클라이언트가 보낸 파라미터 이름과 필드명이 정확히 일치해야 함(대소문자까지))
    @Setter @Getter @ToString
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class OrderInfo {
        private Long orderNo;
        private String goodsName;
        private int goodsAmount;
    }
}
