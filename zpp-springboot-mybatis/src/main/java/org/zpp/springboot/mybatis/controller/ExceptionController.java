package org.zpp.springboot.mybatis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.mybatis.exception.CustomException;

@RestController
public class ExceptionController {

    @GetMapping("/test")
    public String test(Integer num) {
        if (num == null) {
            throw new CustomException(400, "num不能为空");
        }
        int i = 10 / num;
        return "result:" + i;
    }
}