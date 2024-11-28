package org.example.domain.rest.controller;

import org.example.domain.entity.UsuarioAdm;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.rest.dto.CompleteUsuarioAdmDTO;
import org.example.domain.rest.dto.CredenciaisDTO;
import org.example.domain.rest.dto.TokenDTO;
import org.example.domain.security.jwt.JwtService;
import org.example.domain.service.impl.UsuarioAdmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioAdmController {

    @Autowired
    private UsuarioAdmServiceImpl usuarioAdmService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid CompleteUsuarioAdmDTO usuarioAdm){
        String senhaCriptografada = passwordEncoder.encode(usuarioAdm.getSenha());
        usuarioAdm.setSenha(senhaCriptografada);
        return usuarioAdmService.save(usuarioAdm);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try{
            UsuarioAdm usuarioAdm = UsuarioAdm.builder()
                    .email(credenciaisDTO.getEmail())
                    .senha(credenciaisDTO.getSenha())
                    .build();
            UserDetails usuarioAutenticado = usuarioAdmService.autenticar(usuarioAdm);
            String token = jwtService.gerarTokenAdm(usuarioAdm);
            return new TokenDTO(usuarioAdm.getEmail(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado ou senha inválida");
        }
    }
}
