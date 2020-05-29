package com.ryoeiken.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryoeiken.bms.mapper.BookInfoMapper;
import com.ryoeiken.bms.pojo.Book;
import com.ryoeiken.bms.pojo.BookInfo;
import com.ryoeiken.bms.pojo.BookInfoExample;
import com.ryoeiken.bms.pojo.PageResult;
import com.ryoeiken.bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookInfoMapper bookInfoMapper;

    @Override
    public PageResult<BookInfo> getAllBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookInfo> books = this.bookInfoMapper.selectByExample(null);
        PageInfo<BookInfo> pageInfo = new PageInfo<>(books);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        PageResult<BookInfo> page = new PageResult<>();
        page.setList(books);
        page.setPageNum(pageNum);
        page.setPages(pages);
        page.setPageSize(pageSize);
        page.setTotal(total);

        return page;
    }

    @Override
    public PageResult<Book> queryAllBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Book> books = bookInfoMapper.queryAllBooks();
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        PageResult<Book> page = new PageResult<>();
        page.setList(books);
        page.setPageNum(pageNum);
        page.setPages(pages);
        page.setPageSize(pageSize);
        page.setTotal(total);

        return page;
    }

    @Override
    public boolean matchBook(String searchWord) {
        BookInfoExample bookInfoExample = new BookInfoExample();
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andNameLike("%" + searchWord + "%");
        try {
            this.bookInfoMapper.countByExample(bookInfoExample);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<BookInfo> queryBook(String searchWord) {
        BookInfoExample bookInfoExample = new BookInfoExample();
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andNameLike("%" + searchWord + "%");
        List<BookInfo> books = this.bookInfoMapper.selectByExample(bookInfoExample);
        return books;
    }

    @Override
    public List<Book> queryBookNew(String searchWord) {
        List<Book> books = this.bookInfoMapper.selectByExampleNew(searchWord);
        return books;
    }

    @Override
    public boolean addBook(BookInfo bookInfo) {
        try {
            this.bookInfoMapper.insertSelective(bookInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public BookInfo getBook(Long bookId) {
        BookInfo bookInfo = this.bookInfoMapper.selectByPrimaryKey(bookId);
        return bookInfo;
    }

    @Override
    public boolean editBook(BookInfo bookInfo) {
        try {
            this.bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int deleteBook(Long bookId) {
        Book book = queryOne(bookId);
        int updateCount = 0;
        if (book.getNum() == 0 ){
            updateCount = bookInfoMapper.deleteByPrimaryKey(bookId);
        }else {
            int num = book.getNum()-1;
            updateCount = bookInfoMapper.updateBookNum(bookId,num);
        }
        return updateCount;
    }

    @Override
    public List<BookInfo> bookRank() {
        BookInfoExample bookInfoExample = new BookInfoExample();
        bookInfoExample.setOrderByClause("count DESC LIMIT 5");
        return this.bookInfoMapper.selectByExample(bookInfoExample);
    }

    @Override
    public Book queryOne(Long bookId) {
        return bookInfoMapper.queryOneBook(bookId);
    }

}
