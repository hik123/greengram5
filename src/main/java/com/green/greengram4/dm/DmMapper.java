package com.green.greengram4.dm;

import com.green.greengram4.dm.model.*;
import com.green.greengram4.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DmMapper {
    //----------------------- t_dm
    int insDm(DmInsDto dto);
    List<DmSelVo> selDmAll(DmSelDto dto);
    UserEntity selOtherPersonByLoginUser(DmMsgInsDto dto);
    int updDmLastMsg(DmMsgInsDto dto);
    int updDmLastMsgAfterDelByLastMsg(DmMsgDelDto dto);

    //----------------------- t_dm_user
    int insDmUser(DmInsDto dto);
    Integer selDmUserCheck(DmInsDto dto);

    //----------------------- t_dm_msg
    int insDmMsg(DmMsgInsDto dto);
    List<DmMsgSelVo> selDmMsgAll(DmMsgSelDto dto);
    int delDmMsg(DmMsgDelDto dto);
}
