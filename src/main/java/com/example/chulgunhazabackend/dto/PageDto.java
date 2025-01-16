package com.example.chulgunhazabackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageDto<T> {
    private List<T> contents;
    private long totalElements;  // 전체 게시물 수
    private int totalPages;      // 전체 페이지 수
    private int currentPage;     // 현재 페이지
    private boolean hasNextPage; // 다음 페이지가 있는지 여부
    private boolean isFirstPage; // 첫 번째 페이지인지 여부
    private boolean isLastPage;  // 마지막 페이지인지 여부

    public PageDto(Page<T> page) {
        this.contents = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();  // 현재 페이지 번호
        this.hasNextPage = page.hasNext();    // 다음 페이지가 있는지 여부
        this.isFirstPage = page.isFirst();    // 첫 번째 페이지인지 여부
        this.isLastPage = page.isLast();      // 마지막 페이지인지 여부
    }
}
