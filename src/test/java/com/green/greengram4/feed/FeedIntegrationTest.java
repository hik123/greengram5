package com.green.greengram4.feed;


import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
    @Rollback(false)
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(8);
        dto.setContents("통합 테스트 작업 중8");
        dto.setLocation("그린컴퓨터학원8");

        List<String> pics = new ArrayList<>();
        pics.add("https://ko.skyticket.com/guide/wp-content/uploads/2021/07/shutterstock_667925704-2fff.jpg");
        pics.add("https://www.jal.co.jp/kr/ko/guide-to-japan/plan-your-trip/tips/the-climate-seasons-and-weather-in-japan/_jcr_content/root/responsivegrid/sectioncontainer/image_1198557977_cop_876141487.coreimg.jpeg/1674436275739.jpeg");
//        dto.setPics(pics);
        // 레퍼런스 << 주소값 저장, 프러머티브 << 값이 저장됨

        String json = om.writeValueAsString(dto);
        System.out.println("Json: " + json);

        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON) //데이터를 json형태로 날릴때 필수
                                .content(json) //content << 바디
                )
                .andExpect(status().isOk()) //status 상태값
                .andDo(print()) //결과값 전부 프린트
                .andReturn();



                String content = mr.getResponse().getContentAsString();
                // { result : 1}

                ResVo vo = om.readValue(content, ResVo.class); // 기본생성자가 있어야됨!
                assertEquals(true, vo.getResult() > 0);
                //assertTrue(vo.getResult() > 0);


        // json 쓰는이유? 데이터로 만들기위해
        //빈등록 >> 스프링 컨테이너한테 대리로 객체화 부탁함 싱글턴으로

    }

    @Test
    //@Rollback(false)
    public void delFeed() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("ifeed", "2");
        params.add("iuser", "2");

        MvcResult mr = mvc.perform (
                        MockMvcRequestBuilders
                                .delete("/api/feed")
                                .params(params)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        ResVo vo = om.readValue(content, ResVo.class);
        assertEquals(1, vo.getResult());
    }
}
