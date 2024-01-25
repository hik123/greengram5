package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.CharEncodingConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelVo;
import com.green.greengram4.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(CharEncodingConfig.class)
//@MockMvcConfig //한글이 깨지지 않기위해 줌
@WebMvcTest(FeedController.class) //spring 컨테이너 올려줌 빈등록된 컨트롤러들을
class FeedControllerTest {   //슬라이스 테스트

    @Autowired private MockMvc mvc; //테스트때 필요
    @Autowired private ObjectMapper mapper; //json to object 젝슨
    @MockBean private FeedService service; //mockbean 가짜 빈         //객체형은 null, list는 사이즈0짜리 리턴
    @MockBean private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(7);
        //when(service.postFeed(any())).thenReturn(result);
        //given(service.postFeed(any())).willReturn(result);

        FeedInsDto dto = new FeedInsDto();
        //String json = mapper.writeValueAsString(dto);
        //System.out.println("json: " + json);
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(dto))
                )
                //.andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());

        verify(service).postFeed(any());
    }

    @Test
    void getFeedAll() throws Exception { // 테스트는 given세팅 when실행 then검증

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
                .andExpect(content().string(mapper.writeValueAsString(list))) //리턴한걸 실제로 썼는지?
                .andDo(print());

        verify(service).getFeedAll(any());
    }

    @Test
    void delFeed () throws Exception {
        ResVo vo = new ResVo(1);
        given(service.delFeed(any())).willReturn(vo);

        FeedDelDto dto = new FeedDelDto();

        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/feed")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(vo)));

        verify(service).delFeed(any());
    }

    @Test
    void toggleFeedFav() {

    }
}