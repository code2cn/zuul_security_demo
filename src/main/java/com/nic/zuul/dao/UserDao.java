package com.nic.zuul.dao;

import com.nic.zuul.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findUserByName(String username);
    int selectSome();
}
