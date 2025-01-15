package org.example.domain.service.impl;

import org.example.domain.entity.TokenBlackList;
import org.example.domain.repository.TokenBlackListRepository;
import org.example.domain.service.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlackListServiceImpl implements TokenBlackListService {

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    @Override
    public void saveTokenBlackList(String token) {
        TokenBlackList tokenBlacklist = new TokenBlackList();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setLogoutDate(LocalDateTime.now());
        tokenBlackListRepository.save(tokenBlacklist);
    }

    @Override
    public boolean isTokenBlackList(String token) {
        return tokenBlackListRepository.findByToken(token).isPresent();
    }
}
