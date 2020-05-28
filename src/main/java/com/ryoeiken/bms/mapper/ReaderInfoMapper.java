package com.ryoeiken.bms.mapper;

import com.ryoeiken.bms.pojo.ReaderInfo;
import com.ryoeiken.bms.pojo.ReaderInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReaderInfoMapper {

    List<ReaderInfo> queryReaders(String name);

    int countByExample(ReaderInfoExample example);

    int deleteByExample(ReaderInfoExample example);

    int deleteByPrimaryKey(Integer readerId);

    int insert(ReaderInfo record);

    int insertSelective(ReaderInfo record);

    List<ReaderInfo> selectByExample(ReaderInfoExample example);

    ReaderInfo selectByPrimaryKey(Integer readerId);

    int updateByExampleSelective(@Param("record") ReaderInfo record, @Param("example") ReaderInfoExample example);

    int updateByExample(@Param("record") ReaderInfo record, @Param("example") ReaderInfoExample example);

    int updateByPrimaryKeySelective(ReaderInfo record);

    int updateByPrimaryKey(ReaderInfo record);

    int updatePassWord(@Param("readerId") Integer readerId);
}