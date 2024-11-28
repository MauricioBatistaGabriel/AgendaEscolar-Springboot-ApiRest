package org.example.domain.service.impl;

import org.example.domain.entity.UsuarioAdm;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.repository.UsuarioAdmRepository;
import org.example.domain.rest.dto.CompleteUsuarioAdmDTO;
import org.example.domain.service.UsuarioAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAdmServiceImpl implements UsuarioAdmService, UserDetailsService {

    @Autowired
    private UsuarioAdmRepository usuarioAdmRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Integer save(CompleteUsuarioAdmDTO usuarioAdmDTO) {
        UsuarioAdm usuarioAdm = UsuarioAdm
                .builder()
                .email(usuarioAdmDTO.getEmail())
                .senha(usuarioAdmDTO.getSenha())
                .build();

        UsuarioAdm usuarioSave = usuarioAdmRepository.save(usuarioAdm);

        return usuarioSave.getId();
    }

    @Override
    public UserDetails autenticar(UsuarioAdm usuarioAdm) {
        UserDetails user = loadUserByUsername(usuarioAdm.getEmail());
        boolean senhasBatem = encoder.matches(usuarioAdm.getSenha(), user.getPassword());
        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UsuarioAdm usuarioAdm = usuarioAdmRepository.findByEmail(s)
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado"));

        return User
                .builder()
                .username(usuarioAdm.getEmail())
                .password(usuarioAdm.getSenha())
                .roles("ADM")
                .build();
    }
}
