package com.nic.zuul.security.service;

import com.nic.zuul.bean.User;
import com.nic.zuul.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class DetailsService implements UserDetailsService{
    @Autowired
    UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int sss=userDao.selectSome();
        User user = userDao.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
	}

}
