//package com.nic.zuul.service;
//
//
//import com.nic.zuul.bean.User;
//import com.nic.zuul.dao.UserDao;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class UserService {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//@Autowired
//UserDao userDao;
//
//    public User findUserByName(String username)
//    {
//     User user= userDao.findUserByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        return user;
//    }
//}
