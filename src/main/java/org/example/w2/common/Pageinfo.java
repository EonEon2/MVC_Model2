package org.example.w2.common;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString

public class Pageinfo {

    private int page; //current page

    private int start; //시작페이지
    private int end; //마지막페이지

    private boolean prev; //이전페이지가 있을지
    private boolean next; //다음페이지가 있을지


    public Pageinfo(int page, int size, int total){
        this.page = page <= 0 ? 1 : page;

        end = (int)(Math.ceil(this.page/10.0) * 10);

        start = end - 9; //시작페이지는 마지막-9

        prev = start == 1 ? false : true; // 시작페이지가 1이면 이전페이지가 false

        if(end*size < total) { //전체 데이터가 마지막페이지 * 페이지당 게시물 개수보다 크면
            next = true; // 정상
        }
        else{
            next = false;
            end = (int)(Math.ceil(total/(double)size));
        }
    }
}
