package com.green.greengram4.feed.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class FeedInsDto { //feed 생성할때 프론트로부터 받아야하는 자료
    @JsonIgnore
    private int ifeed;
    //@Schema(hidden = true)
    private int iuser;
    private String contents;
    private String location;
    private List<String> pics;
}
