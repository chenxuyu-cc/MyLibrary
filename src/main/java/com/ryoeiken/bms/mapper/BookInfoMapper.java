package com.ryoeiken.bms.mapper;

import com.ryoeiken.bms.pojo.Book;
import com.ryoeiken.bms.pojo.BookInfo;
import com.ryoeiken.bms.pojo.BookInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookInfoMapper {
    int countByExample(BookInfoExample example);

    int deleteByExample(BookInfoExample example);

    int deleteByPrimaryKey(Long bookId);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    List<BookInfo> selectByExampleWithBLOBs(BookInfoExample example);

    List<BookInfo> selectByExample(BookInfoExample example);



    BookInfo selectByPrimaryKey(Long bookId);

    int updateByExampleSelective(@Param("record") BookInfo record, @Param("example") BookInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") BookInfo record, @Param("example") BookInfoExample example);

    int updateByExample(@Param("record") BookInfo record, @Param("example") BookInfoExample example);

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKeyWithBLOBs(BookInfo record);

    int updateByPrimaryKey(BookInfo record);

    List<Book> queryAllBooks();

    Book queryOneBook(@Param("bookId") Long bookId);

    int updateBooks(@Param("book") Book book);

    List<Book> selectByExampleNew(@Param("searchWord") String searchWord);

    int updateBookNum(@Param("bookId") Long bookId, @Param("num") int num);
}