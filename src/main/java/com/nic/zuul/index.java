package com.nic.zuul;

import com.nic.zuul.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class index {
    @Autowired
    UserDao userDao;
    @RequestMapping("/test")
    public String testConfig() {
        return userDao.selectSome()+"";
    }
    @GetMapping("/user/getModle")
    public List<String> getModle()
    {
        List<String> ms=new ArrayList<>();
        ms.add("模块1");
        return ms;
    }
}
