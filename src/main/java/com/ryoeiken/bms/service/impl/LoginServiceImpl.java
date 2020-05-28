package com.ryoeiken.bms.service.impl;

import com.ryoeiken.bms.mapper.AdminMapper;
import com.ryoeiken.bms.mapper.ReaderCardMapper;
import com.ryoeiken.bms.pojo.Admin;
import com.ryoeiken.bms.pojo.AdminExample;
import com.ryoeiken.bms.pojo.ReaderCard;
import com.ryoeiken.bms.pojo.ReaderCardExample;
import com.ryoeiken.bms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ReaderCardMapper readerCardMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean hasMatchReader(int id, String passwd) {
        ReaderCardExample readerCardExample = new ReaderCardExample();
        ReaderCardExample.Criteria criteria = readerCardExample.createCriteria();
        criteria.andReaderIdEqualTo(id);
        criteria.andPasswdEqualTo(passwd);
        try {
            this.readerCardMapper.countByExample(readerCardExample);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean hasMatchAdmin(int id, String passwd) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andAdminIdEqualTo(id);
        criteria.andPasswordEqualTo(passwd);

        return this.adminMapper.countByExample(adminExample) == 1;
    }

    @Override
    public ReaderCard findReaderCardByUserId(int id) {
        ReaderCard readerCard = this.readerCardMapper.selectByPrimaryKey(id);
        return readerCard;
    }

    @Override
    public String getAdminPasswd(int id) {
        Admin admin = this.adminMapper.selectByPrimaryKey(id);
        String passwd = admin.getPassword();
        return passwd;
    }

    @Override
    public boolean adminRePasswd(int id, String newPasswd) {
        Admin admin = this.adminMapper.selectByPrimaryKey(id);
        admin.setPassword(newPasswd);
        try {
            this.adminMapper.updateByPrimaryKeySelective(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
