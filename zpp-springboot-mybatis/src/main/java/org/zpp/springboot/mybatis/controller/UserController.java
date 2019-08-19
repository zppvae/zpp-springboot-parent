package org.zpp.springboot.mybatis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.mybatis.common.R;
import org.zpp.springboot.mybatis.common.annotation.Log;
import org.zpp.springboot.mybatis.model.SysUser;
import org.zpp.springboot.mybatis.service.SysUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zpp
 * @date 2018/12/18 11:01
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/user")
    public String add(){
        SysUser user = null;
        int length = 4000000;
        List<SysUser> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            user = new SysUser();
            user.setUsername("user_"+(6000001+i));
            user.setPassword("123456");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());

            list.add(user);
        }
        sysUserService.insertBatch(list);
        return "success";
    }

    @Log("测试日志")
    @GetMapping("/user/log")
    public R testLog(){
        return R.builder()
                .code(200)
                .msg("success")
                .build();
    }

    @PostMapping("/user/test")
    public R test(@RequestBody Map<String,Object> map){
        return R.builder()
                .code(200)
                .msg("success")
                .build();
    }
}
