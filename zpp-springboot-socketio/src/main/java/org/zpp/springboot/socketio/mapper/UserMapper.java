package org.zpp.springboot.socketio.mapper;

import org.zpp.springboot.socketio.model.User;

import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectById(Integer userId);


    List<User> selectAllUser();

    User selectByName(String userName);
}