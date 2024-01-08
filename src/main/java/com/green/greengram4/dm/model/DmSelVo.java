package com.green.greengram4.dm.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DmSelVo {
    private int idm;
    private String lastMsg;
    private String lastMsgAt;
    private int otherPersonIuser;
    private String otherPersonNm;
    private String otherPersonPic;
}
