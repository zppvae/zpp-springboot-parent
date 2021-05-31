package org.zpp.springboot.mybatis.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zpp.springboot.mybatis.mapper.SysUserMapper;
import org.zpp.springboot.mybatis.model.SysUser;
import org.zpp.springboot.mybatis.service.SysUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zpp
 * @date 2018/12/18 10:54
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert() {
        SysUser user = null;
        int length = 2;
        for (int i = 0; i < length; i++) {
            user = new SysUser();
            user.setUsername("user_"+(6000001+i));
            user.setPassword("123456");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());

            baseMapper.insert(user);
        }
        int a = 1/0;
    }
}
