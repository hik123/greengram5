package com.green.greengram4.feed.model;


import lombok.Data;

import java.util.List;

@Data
public class FeedSelVo {  // 하나의 feed 나오게 할때 쓰는 객체
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private String writerIuser;
    private String writerNm;
    private String writerPic;
    private List<String> pics;
    private int isFav; // 1:좋아요했음, 0:좋아요 아님
    private List<FeedCommentSelVo> comments;
    private int isMoreComment; // 0: 댓글이 더 없음, 1: 댓글이 더있음

}
