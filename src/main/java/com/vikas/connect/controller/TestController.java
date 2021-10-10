package com.vikas.connect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @GetMapping("/hello")
    public Mono<String> test() {
        return Mono.just("Hello vikas");
    }

}
