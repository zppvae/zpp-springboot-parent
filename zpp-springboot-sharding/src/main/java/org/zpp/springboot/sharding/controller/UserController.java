package org.zpp.springboot.sharding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.sharding.model.User;
import org.zpp.springboot.sharding.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/list")
    public List<User> select() {
        return userService.getUserList();
    }

    @PostMapping("/user")
    public Boolean insert(@RequestBody User user) {
        return userService.save(user);
    }

}