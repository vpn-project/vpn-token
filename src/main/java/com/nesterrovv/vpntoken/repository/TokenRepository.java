package com.nesterrovv.vpntoken.repository;

import com.nesterrovv.vpntoken.entity.Token;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TokenRepository extends R2dbcRepository<Token, Integer> {

}
