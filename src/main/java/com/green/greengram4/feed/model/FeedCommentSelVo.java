package com.green.greengram4.feed.model;

import lombok.Data;

@Data
public class FeedCommentSelVo {
    private int ifeedComment;
    private String comment;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
}
