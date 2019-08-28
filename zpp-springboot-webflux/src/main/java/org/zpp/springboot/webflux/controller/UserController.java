package org.zpp.springboot.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zpp.springboot.webflux.model.SysUser;
import org.zpp.springboot.webflux.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增数据
     * @param user
     * @return
     */
    @PostMapping
    public Mono<SysUser> insert(@RequestBody SysUser user) {
        return userService.save(user);
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/list")
    public Flux<SysUser> list(){
        return userService.selectAll();
    }


    /**
     * 查询多个
     *
     * 异步组装结果
     * @return
     */
    @GetMapping("/search")
    public Flux<SysUser> search() {
        Flux<SysUser> flux = Flux.just("111@qq.com","222@qq.com","333@qq.com")
                .flatMap(s -> userService.selectByEmail(s));
        return flux;
    }


}