package com.nesterrovv.vpntoken.repository;

import com.nesterrovv.vpntoken.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
