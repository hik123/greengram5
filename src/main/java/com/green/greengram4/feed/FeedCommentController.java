package com.green.greengram4.feed;


import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedCommentInsDto;
import com.green.greengram4.feed.model.FeedCommentSelDto;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public ResVo postFeedComment(@RequestBody FeedCommentInsDto dto) {
        return service.postFeedComment(dto);
    }

    @GetMapping
    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed) { //4~999까지의 레코드만 리턴될 수 있도록
        FeedCommentSelDto dto = new FeedCommentSelDto();
        dto.setIfeed(ifeed);
        dto.setStartIdx(3);
        dto.setRowCount(999);
        return service.getFeedCommentAll(dto);
    }
}
