package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    private int ifeedComment;
    //@JsonIgnore
    private int iuser;
    private int ifeed;
    private String comment;
}

