package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedCommentInsDto;
import com.green.greengram4.feed.model.FeedCommentSelDto;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public ResVo postFeedComment(FeedCommentInsDto dto) {
        int affectedRows = mapper.insFeedComment(dto);
        return new ResVo(dto.getIfeedComment());
    }

    public List<FeedCommentSelVo> getFeedCommentAll(FeedCommentSelDto dto) {
        dto.setStartIdx(3);
        dto.setRowCount(999);
        return mapper.selFeedCommentAll(dto);
    }
}
