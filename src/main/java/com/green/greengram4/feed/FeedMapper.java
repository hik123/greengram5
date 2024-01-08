package com.green.greengram4.feed;

import com.green.greengram4.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsDto dto);
    List<FeedSelVo> selFeedAll(FeedSelDto dto);
    int selFeedConfirm(FeedDelDto dto);
    int delFeed(FeedDelDto dto);
}
