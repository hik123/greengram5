package com.green.greengram4.feed.model;

import lombok.Data;

import java.util.List;

@Data
public class FeedSelVo {
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
    private List<String> pics;
    private int isFav; //1: 좋아요 했음, 0: 좋아요 아님
    private List<FeedCommentSelVo> comments;
    private int isMoreComment; //0: 댓글이 더 없음. 1: 댓글이 더 있음
}
