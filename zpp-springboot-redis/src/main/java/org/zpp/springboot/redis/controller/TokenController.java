package org.zpp.springboot.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.redis.annotation.ApiIdempotent;
import org.zpp.springboot.redis.service.TokenService;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")
    public String getToken(){
        return tokenService.createToken();
    }

    /**
     * 测试重复操作
     * @return
     */
    @ApiIdempotent
    @PostMapping("/token/test")
    public String test(){
        return "success";
    }
}
