package com.green.greengram4.feed;


import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

    public ResVo postFeed(FeedInsDto dto) {
        int feedAffectedRow = mapper.insFeed(dto);
        log.info("feedAffectedRows: {} ", feedAffectedRow);

        int feedPicsAffectedRow = picsMapper.insFeedPics(dto);
        log.info("feedPicsAffectedRows: {} ", feedPicsAffectedRow);
        return new ResVo(dto.getIfeed());
    }

    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {                     // n+1문제 / for문에서 feed가 4개일때 for문 5번반복?
        List<FeedSelVo> list = mapper.selFeedAll(dto); // list에 feed 싹다 담음

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);

        for(FeedSelVo vo : list) {      // 추가로 사진도 싹다 담음
            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
            vo.setPics(pics);           // setter메소드로 FeedSelVo의 pics에 담아줌

            fcDto.setIfeed(vo.getIfeed());              // FeedCommentSelDto에 ifeed값 담고
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);  //comment 전부 comments에 담고
            if(comments.size() == Const.FEED_COMMENT_FIRST_CNT) {      // 댓글4일때 3개까지보이게
                vo.setIsMoreComment(1);             //IsMoreComment 0: 댓글이 더 없음, 1: 댓글이 더있음
                comments.remove(comments.size() - 1);
            }
            vo.setComments(comments);
        }
        return list;

        //ex) select 했을때 20개의 레코드가 넘어왔을때 21번 select 하는것을 n+1문제, 코드상으론 깔끔한데 성능이 느림
    }

    // ----------- t_feed_fav
    public ResVo toggleFeedFav(FeedFavDto dto) {
        //ResVo - result값은 삭제했을 시(좋아요 취소) 0리턴,
        // 등록했을 시 1리턴.
        int delAffectedRow = favMapper.delFeedFav(dto);
        if(delAffectedRow == 1) {
            return new ResVo(Const.FEED_FAV_DEL);
        }
        int insAffectedRow = favMapper.insFeedFav(dto);
        return new ResVo(Const.FEED_FAV_ADD);
    }

    public ResVo DelFeed(FeedDelDto dto) {
        int result = mapper.selFeedConfirm(dto);
        log.info("result : {}", result);
        if(result == dto.getIuser()) {
            picsMapper.delFeedPics(dto);
            favMapper.delFeedByFav(dto);
            commentMapper.delFeedComment(dto);
            mapper.delFeed(dto);
            return new ResVo(1);
        }
        return new ResVo(0);
    }
}
