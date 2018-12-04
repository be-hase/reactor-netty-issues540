package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@SpringBootApplication
public class DemoApplication {
    private final WebClient webClient;

    public DemoApplication() {
        webClient = WebClient.create("https://httpbin.org");
    }

    // It's OK.
    @GetMapping("/200")
    public Object statusCode200() {
        return webClient.get()
                        .uri("/status/200")
                        .retrieve()
                        .bodyToMono(String.class)
                        .onErrorReturn("ERROR")
                        .block();
    }

    // I'm handling with `onErrorReturn`, but error log appears.
    @GetMapping("/400")
    public Object statusCode400() {
        return webClient.get()
                        .uri("/status/400")
                        .retrieve()
                        .bodyToMono(String.class)
                        .onErrorReturn(WebClientResponseException.class, "ERROR")
                        .block();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
