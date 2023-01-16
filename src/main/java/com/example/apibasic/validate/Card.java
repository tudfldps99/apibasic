// 2023-01-16
package com.example.apibasic.validate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.YearMonth;

@Setter @Getter @ToString
public class Card {

    @NotBlank
    private String cardNo;      // 카드번호
    @JsonFormat(pattern = "yyyy-MM")
    @Future     // 미래 날짜인지 검사
    private YearMonth validMonth;       // 카드 만료 기간

}
