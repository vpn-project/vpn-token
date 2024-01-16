package com.nesterrovv.vpntoken.mapper;

import com.nesterrovv.vpntoken.dto.TokenDto;
import com.nesterrovv.vpntoken.entity.Token;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenDto entityToDto(Token token) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(token, TokenDto.class);
    }

    public Token dtoToEntity(TokenDto userCreateDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userCreateDto, Token.class);
    }

}
