package com.chunjae.project05.util;

import lombok.*;

@Data
public class Page {
    private int pageCount = 5;                          // 페이징 출력 갯수
    private int blockStartNum = 0;                      // 해당 페이지 출력 갯수 시작 숫자
    private int blockLastNum = 0;                       // 해당 페이지 출력 갯수 종료 숫자
    private int lastPageNum = 0;                        // 마지막 페이징 처리 숫자
    private int curPageNum = 1;                         // 현재 페이지 숫자
    private int postCount = 10;                         // 한 페이지 당 리스트 출력 갯수
    private int postStart;                              // sql 문에 입력될 시작 숫자
    private int pageBlockNum = 1;                       // 넌 뭐냐3...?
    private int totalBlockNum = 1;                      // 총 페이징 처리 된 숫자
    private int totalPageCount = 1;                     // 총 ..?뭐냐..?
    private String searchType = "";                     // 입력된 검색 타입
    private String searchKeyword = "";                  // 입력된 검색 키워드

    // 전체 페이지 개수 구하는 메소드
    public void makePostStart(int curPage, int total){

        this.postStart = (curPage - 1) * this.postCount;
        this.pageBlockNum = (int)Math.floor(curPage / pageCount);

        int comp = pageCount * postCount;
        if( total % comp == 0 ) {
            this.totalBlockNum = (int)Math.floor(total/comp);
        } else {
            this.totalBlockNum = (int)Math.floor(total/comp) + 1;
        }
        if( total % postCount == 0 ){
            totalPageCount = (int)Math.floor(total/postCount);
        } else {
            totalPageCount = (int)Math.floor(total/postCount)+1;
        }
    }

    // block을 생성
    // 현재 페이지가 속한 block의 시작 번호, 끝 번호를 계산하는 메소드
    public void makeBlock(int curPage, int total){
        int blockNum = 0;

        blockNum = (int)Math.floor((curPage-1)/ pageCount);
        blockStartNum = (pageCount * blockNum) + 1;

        int comp = 0;
        if( total % postCount == 0 ){
            comp = (int)Math.floor(total/ postCount);
        } else {
            comp = (int)Math.floor(total/ postCount)+1;
        }
        blockLastNum = blockStartNum + (pageCount-1);
        if(blockLastNum >= comp){
            blockLastNum = comp;
        }
    }

    // 총 페이지의 마지막 번호 구하는 메소드
    public void makeLastPageNum(int total) {
        if( total % pageCount == 0 ) {
            lastPageNum = (int)Math.floor(total/pageCount);
        }
        else {
            lastPageNum = (int)Math.floor(total/pageCount) + 1;
        }
    }
}