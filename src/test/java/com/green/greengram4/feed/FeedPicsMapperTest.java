package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest // 맵퍼(인터페이스, xml)들을 객체화 해줌 (서비스,컨트롤러는 X)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //기존 h2 DB사용에서  yaml파일에 썼던 DB를 그대로 쓰겠다
class FeedPicsMapperTest {

    private FeedInsDto dto;

    public FeedPicsMapperTest() {
        this.dto = new FeedInsDto();
        this.dto.setIfeed(3);

        List<String> pics = new ArrayList();
        pics.add("aa.jpg");
        pics.add("bbb.jpg");
        pics.add("cccc.jpg");
//        this.dto.setPics(pics);

    }

    @Autowired // 테스트 때는 final(DI주입) 못붙임 Autowired사용
    private FeedPicsMapper mapper;

    @BeforeEach // 각각의 테스트 메서드 앞에 실행
    public void beforEach() {
        FeedDelDto delDto = new FeedDelDto();
        delDto.setIfeed(this.dto.getIfeed());
        delDto.setIuser(2);
//        int affectedRows = mapper.delFeedPics(delDto);
//        System.out.println("delrows :" + affectedRows);

    }

    /*
    @Test
    void insFeedPics() {
        List<String> preList = mapper.selFeedPicsAll(dto.getIfeed());
        assertEquals(0, preList.size());

        int insAffectedRow = mapper.insFeedPics(dto);
        assertEquals(dto.getPics().size(), insAffectedRow);

        List<String> afterList = mapper.selFeedPicsAll(dto.getIfeed());
        assertEquals(dto.getPics().size(), afterList.size());

        for(int i=0; i<dto.getPics().size(); i++) {
            assertEquals(dto.getPics().get(i), afterList.get(i));
        }

        //assertEquals(dto.getPics().get(0), afterList.get(0));
        //assertEquals(dto.getPics().get(1), afterList.get(1));
    }

     */
}