package com.ryoeiken.bms.service;

import com.ryoeiken.bms.pojo.ReaderCard;

public interface LoginService {
    boolean hasMatchReader(int id, String passwd);

    boolean hasMatchAdmin(int id, String passwd);

    ReaderCard findReaderCardByUserId(int id);

    String getAdminPasswd(int id);

    boolean adminRePasswd(int id, String newPasswd);
}
