package com.nesterrovv.vpntoken.controller;

import com.nesterrovv.vpntoken.dto.TokenDto;
import com.nesterrovv.vpntoken.service.TokenService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<TokenDto>> findTokenById(@PathVariable Long id) {
        return tokenService.findById(id)
            .map(optionalToken -> optionalToken
                .map(token -> ResponseEntity.ok(new TokenDto(token.getToken()))).orElseGet(() ->
                    ResponseEntity.notFound().build()));
    }

    @PostMapping("/generate")
    public Mono<TokenDto> generateToken() {
        return tokenService.generateToken().map(token -> new TokenDto(token.getToken()));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteToken(@PathVariable Long id) {
        tokenService.delete(id);
    }

}
