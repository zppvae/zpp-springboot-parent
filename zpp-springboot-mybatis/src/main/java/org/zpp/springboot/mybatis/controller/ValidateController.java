package org.zpp.springboot.mybatis.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.mybatis.common.annotation.DateTime;
import org.zpp.springboot.mybatis.model.GroupBean;
import org.zpp.springboot.mybatis.validator.ValidateGroup;

@Validated
@RestController
public class ValidateController {

    @GetMapping("/testValidate")
    public String test(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") String date) {
        return "success";
    }

    @GetMapping("/insert")
    public String insert(@Validated(value = ValidateGroup.Default.class) GroupBean book) {
        return "insert";
    }


    @GetMapping("/update")
    public String update(@Validated(value = {ValidateGroup.Default.class, ValidateGroup.Update.class}) GroupBean book) {
        return "update";
    }
}