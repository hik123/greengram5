package com.green.greengram4.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelDto {
    private String providerType;
    private String uid;
    private int iuser;
}
