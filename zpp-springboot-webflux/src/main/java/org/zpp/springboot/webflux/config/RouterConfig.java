package org.zpp.springboot.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.zpp.springboot.webflux.handler.TestHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 路由
 */
@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> timerRouterRunction(TestHandler testHandler) {
        return route(GET("/time"), testHandler::getTime)
                .andRoute(GET("/times"), testHandler::sendTimePerSec);
    }
}