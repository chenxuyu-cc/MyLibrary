package com.ryoeiken.bms.service.impl;

import com.ryoeiken.bms.mapper.ReaderCardMapper;
import com.ryoeiken.bms.pojo.ReaderCard;
import com.ryoeiken.bms.pojo.ReaderCardExample;
import com.ryoeiken.bms.service.ReaderCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderCardServiceImpl implements ReaderCardService {
    @Autowired
    private ReaderCardMapper readerCardMapper;

    @Override
    public boolean updateName(Integer readerId, String name) {
        ReaderCard readerCard = new ReaderCard();
        readerCard.setName(name);

        ReaderCardExample readerCardExample = new ReaderCardExample();
        ReaderCardExample.Criteria criteria = readerCardExample.createCriteria();
        criteria.andReaderIdEqualTo(readerId);

        try {
            this.readerCardMapper.updateByExampleSelective(readerCard, readerCardExample);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePasswd(int readerId, String newPasswd) {
        ReaderCard readerCard = new ReaderCard();
        readerCard.setPasswd(newPasswd);

        ReaderCardExample readerCardExample = new ReaderCardExample();
        ReaderCardExample.Criteria criteria = readerCardExample.createCriteria();
        criteria.andReaderIdEqualTo(readerId);

        try {
            this.readerCardMapper.updateByExampleSelective(readerCard, readerCardExample);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
