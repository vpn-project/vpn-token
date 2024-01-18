package com.nesterrovv.vpntoken.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nesterrovv.vpntoken.entity.Token;
import com.nesterrovv.vpntoken.repository.TokenRepository;
import com.nesterrovv.vpntoken.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TokenServiceTest {

    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenService(tokenRepository);
    }

    @Test
    void testGenerateToken() {
        when(tokenRepository.save(any(Token.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        StepVerifier.create(tokenService.generateToken()).assertNext(token -> {
            assertNotNull(token);
            assertNotNull(token.getToken());
            verify(tokenRepository, times(1)).save(any(Token.class));
        }).verifyComplete();
    }

    @Test
    void testSave() {
        Token tokenToSave = new Token();
        when(tokenRepository.save(tokenToSave)).thenReturn(Mono.just(tokenToSave));
        StepVerifier.create(tokenService.save(tokenToSave)).assertNext(Assertions::assertNotNull).verifyComplete();
        verify(tokenRepository, times(1)).save(tokenToSave);
    }

    @Test
    void testFindById() {
        Integer tokenId = 1;
        when(tokenRepository.findById(tokenId)).thenReturn(Mono.just(new Token()));
        StepVerifier.create(tokenService.findById(tokenId)).assertNext(token -> assertNotNull(token)).verifyComplete();
        verify(tokenRepository, times(1)).findById(tokenId);
    }

    @Test
    void testDelete() {
        Integer tokenId = 1;
        when(tokenRepository.deleteById(tokenId)).thenReturn(Mono.empty());
        StepVerifier.create(tokenService.delete(tokenId)).verifyComplete();
        verify(tokenRepository, times(1)).deleteById(tokenId);
    }

}
