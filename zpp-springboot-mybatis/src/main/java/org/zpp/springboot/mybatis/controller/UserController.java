package org.zpp.springboot.mybatis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
    public R add(){
        sysUserService.insert();
        return R.builder()
                .code(200)
                .msg("success")
                .build();
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

    @GetMapping("/users/{userId}")
    public R get(@PathVariable("userId") SysUser sysUser){
        return new R(sysUser);
    }
}
