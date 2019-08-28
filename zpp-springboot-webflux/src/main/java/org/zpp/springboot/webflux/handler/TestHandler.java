package org.zpp.springboot.webflux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author zpp
 * @date 2019/8/27 14:20
 */
@Component
public class TestHandler {

    public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(
                Mono.just("Now is " + LocalDateTime.now()), String.class);
    }

    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux.interval(Duration.ofSeconds(1)).
                        map(l -> LocalDateTime.now().toString()), String.class);
    }
}
