package com.ryoeiken.bms.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    // 总记录数
    private long total;
    // 总页数
    private int pages;
    // 数据列表
    private List<T> list;
    // 当前页码
    private int pageNum;
    //  每页的行数
    private int pageSize;

}
