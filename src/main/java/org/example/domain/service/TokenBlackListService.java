package org.example.domain.service;

public interface TokenBlackListService {
    void saveTokenBlackList(String token);
    boolean isTokenBlackList(String token);
}
