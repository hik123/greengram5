package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class FeedInsDto {
    @JsonIgnore
    //@Schema(hidden = true)
    private int ifeed;
    @JsonIgnore
    private int iuser;
    private String contents;
    private String location;
    private List<String> pics;
}
