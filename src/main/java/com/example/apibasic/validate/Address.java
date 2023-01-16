// 2023-01-16
package com.example.apibasic.validate;

import lombok.*;

import javax.validation.constraints.*;

@Setter @Getter @ToString
public class Address {

    @NotBlank
    private String street;      // 도로명주소
    @NotBlank
    private String postCode;    // 우편번호
}
