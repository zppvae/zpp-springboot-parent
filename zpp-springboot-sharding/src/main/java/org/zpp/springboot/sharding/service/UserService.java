package org.zpp.springboot.sharding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zpp.springboot.sharding.model.User;

import java.util.List;


public interface UserService extends IService<User> {

    @Override
    boolean save(User user);

    List<User> getUserList();
}
