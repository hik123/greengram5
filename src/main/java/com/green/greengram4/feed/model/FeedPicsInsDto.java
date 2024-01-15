package com.green.greengram4.feed.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedPicsInsDto {
    private int ifeed;
    private List<String> pics = new ArrayList();

}
