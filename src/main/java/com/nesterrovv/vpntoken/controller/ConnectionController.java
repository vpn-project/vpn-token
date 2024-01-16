package com.nesterrovv.vpntoken.controller;

import com.nesterrovv.vpntoken.dto.TokenDto;
import com.nesterrovv.vpntoken.entity.Token;
import com.nesterrovv.vpntoken.service.TokenService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vpn/token")
@RequiredArgsConstructor
public class ConnectionController {

    private final TokenService tokenService;

    @GetMapping("/get/{id}")
    public ResponseEntity<TokenDto> findTokenById(@PathVariable Long id) {
        Optional<Token> tokenOptional = tokenService.findById(id);
        return tokenOptional
            .map(token -> ResponseEntity.ok(new TokenDto(token.getToken())))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/generate")
    public TokenDto generateToken() {
        Token generatedToken = tokenService.generateToken();
        return new TokenDto(generatedToken.getToken());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteToken(@PathVariable Long id) {
        tokenService.delete(id);
    }

}
