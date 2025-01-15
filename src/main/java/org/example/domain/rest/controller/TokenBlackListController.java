package org.example.domain.rest.controller;

import org.example.domain.service.impl.TokenBlackListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TokenBlackListController {

    @Autowired
    private TokenBlackListServiceImpl tokenBlackListService;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(String token){
        tokenBlackListService.saveTokenBlackList(token);
    }
}
