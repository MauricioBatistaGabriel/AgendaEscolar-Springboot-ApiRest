package org.example.domain.rest.controller;

import org.example.domain.entity.Professor;
import org.example.domain.enums.Periodo;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.rest.dto.*;
import org.example.domain.security.jwt.JwtService;
import org.example.domain.service.ProfessorService;
import org.example.domain.service.ProfessorTurmaService;
import org.example.domain.service.impl.AulaServiceImpl;
import org.example.domain.service.impl.ProfessorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    private ProfessorServiceImpl professorService;

    @Autowired
    private AulaServiceImpl aulaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid CompleteProfessorDTO professorDTO){
        String senhaCriptografada = passwordEncoder.encode(professorDTO.getSenha());
        professorDTO.setSenha(senhaCriptografada);
        return professorService.save(professorDTO);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try{
            Professor professor = Professor.builder()
                    .email(credenciaisDTO.getEmail())
                    .senha(credenciaisDTO.getSenha())
                    .build();
            UserDetails professorAutenticado = professorService.autenticar(professor);
            String token = jwtService.gerarTokenProfessor(professor);
            return new TokenDTO(professor.getEmail(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException ex){
            throw new ResponseStatusException(UNAUTHORIZED, "Professor não encontrado ou senha inválida");
        }
    }

    @GetMapping("{id}")
    public ReturnCompleteProfessorDTO findById(@PathVariable Integer id){
        return professorService.findByIdReturnDTOComplete(id);
    }

    @GetMapping("/aulas/{id}")
    public List<ReturnAulaInProfessorDTO> findAulaByProfessorId(@PathVariable Integer id){
        return aulaService.findByProfessorId(id);
    }

    @GetMapping("/turma/{id}")
    public List<Professor> findByTurmaId(@PathVariable Integer id){
        return professorService.findByTurmaId(id);
    }

    @GetMapping("/materia/{idMateria}")
    public List<ReturnProfessorDTO> findProfessorByMateriaAndPeriodo(@PathVariable Integer idMateria, @RequestBody Periodo periodo){
        return professorService.findProfessorDTOByMateriaAndPeriodo(idMateria, periodo);
    }

    @GetMapping
    public List<ReturnProfessorDTO> findAll(){
        return professorService.findAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Professor update(@PathVariable Integer id, @RequestBody @Valid Professor professor){
        return professorService.update(id, professor);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        professorService.deleteById(id);
    }
}
