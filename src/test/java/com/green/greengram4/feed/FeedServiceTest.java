package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@ExtendWith(SpringExtension.class) // spring컨테이너를 올라오게끔 해줌
@Import({ FeedService.class }) // {service, aaa}를 빈등록, 객체화 해달라, 여러개도 가능
class FeedServiceTest {         // 테스트 종류 >> 메소드 단위, 슬라이스 클래스 단위, 통합 프로젝트 단위

    @MockBean //가짜, 실제로 객체화 되지만 안이 텅빈 내용만 있는 가벼운객체
    private FeedMapper mapper;

    @MockBean
    private FeedPicsMapper picsMapper;

    @MockBean
    private FeedFavMapper favMapper;

    @MockBean
    private FeedCommentMapper commentMapper;

    @Autowired //테스트에서 생성자 주입이 안되서 @Autowired사용
    private FeedService service;

    /*
    @Test
    public void postFeed() {
        when(mapper.insFeed(any())).thenReturn(1); // isFeed 호출 했을때 1 리턴
        when(picsMapper.insFeedPics(any())).thenReturn(2); //when 역할 안주면 디폴트값 리턴 list타입만 null 말고 크기0짜리 넘어옴

        FeedInsDto dto  = new FeedInsDto();
        dto.setIfeed(100);
        ResVo vo = service.postFeed(dto);
        assertEquals(dto.getIfeed(), vo.getResult());

        verify(mapper).insFeed(any()); // 실제로 호출했는지 안했는지만 검증 // 호출만되면 true
        verify(picsMapper).insFeedPics(any());

        FeedInsDto dto2  = new FeedInsDto();
        dto2.setIfeed(200);
        ResVo vo2 = service.postFeed(dto2);
        assertEquals(dto2.getIfeed(), vo2.getResult());
    }

     */

    @Test
    public void getFeedAll() {
        FeedSelVo feedSelVo1 = new FeedSelVo();
        feedSelVo1.setIfeed(1);
        feedSelVo1.setContents("일번 feedSelVo");

        FeedSelVo feedSelVo2 = new FeedSelVo();
        feedSelVo2.setIfeed(2);
        feedSelVo2.setContents("이번 feedSelVo");

        List<FeedSelVo> list = new ArrayList();
        list.add(feedSelVo1); //주소값 크기 8byte
        list.add(feedSelVo2);


        when( mapper.selFeedAll(any()) ).thenReturn(list); //가짜에 역할 부여
                                                //주소값 복사해서 줌
        List<String> feed1Pics = Arrays.stream( new String[]{"a.jpg", "b.jpg"}).toList(); // 배열을 리스트로 변경

        List<String> feed2Pics = new ArrayList();
        feed2Pics.add("가.jpg");
        feed2Pics.add("나.jpg");

        List<List<String>> picsList = new ArrayList<>();
        picsList.add(feed1Pics);
        picsList.add(feed2Pics);

        List<String>[] picsArr = new List[2];
        picsArr[0] = feed1Pics;
        picsArr[1] = feed2Pics;


        when( picsMapper.selFeedPicsAll(1) ).thenReturn(feed1Pics);
        when( picsMapper.selFeedPicsAll(2) ).thenReturn(feed2Pics);

        //-------------- ifeed(1) 댓글
        List<FeedCommentSelVo> cmtsFeed1 = new ArrayList<>();

        FeedCommentSelVo cmtVo1_1 = new FeedCommentSelVo();
        cmtVo1_1.setIfeedComment(1);
        cmtVo1_1.setComment("1-cmtVo1_1");

        FeedCommentSelVo cmtVo1_2 = new FeedCommentSelVo();
        cmtVo1_2.setIfeedComment(2);
        cmtVo1_2.setComment("2-cmtVo1_2");

        cmtsFeed1.add(cmtVo1_1);
        cmtsFeed1.add(cmtVo1_2);

        FeedCommentSelDto fcDto1 = new FeedCommentSelDto();
        fcDto1.setStartIdx(0);
        fcDto1.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto1.setIfeed(1);
        when( commentMapper.selFeedCommentAll(fcDto1) ).thenReturn(cmtsFeed1);

        //-------------- ifeed(2) 댓글
        List<FeedCommentSelVo> cmtsFeed2 = new ArrayList<>();

        FeedCommentSelVo cmtVo2_1 = new FeedCommentSelVo();
        cmtVo2_1.setIfeedComment(3);
        cmtVo2_1.setComment("3-cmtVo2_1");

        FeedCommentSelVo cmtVo2_2 = new FeedCommentSelVo();
        cmtVo2_2.setIfeedComment(4);
        cmtVo2_2.setComment("4-cmtVo2_2");

        FeedCommentSelVo cmtVo2_3 = new FeedCommentSelVo();
        cmtVo2_3.setIfeedComment(5);
        cmtVo2_3.setComment("5-cmtVo2_3");

        FeedCommentSelVo cmtVo2_4 = new FeedCommentSelVo();
        cmtVo2_4.setIfeedComment(6);
        cmtVo2_4.setComment("6-cmtVo2_4");

        cmtsFeed2.add(cmtVo2_1);
        cmtsFeed2.add(cmtVo2_2);
        cmtsFeed2.add(cmtVo2_3);
        cmtsFeed2.add(cmtVo2_4);

        FeedCommentSelDto fcDto2 = new FeedCommentSelDto();
        fcDto2.setStartIdx(0);
        fcDto2.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto2.setIfeed(2);
        when( commentMapper.selFeedCommentAll(fcDto2) ).thenReturn(cmtsFeed2);

        FeedSelDto dto = new FeedSelDto();
        List<FeedSelVo> result = service.getFeedAll(dto);

        assertEquals(list, result);

        for(int i=0; i<list.size(); i++) {
            FeedSelVo vo = list.get(i);
            assertNotNull(vo.getPics());

            List<String> pics = picsList.get(i);
            assertEquals(vo.getPics(), pics);

            List<String> pics2 = picsArr[i];
            assertEquals(vo.getPics(), pics2);
        }

        List<FeedCommentSelVo> commentsResult1 = list.get(0).getComments();
        assertEquals(cmtsFeed1, commentsResult1, "ifeed(1) 댓글 체크");
        assertEquals(0, list.get(0).getIsMoreComment(), "ifeed(1) isMoreComment 체크");

        List<FeedCommentSelVo> commentsResult2 = list.get(1).getComments();
        assertEquals(cmtsFeed2, commentsResult2, "ifeed(2) 댓글 체크");
        assertEquals(1, list.get(1).getIsMoreComment(), "ifeed(2) isMoreComment 체크");

        assertEquals(2, commentsResult1.size());
        assertEquals(3, commentsResult2.size());


    }



