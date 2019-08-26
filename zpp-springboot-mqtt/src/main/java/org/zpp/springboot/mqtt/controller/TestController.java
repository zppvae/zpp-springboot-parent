package org.zpp.springboot.mqtt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zpp.springboot.mqtt.component.MQTTProducer;

/**
 * @author zpp
 * @date 2019/8/26 18:19
 */
@RestController
@ResponseBody
@RequestMapping("/mqtt")
@Slf4j
public class TestController {

    @Autowired
    private MQTTProducer mqttProducer;

    @GetMapping("/send/{msg}")
    public String send(@PathVariable String msg){
        mqttProducer.send(msg);
        return "success";
    }
}
