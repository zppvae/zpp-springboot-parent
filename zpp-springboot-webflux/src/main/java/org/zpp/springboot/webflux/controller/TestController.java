package org.zpp.springboot.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zpp
 * @date 2019/8/27 14:07
 */
@RestController
public class TestController {

    @GetMapping("/index")
    public Mono<String> hello() {
        return Mono.just("webflux world");
    }
}
