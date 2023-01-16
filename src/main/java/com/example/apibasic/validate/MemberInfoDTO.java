// 2023-01-16
package com.example.apibasic.validate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class MemberInfoDTO {

    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank @Size(min = 2, max = 5)
    private String userName;
    @JsonFormat(pattern = "yyMMdd")     // 생년월일 6자리로 표현
    @Past   // 과거 날짜인지 검사
    private LocalDate birthOfDate;      // 생일

    @Valid      // 컴포지션 객체(ex - Address address) 입력값 검증, @Valid 생략 가능 (구버전에서는 생략 불가능)
    private Address address;    // 주소 정보 (도로명주소 + 우편번호)
    @Valid
    private Card card;          // 결제 카드 정보 (카드번호 + 카드만료기간)
}
