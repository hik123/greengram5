package com.green.greengram4.openapi.model;


import lombok.Data;

@Data
public class ApartmentTransactionDetailDto {
    private String dealYm;
    private String lawdCd;
    private int pageNo;
    private int numOfRows;
}
