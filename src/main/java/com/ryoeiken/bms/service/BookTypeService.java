package com.ryoeiken.bms.service;

import com.ryoeiken.bms.pojo.ClassInfo;
import com.ryoeiken.bms.pojo.PageResult;


public interface BookTypeService {
    PageResult<ClassInfo> classInfos(Integer pageNum, Integer pageSize);

    boolean deleteClassInfo(Integer classId);

    ClassInfo getClassInfo(Integer classId);

    boolean editBookType(ClassInfo classInfo);

    boolean addBookType(ClassInfo classInfo);
}
