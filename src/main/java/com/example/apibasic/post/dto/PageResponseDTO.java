// 2023-01-17
package com.example.apibasic.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

// 클라이언트에게 응답할 페이지 정보
/*
    pageInfo : {
        "startPage" : 1,
        "endPage" : 10,
        "currentPage" : 3,
        "prev" : false,
        "next" : true,
        "totalCount" : 500
    }
 */
@ToString @Setter @Getter
public class PageResponseDTO<T> {

    private int startPage;
    private int endPage;
    private int currentPage;

    private boolean prev;
    private boolean next;

    private int totalCount;

    // 페이지 개수
    private static final int PAGE_COUNT = 10;       // 한 페이지에 10개씩이므로 10

    public PageResponseDTO(Page<T> pageData) {      // <T> 범용적으로 사용하기 위해 제너릭으로
        this.totalCount = (int)pageData.getTotalElements();
        this.currentPage = pageData.getPageable().getPageNumber() + 1;      // PageTest.java 에서 -1 해주면서 DB 에 넣었기 때문에, 다시 클라이언트로 보낼 때는 +1
        this.endPage = (int)(Math.ceil((double)currentPage / PAGE_COUNT) * PAGE_COUNT);     // Math.ceil : 올림
        this.startPage = endPage - PAGE_COUNT + 1;

        // 페이지 마지막 구간에서 endPage 값 보정
        // 실제 끝페이지 숫자를 구함
//        int realEnd = (int)(Math.ceil((double)totalCount / pageData.getSize()));      // 실제 끝페이지 계산
        int realEnd = pageData.getTotalPages();

        // 언제 보정해야돼? 마지막 구간에서만
        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = startPage > 1;      // startPage 가 1보다 크면 prev - true, startPage 가 1이면 prev - false
        this.next = endPage < realEnd;       // 마지막 구간에서만 next - false
    }
}
