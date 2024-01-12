package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


    @MybatisTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //
    class FeedFavMapperTest {
        @Autowired
        private FeedFavMapper mapper;

                                                        //트랙잭션 >> 여러개의 업무를 하나로 보는것
    @Test
    public void insFeedFavTest() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(2);
        dto.setIuser(2);

        List<FeedFavDto> preResult2 = mapper.selFeedFavForTest(dto);
        assertEquals(0, preResult2.size(), "첫번째 insert전 미리 확인");

        int affectedRow = mapper.insFeedFav(dto);
        assertEquals(1, affectedRow, "첫번째 insert");

        List<FeedFavDto> result = mapper.selFeedFavForTest(dto);
        assertEquals(1, result.size(), "두번째 insert 확인");


    }

    @Test
    public void delFeedFavTest() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(223);
        dto.setIuser(1);

        int affectedRow1 = mapper.delFeedFav(dto);
        assertEquals(1, affectedRow1);

        int affectedRow2 = mapper.delFeedFav(dto);
        assertEquals(0, affectedRow2);

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        //assertNull(result2);
        assertEquals(0, result2.size());
    }

    @Test
    public void delFeedFavAllTest() {
        final int ifeed = 12;

        FeedFavDto selDto = new FeedFavDto();
        selDto.setIfeed(ifeed);
        List<FeedFavDto> selList = mapper.selFeedFavForTest(selDto);

        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(ifeed);
//        int delAffectedRows = mapper.delFeedByFav(dto);
//        assertEquals(selList.size(), delAffectedRows);

        List<FeedFavDto> selList2 = mapper.selFeedFavForTest(selDto);
        assertEquals(0, selList2.size());
    }
}