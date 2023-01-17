// 2023-01-17
package com.example.apibasic.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

// 클라이언트가 보낸 페이지 정보를 가진 객체 (Page)
@Getter
@AllArgsConstructor
@Builder @ToString
public class PageRequestDTO {

    private int page;           // 요청한 페이지 번호
    private int sizePerPage;    // 한 페이지에 보여줄 데이터 수

    // 초기 렌더링 시(초기 요청 시)에 사용할 데이터
    public PageRequestDTO() {       // 기본생성자 : 초기값 설정 (안하면 page, sizePerPage 초기값 = 0)
        this.page = 1;
        this.sizePerPage = 10;
    }

    public void setPage(int page) {
        if (page < 0 || page > Integer.MAX_VALUE) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public void setSizePerPage(int sizePerPage) {
        if (sizePerPage < 10 || sizePerPage > 100) {
            this.sizePerPage = 10;
            return;
        }
        this.sizePerPage = sizePerPage;
    }
}
