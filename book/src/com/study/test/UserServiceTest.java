package com.study.test;

import com.study.pojo.User;
import com.study.servise.UserService;
import com.study.servise.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService userService = new UserServiceImpl();

    @Test
    public void registerUser() {
        userService.registerUser(new User(null, "bbj168", "666666", "bbj168@qq.com"));
        userService.registerUser(new User(null, "abc168", "666666", "abc168@qq.com"));
    }

    @Test
    public void login() {
        System.out.println( userService.login(new User(null, "wzg168", "123456", null)));
    }

    @Test
    public void existsUsername() {
        if (userService.existsUsername("wzg1688")) {
            System.out.println("用户名已存在！");
        } else {
            System.out.println("用户名可用！");
        }
    }
}