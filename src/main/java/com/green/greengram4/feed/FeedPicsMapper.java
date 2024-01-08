package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicsMapper {
    int insFeedPics(FeedInsDto dto);

    List<String> selFeedPicsAll(int ifeed);  //컬럼이 2개이상 부터는 list말고 객체사용

    int delFeedPics(FeedDelDto dto);

}
