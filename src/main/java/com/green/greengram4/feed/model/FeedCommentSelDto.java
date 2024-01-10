package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FeedCommentSelDto {
    private int ifeed;
    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int rowCount;
}
