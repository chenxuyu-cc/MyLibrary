package com.ryoeiken.bms.service;

import com.ryoeiken.bms.pojo.PageResult;
import com.ryoeiken.bms.pojo.ReaderInfo;

import java.util.List;

public interface ReaderInfoService {
    PageResult<ReaderInfo> readerInfos(Integer pageNum, Integer pageSize);

    List<ReaderInfo> queryReaders(String searchWord);

    boolean deleteReaderInfo(Integer readerId);

    ReaderInfo getReaderInfo(Integer readerId);

    boolean editReader(ReaderInfo readerInfo);

    boolean addReader(ReaderInfo readerInfo);

    boolean reBackPassWord(Integer readerId);
}