        /*
        FeedSelDto dto = new FeedSelDto();

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto.setIfeed(feedSelVo1.getIfeed());

        FeedCommentSelDto fcDto2 = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto.setIfeed(feedSelVo1.getIfeed());

        List<FeedCommentSelVo> commentsList = new ArrayList();
        FeedCommentSelVo commentVo = new FeedCommentSelVo();
        commentVo.setComment("ddd");
        FeedCommentSelVo commentVo2 = new FeedCommentSelVo();
        commentVo2.setComment("fff");
        FeedCommentSelVo commentVo3 = new FeedCommentSelVo();
        commentVo3.setComment("ggg");
        FeedCommentSelVo commentVo4 = new FeedCommentSelVo();
        commentVo4.setComment("ㅎㅎㅎ");
        FeedCommentSelVo commentVo5 = new FeedCommentSelVo();
        commentVo5.setComment("ㅎㅎㅎ");

        commentsList.add(commentVo);
        commentsList.add(commentVo2);
        commentsList.add(commentVo3);
        commentsList.add(commentVo4);

        when(commentMapper.selFeedCommentAll(fcDto)).thenReturn(commentsList);
        when(commentMapper.selFeedCommentAll(fcDto2)).thenReturn(commentsList);

        List<FeedSelVo> result = service.getFeedAll(dto);
        log.info("result {}",result);


        assertEquals(list, result);

        for(int i=0; i<result.size(); i++) {
            FeedSelVo vo = result.get(i);
            assertNotNull(vo.getPics());

            List<String> pics = picsList.get(i);
            assertEquals(vo.getPics(), pics);

            List<String> pics2 = picsArr[i];
            assertEquals(vo.getPics(),pics2);

            assertEquals(4, result.get(i).getComments().size());
            assertEquals(1, result.get(i).getIsMoreComment(),"kj");
        }

        assertEquals(list.get(1).getComments(), commentsList);

         */

        /*
        for(int i=0; i<result.size(); i++) {
            FeedSelVo rVo = result.get(i);
            FeedSelVo pVo = list.get(i);

            assertEquals(pVo.getIfeed(), rVo.getIfeed());
            assertEquals(pVo.getContents(), rVo.getContents());
        }
         */

        //ex) select 했을때 20개의 레코드가 넘어왔을때 21번 select 하는것을 n+1문제 코드상으론 깔끔한데 성능이 느림

}
