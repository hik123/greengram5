package com.green.greengram4.dm;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.dm.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dm")
public class DmController {

    private final DmService service;

    @GetMapping
    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return service.getDmAll(dto);
    }

    @PostMapping
    public DmSelVo postDm(@RequestBody DmInsDto dto) {
        return service.postDm(dto);
    }

    //----------------------------- t_dm_msg
    @PostMapping("/msg")
    public ResVo postDmMsg(@RequestBody DmMsgInsDto dto) {
        return service.postDmMsg(dto);
    }

    @GetMapping("/msg")
    public List<DmMsgSelVo> getDmMsgAll(DmMsgSelDto dto) {
        log.info("dto: {}", dto);
        return service.getMsgAll(dto);
    }

    @DeleteMapping("/msg")
    public ResVo delDmMsg(DmMsgDelDto dto) {
        log.info("dto: {}", dto);
        return service.delDmMsg(dto);
    }
}
