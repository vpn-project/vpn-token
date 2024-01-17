package com.nesterrovv.vpntoken.vpnaccount.controller;

import com.nesterrovv.vpntoken.controller.ConnectionController;
import com.nesterrovv.vpntoken.entity.Token;
import com.nesterrovv.vpntoken.service.TokenService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebFluxTest(ConnectionController.class)
@ExtendWith(SpringExtension.class)
class ConnectionControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private TokenService tokenService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindTokenById() {
        Long tokenId = 1L;
        Token token = new Token();
        when(tokenService.findById(tokenId)).thenReturn(Mono.just(Optional.of(token)));

        webTestClient.get()
            .uri("/vpn/token/get/{id}", tokenId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.token").isEqualTo(token.getToken());

        verify(tokenService, times(1)).findById(tokenId);
        verifyNoMoreInteractions(tokenService);
    }

    @Test
    void testFindTokenByIdWhenNotFound() {
        Long tokenId = 1L;
        when(tokenService.findById(tokenId)).thenReturn(Mono.just(Optional.empty()));

        webTestClient.get()
            .uri("/vpn/token/get/{id}", tokenId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();

        verify(tokenService, times(1)).findById(tokenId);
        verifyNoMoreInteractions(tokenService);
    }

    @Test
    void testGenerateToken() {
        Token generatedToken = new Token();
        generatedToken.setToken("generated_token");
        when(tokenService.generateToken()).thenReturn(Mono.just(generatedToken));

        webTestClient.post()
            .uri("/vpn/token/generate")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.token").isEqualTo(generatedToken.getToken());

        verify(tokenService, times(1)).generateToken();
        verifyNoMoreInteractions(tokenService);
    }

    @Test
    void testDeleteToken() {
        Long tokenId = 1L;

        webTestClient.delete()
            .uri("/vpn/token/delete/{id}", tokenId)
            .exchange()
            .expectStatus().isOk();

        verify(tokenService, times(1)).delete(tokenId);
        verifyNoMoreInteractions(tokenService);
    }

}
