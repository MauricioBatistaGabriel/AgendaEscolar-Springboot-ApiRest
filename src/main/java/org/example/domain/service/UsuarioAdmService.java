package org.example.domain.service;

import org.example.domain.entity.UsuarioAdm;
import org.example.domain.rest.dto.CompleteUsuarioAdmDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioAdmService {

    Integer save(CompleteUsuarioAdmDTO usuarioAdmDTO);
    UserDetails autenticar(UsuarioAdm usuarioAdm);
}
