package com.study.servise.impl;

import com.study.Dao.UserDao;
import com.study.Dao.impl.UserDaoImpl;
import com.study.pojo.User;
import com.study.servise.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {

        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {

        if (userDao.queryUserByUsername(username) == null){
            return false;
        }
        return true;

    }
}
