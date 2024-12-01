package org.example.domain.config;

import org.example.domain.entity.UsuarioAdm;
import org.example.domain.security.jwt.JwtAuthFilter;
import org.example.domain.security.jwt.JwtService;
import org.example.domain.service.impl.AlunoServiceImpl;
import org.example.domain.service.impl.ProfessorServiceImpl;
import org.example.domain.service.impl.UsuarioAdmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ProfessorServiceImpl professorService;

    @Autowired
    AlunoServiceImpl alunoService;

    @Autowired
    UsuarioAdmServiceImpl usuarioAdmService;

    @Autowired
    JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, professorService, alunoService, usuarioAdmService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(alunoService)
                .passwordEncoder(passwordEncoder());
        auth
                .userDetailsService(professorService)
                .passwordEncoder(passwordEncoder());
        auth
                .userDetailsService(usuarioAdmService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()

                //USUARIO CONTROLLER
                .antMatchers(POST, "/api/usuario/**").permitAll()

                //PROFESSOR CONTROLLER
                .antMatchers(POST, "/api/professor/auth").permitAll()
                .antMatchers(POST, "/api/professor").hasRole("ADM")
                .antMatchers(DELETE, "/api/professor/{id}").hasRole("ADM")
                .antMatchers(GET, "/api/professor/**").hasAnyRole("ADM", "PROFESSOR")

                //ALUNO CONTROLLER
                .antMatchers(POST, "/api/aluno/auth").permitAll()
                .antMatchers(POST, "/api/aluno").hasRole("ADM")
                .antMatchers(DELETE, "/api/aluno/{id}").hasRole("ADM")
                .antMatchers(GET, "/api/aluno/**").hasAnyRole("ADM", "ALUNO")

                //AULA CONTROLLER
                .antMatchers(POST, "/api/aula").hasRole("ADM")
                .antMatchers(PUT, "/api/aula/{id}").hasRole("ADM")
                .antMatchers(DELETE, "/api/aula/{id}").hasRole("ADM")
                .antMatchers(GET, "/api/aula/**").hasAnyRole("ADM", "ALUNO", "PROFESSOR")

                //AVALIAÇÃO CONTROLLER
                .antMatchers(POST, "/api/avaliacao").hasRole("PROFESSOR")
                .antMatchers(PUT, "/api/avaliacao/{id}").hasRole("PROFESSOR")
                .antMatchers(DELETE, "/api/avaliacao/{id}").hasRole("PROFESSOR")
                .antMatchers(GET, "/api/avaliacao/**").hasAnyRole("ADM", "ALUNO", "PROFESSOR")

                //MATERIA CONTROLLER
                .antMatchers(POST, "/api/materia").hasRole("ADM")
                .antMatchers(PUT, "/api/materia/{id}").hasRole("ADM")
                .antMatchers(DELETE, "/api/materia/{id}").hasRole("ADM")
                .antMatchers(GET, "/api/materia/**").hasAnyRole("ADM", "ALUNO", "PROFESSOR")

                //NOTA CONTROLLER
                .antMatchers(POST, "/api/nota").hasRole("PROFESSOR")
                .antMatchers(PUT, "/api/nota/{id}").hasRole("PROFESSOR")
                .antMatchers(DELETE, "/api/nota/{id}").hasRole("PROFESSOR")
                .antMatchers(GET, "/api/nota/**").hasAnyRole("ADM", "ALUNO", "PROFESSOR")

                //SALA CONTROLLER
                .antMatchers(POST, "/api/sala").hasRole("ADM")
                .antMatchers(PUT, "/api/sala/{id}").hasRole("ADM")
                .antMatchers(DELETE, "/api/sala/{id}").hasRole("ADM")
                .antMatchers(GET, "/api/sala/**").hasAnyRole("ADM", "ALUNO", "PROFESSOR")

                //TURMA CONTROLLER
                .antMatchers(POST, "/api/turma").hasRole("ADM")
                .antMatchers(PUT, "/api/turma/{id}").hasRole("ADM")
                .antMatchers(DELETE, "/api/turma/{id}").hasRole("ADM")
                .antMatchers(GET, "/api/turma/**").hasAnyRole("ADM", "ALUNO", "PROFESSOR")

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
