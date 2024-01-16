package com.nesterrovv.vpntoken.repository;

import com.nesterrovv.vpntoken.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

}
