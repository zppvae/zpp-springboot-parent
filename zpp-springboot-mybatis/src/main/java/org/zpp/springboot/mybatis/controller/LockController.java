package org.zpp.springboot.mybatis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.mybatis.common.annotation.CacheLock;
import org.zpp.springboot.mybatis.common.annotation.CacheParam;

@RestController
@RequestMapping("/redislock")
public class LockController {

    @CacheLock(prefix = "lock")
    @GetMapping
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }
}
