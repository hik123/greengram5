package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.CharEncodingConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(CharEncodingConfig.class)
//@MockMvcConfig //한글이 깨지지 않기위해 줌
@WebMvcTest(FeedController.class) //spring 컨테이너 올려줌 빈등록된 컨트롤러들을
class FeedControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @MockBean private FeedService service;            //객체형은 null, list는 사이즈0짜리 리턴

    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(2);
        //when(service.postFeed(any())).thenReturn(result);
        given(service.postFeed(any())).willReturn(result);  //when given 성능은 똑같

        FeedInsDto dto = new FeedInsDto();

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/feed")
                        .contentType(MediaType.APPLICATION_JSON) //데이터를 json형태로 날릴때 필수
                        .content(mapper.writeValueAsString(dto)) //content << 바디
        )
        .andExpect(status().isOk()) //status 상태값
        .andExpect(content().string(mapper.writeValueAsString(result))) //content문자열이 내가 기대한 문자열과 같나?           //content import >> mvcResultmatcher?
        .andDo(print()); //결과값 전부 프린트

        verify(service).postFeed(any());
    }

    @Test
    void getFeedAll() throws Exception {

        List<FeedSelVo> list = new ArrayList();
        FeedSelVo vo = new FeedSelVo();
        vo.setContents("ㅎㅎㅎㅎㅎ");
        vo.setIfeed(1);
        FeedSelVo vo3 = new FeedSelVo();
        vo.setContents("안녕하세요2");
        vo.setIfeed(3);
        FeedSelVo vo2 = new FeedSelVo();
        vo.setContents("안녕하세요3");
        vo.setIfeed(2);

        list.add(vo);
        list.add(vo2);
        list.add(vo3);

        given(service.getFeedAll(any())).willReturn(list);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("page", "2");
        params.add("loginedIuser", "4");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/feed") // api/feed?page=2&loginedIuser=4
                        .params(params)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(list)))
                .andDo(print());

        verify(service).getFeedAll(any());
    }

    @Test
    void delFeed () throws Exception {
        ResVo vo = new ResVo(1);
//        given(service.DelFeed(any())).willReturn(vo);

        FeedDelDto dto = new FeedDelDto();

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/feed")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(vo)));

//        verify(service).DelFeed(any());
    }

    @Test
    void toggleFeedFav() {

    }
}