package com.ryoeiken.bms.mapper;

import com.ryoeiken.bms.pojo.LendList;
import com.ryoeiken.bms.pojo.LendListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LendListMapper {
    int countByExample(LendListExample example);

    int deleteByExample(LendListExample example);

    int deleteByPrimaryKey(Long sernum);

    int insert(LendList record);

    int insertSelective(LendList record);

    String queryNameByReaderId(@Param("readerId") Integer readerId);

    List<LendList> selectByExample(LendListExample example);

    List<LendList> selectLog();

    List<LendList> selectMyLog(@Param("readerId") Integer readerId);

    LendList selectByPrimaryKey(Long sernum);

    int updateByExampleSelective(@Param("record") LendList record, @Param("example") LendListExample example);

    int updateReturnDate(@Param("record") LendList record);

    int updateByExample(@Param("record") LendList record, @Param("example") LendListExample example);

    int updateByPrimaryKeySelective(LendList record);

    int updateByPrimaryKey(LendList record);
}