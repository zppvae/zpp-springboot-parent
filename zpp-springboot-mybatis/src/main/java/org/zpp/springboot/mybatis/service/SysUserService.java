package org.zpp.springboot.mybatis.service;

import com.baomidou.mybatisplus.service.IService;
import org.zpp.springboot.mybatis.model.SysUser;

/**
 * @author zpp
 * @date 2018/12/18 10:53
 */
public interface SysUserService extends IService<SysUser> {

    public void insert();
}
