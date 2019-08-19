package org.zpp.springboot.socketio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zpp.springboot.socketio.mapper.UserMapper;
import org.zpp.springboot.socketio.model.User;
import org.zpp.springboot.socketio.service.UserService;

import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public User selectByName(String userName) {
        return userMapper.selectByName(userName);
    }

    @Override
    public User selectById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAllUser();
    }
}