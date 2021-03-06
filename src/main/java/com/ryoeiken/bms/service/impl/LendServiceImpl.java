package com.ryoeiken.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryoeiken.bms.mapper.BookInfoMapper;
import com.ryoeiken.bms.mapper.LendListMapper;
import com.ryoeiken.bms.pojo.*;
import com.ryoeiken.bms.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LendServiceImpl implements LendService {
    @Autowired
    private LendListMapper lendListMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    /**
     * 读者日志
     * @param readerId
     * @return
     */
    @Override
    public List<LendList> myLendList(Integer readerId) {
        return lendListMapper.selectMyLog(readerId);
    }

    /**
     * 管理员日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<LendList> lendList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LendList> list = lendListMapper.selectLog();
        PageInfo<LendList> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        PageResult<LendList> page = new PageResult<>();
        page.setList(list);
        page.setPageNum(pageNum);
        page.setPages(pages);
        page.setPageSize(pageSize);
        page.setTotal(total);
        return page;
    }

    /**
     * 删除日志
     * @param sernum
     * @return
     */
    @Override
    public boolean deleteLog(Long sernum) {
        try {
            this.lendListMapper.deleteByPrimaryKey(sernum);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关键字日志搜索
     * @param searchWord
     * @return
     */
    @Override
    public boolean matchLog(Integer searchWord) {
        LendListExample lendListExample = new LendListExample();
        LendListExample.Criteria criteria = lendListExample.createCriteria();
        criteria.andReaderIdEqualTo(searchWord);
        try {
            this.lendListMapper.countByExample(lendListExample);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean bookLend(Long bookId, Integer readerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        try {
            Date lendDate = sdf.parse(date);

            LendList lendList = new LendList();
            lendList.setBookId(bookId);
            lendList.setReaderId(readerId);
            lendList.setLendDate(date);
            String readerName = lendListMapper.queryNameByReaderId(readerId);
            lendList.setReaderName(readerName);
            int addSuc = this.lendListMapper.insertSelective(lendList);

            BookInfoExample bookInfoExample = new BookInfoExample();
            BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
            criteria.andBookIdEqualTo(bookId);

            Integer count = this.bookInfoMapper.selectByPrimaryKey(bookId).getCount();

            BookInfo bookInfo = new BookInfo();
            bookInfo.setState((short) 0);
            bookInfo.setCount(count + 1);
            int updateSuc = this.bookInfoMapper.updateByExampleSelective(bookInfo, bookInfoExample);

            return addSuc > 0 && updateSuc > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bookReturn(Long bookId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        try {

            LendList lendList = new LendList();
            lendList.setBackDate(date);

            LendListExample lendListExample = new LendListExample();
            LendListExample.Criteria lendCriteria = lendListExample.createCriteria();
            lendCriteria.andBookIdEqualTo(bookId);

            int updateLendSuc = this.lendListMapper.updateByExampleSelective(lendList, lendListExample);

            BookInfo bookInfo = new BookInfo();
            bookInfo.setState((short) 1);

            BookInfoExample bookInfoExample = new BookInfoExample();
            BookInfoExample.Criteria bookCriteria = bookInfoExample.createCriteria();
            bookCriteria.andBookIdEqualTo(bookId);
            int updateBookSuc = this.bookInfoMapper.updateByExampleSelective(bookInfo, bookInfoExample);

            return updateLendSuc > 0 && updateBookSuc > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bookLendNew(Long bookId, Integer readerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        try {
            Book oneBook = bookInfoMapper.queryOneBook(bookId);

            LendList lendList = new LendList();
            lendList.setBookId(bookId);
            lendList.setReaderId(readerId);
            lendList.setLendDate(date);
            lendList.setBookName(oneBook.getName());
            String readerName = lendListMapper.queryNameByReaderId(readerId);
            lendList.setReaderName(readerName);
            int addSuc = this.lendListMapper.insertSelective(lendList);

            Integer count = this.bookInfoMapper.selectByPrimaryKey(bookId).getCount();
            Integer num = oneBook.getNum();

            Book book = new Book();
            book.setState((short) 0);
            book.setCount(count + 1);
            book.setNum(num - 1);
            book.setBookId(bookId);

            int updateSuc = bookInfoMapper.updateBooks(book);

            return addSuc > 0 && updateSuc > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bookReturnNew(Long bookId,Long sernum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        try {
            LendList lendList = new LendList();
            lendList.setBackDate(date);
            lendList.setSernum(sernum);
            int updateLendSuc = lendListMapper.updateReturnDate(lendList);

            Integer num = bookInfoMapper.queryOneBook(bookId).getNum();

            Book book = new Book();
            book.setState((short) 1);
            book.setNum(num + 1);
            book.setBookId(bookId);
            int updateSuc = bookInfoMapper.updateBooks(book);

            return updateLendSuc > 0 && updateSuc > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
