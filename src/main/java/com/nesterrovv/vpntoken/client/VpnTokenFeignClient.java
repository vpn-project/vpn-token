package com.nesterrovv.vpntoken.client;

import com.nesterrovv.vpntoken.entity.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("vpn-token")
public interface VpnTokenFeignClient {

    @DeleteMapping("/tokens/{tokenId}")
    void deleteToken(@PathVariable("tokenId") Long tokenId);

    @PostMapping("/tokens/generate")
    Token generateToken();

}
