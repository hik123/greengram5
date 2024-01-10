package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsDto p);
    List<FeedSelVo> selFeedAll(FeedSelDto p);
    int delFeed(FeedDelDto dto);
}

class FeedMapperImpl implements FeedMapper {


    @Override
    public int insFeed(FeedInsDto p) {
        return 0;
    }

    @Override
    public List<FeedSelVo> selFeedAll(FeedSelDto p) {

        FeedSelVo feedSelVo1 = new FeedSelVo();
        feedSelVo1.setIfeed(1);
        feedSelVo1.setContents("일번 feedSelVo");

        FeedSelVo feedSelVo2 = new FeedSelVo();
        feedSelVo2.setIfeed(2);
        feedSelVo2.setContents("이번 feedSelVo");

        List<FeedSelVo> list = new ArrayList();
        list.add(feedSelVo1);
        list.add(feedSelVo2);

        return list;
    }

    @Override
    public int delFeed(FeedDelDto dto) {
        return 0;
    }
}