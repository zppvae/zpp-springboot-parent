package org.zpp.springboot.sharding.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zpp.springboot.sharding.mapper.UserMapper;
import org.zpp.springboot.sharding.model.User;
import org.zpp.springboot.sharding.service.UserService;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
