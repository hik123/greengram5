package com.green.greengram4.feed;


import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name = "피드 API", description = "피드 관련 처리") // description 에서 <br> >> 개행
public class FeedController {
    private final FeedService service;

    @Operation(summary = "피드 등록", description = "피드 등록 처리")
    @PostMapping
    public ResVo postFeed(@RequestBody FeedInsDto dto) {
        ResVo vo = service.postFeed(dto);
        return vo;
    }

    @Operation(summary = "피드 리스트", description = "전체 피드 리스트")
    @GetMapping
    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
        /*FeedSelDto dto = FeedSelDto.builder()    // builder패턴 쓰면 객체화 불가능
                .rowCount(Const.FEED_COUNT_PER_PAGE)
                .startIdx((page-1) * Const.FEED_COUNT_PER_PAGE)
                .build();
        return service.getFeedAll(dto); */
        List<FeedSelVo> list = service.getFeedAll(dto);
        return list;
    }

    @GetMapping("/fav")
    public ResVo toggleFeedFav(FeedFavDto dto) {
        return service.toggleFeedFav(dto);
    }

    //ifeed, iuser
    @DeleteMapping
    public ResVo DelFeed(FeedDelDto dto) {
        log.info("dto : {}", dto);
        ResVo vo = service.DelFeed(dto);
        return vo;
    }
}
