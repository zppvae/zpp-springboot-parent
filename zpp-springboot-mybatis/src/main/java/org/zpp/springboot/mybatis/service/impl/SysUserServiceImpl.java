package org.zpp.springboot.mybatis.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zpp.springboot.mybatis.mapper.SysUserMapper;
import org.zpp.springboot.mybatis.model.SysUser;
import org.zpp.springboot.mybatis.service.SysUserService;

/**
 * @author zpp
 * @date 2018/12/18 10:54
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private SysUserMapper sysUserMapper;
}
