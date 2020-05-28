package com.ryoeiken.bms.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book implements Serializable {
    private Long bookId;

    private String name;

    private String author;

    private String publish;

    private String isbn;

    private String language;

    private BigDecimal price;

    private Date pubdate;

    private Integer classId;

    private Integer pressmark;

    private Short state;

    private Integer count;

    private String introduction;

    private Integer num;


}