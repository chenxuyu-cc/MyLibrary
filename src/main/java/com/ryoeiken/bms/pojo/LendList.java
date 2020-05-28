package com.ryoeiken.bms.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LendList {
    private Long sernum;

    private Long bookId;

    private String bookName;

    private Integer readerId;

    private String readerName;

    private String lendDate;

    private String backDate;


}