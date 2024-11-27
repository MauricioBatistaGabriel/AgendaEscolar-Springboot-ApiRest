package org.example.domain.rest.controller;

import org.example.domain.entity.Aluno;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.rest.dto.CompleteAlunoDTO;
import org.example.domain.rest.dto.CredenciaisDTO;
import org.example.domain.rest.dto.TokenDTO;
import org.example.domain.security.jwt.JwtService;
import org.example.domain.service.AlunoService;
import org.example.domain.service.impl.AlunoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/aluno")
public class AlunoController {

    @Autowired
    private AlunoServiceImpl alunoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody CompleteAlunoDTO alunoDTO){
        String senhaCriptografada = passwordEncoder.encode(alunoDTO.getSenha());
        alunoDTO.setSenha(senhaCriptografada);
        return alunoService.save(alunoDTO);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try{
            Aluno aluno = Aluno.builder()
                    .email(credenciaisDTO.getEmail())
                    .senha(credenciaisDTO.getSenha())
                    .build();
            UserDetails alunoAutenticado = alunoService.autenticar(aluno);
            String token = jwtService.gerarTokenAluno(aluno);
            return new TokenDTO(aluno.getEmail(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException ex){
            throw new ResponseStatusException(UNAUTHORIZED, "Aluno não encontrado ou senha inválida");
        }
    }

    @GetMapping("{id}")
    public CompleteAlunoDTO findById(@PathVariable Integer id){
        return alunoService.findByIdReturnDTO(id);
    }

    @GetMapping
    public List<CompleteAlunoDTO> filterAll(@RequestBody CompleteAlunoDTO alunoDTO){
        return alunoService.filterAll(alunoDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Aluno update(@PathVariable Integer id, @RequestBody Aluno aluno){
        return alunoService.update(id, aluno);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        alunoService.deleteById(id);
    }
}
