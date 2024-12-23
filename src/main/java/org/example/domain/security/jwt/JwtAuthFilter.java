package org.example.domain.security.jwt;

import org.example.domain.service.impl.AlunoServiceImpl;
import org.example.domain.service.impl.ProfessorServiceImpl;
import org.example.domain.service.impl.UsuarioAdmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private ProfessorServiceImpl professorService;
    private AlunoServiceImpl alunoService;
    private UsuarioAdmServiceImpl usuarioAdmService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, ProfessorServiceImpl professorService, AlunoServiceImpl alunoService, UsuarioAdmServiceImpl usuarioAdmService) {
        this.jwtService = jwtService;
        this.professorService = professorService;
        this.alunoService = alunoService;
        this.usuarioAdmService = usuarioAdmService;
    }

    @Override protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse,
                                              FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest
                .getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);
            if (isValid) {
                String loginUsuario = jwtService.obterLoginUsuario(token);
                UserDetails usuario = getUserDetails(loginUsuario);
                if (usuario != null) {
                    UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UserDetails getUserDetails(String username) {
        UserDetails userDetails = null;

        try{
            userDetails = alunoService.loadUserByUsername(username);
        } catch (EntityNotFoundException e){
            try{
                userDetails = professorService.loadUserByUsername(username);
            } catch (EntityNotFoundException e2){
                try{
                    userDetails = usuarioAdmService.loadUserByUsername(username);
                }catch (EntityNotFoundException e3){

                }
            }

        }

        return userDetails;
    }


}
