package com.nesterrovv.vpntoken.controller;

import com.nesterrovv.vpntoken.dto.TokenDto;
import com.nesterrovv.vpntoken.service.TokenService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vpn/token")
@RequiredArgsConstructor
public class ConnectionController {

    private final TokenService tokenService;
    private static final String FALLBACK_MESSAGE = "Функционал не доступен";
    private static final int STATUS_CODE = 500;

    @GetMapping("/get/{id}")
    @HystrixCommand(fallbackMethod = "fallbackFindTokenById")
    public ResponseEntity<TokenDto> findTokenById(@PathVariable Integer id) {
        return tokenService.findById(id)
            .map(optionalToken -> optionalToken
                .map(token -> ResponseEntity.ok(new TokenDto(token.getToken()))).orElseGet(() ->
                    ResponseEntity.notFound().build())).block();
    }

    private Mono<ResponseEntity<TokenDto>> fallbackFindTokenById(Integer id) {
        return Mono.just(ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new TokenDto(FALLBACK_MESSAGE)));
    }

    @PostMapping("/generate")
    @HystrixCommand(fallbackMethod = "fallbackGenerateToken")
    public TokenDto generateToken() {
        return tokenService.generateToken().map(token -> new TokenDto(token.getToken())).block();
    }

    private Mono<TokenDto> fallbackGenerateToken() {
        return Mono.just(new TokenDto("Fallback"));
    }

    @DeleteMapping("/delete/{id}")
    @HystrixCommand(fallbackMethod = "fallbackDeleteToken")
    public void deleteToken(@PathVariable Integer id) {
        tokenService.delete(id);
    }

    private Mono<ResponseEntity<TokenDto>> fallbackDeleteToken(Integer id) {
        return Mono.just(ResponseEntity.status(STATUS_CODE).body(new TokenDto(FALLBACK_MESSAGE)));
    }

}
