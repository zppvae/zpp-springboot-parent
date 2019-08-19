package org.zpp.springboot.socketio.service;

import org.zpp.springboot.socketio.model.User;

import java.util.List;


public interface UserService {

    int addUser(User user);

    User selectByName(String userName);

    User selectById(Integer userId);

    List<User> getAllUser();
}