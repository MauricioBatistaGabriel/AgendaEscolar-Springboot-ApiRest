package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.exception.EntityNotDisponibleException;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.CompleteAlunoDTO;
import org.example.domain.rest.dto.ReturnAllAlunoDTO;
import org.example.domain.rest.dto.ReturnAlunoOnlyNameDTO;
import org.example.domain.service.AlunoService;
import org.example.domain.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService, UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioAdmRepository usuarioAdmRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private NotaService notaService;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaServiceImpl turmaService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Integer save(CompleteAlunoDTO alunoDTO) {
        Aluno aluno = new Aluno(alunoDTO.getEmail(), alunoDTO.getSenha(), alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());

        //Valida se email de usuário está livre
        UsuarioAdm userExist = usuarioAdmRepository.findByEmail(alunoDTO.getEmail()).orElse(null);
        Aluno alunoExist = alunoRepository.findByEmail(alunoDTO.getEmail()).orElse(null);
        Professor professorExist = professorRepository.findByEmail(alunoDTO.getEmail()).orElse(null);
        if (userExist != null || alunoExist != null || professorExist != null){
            throw new EntityNotDisponibleException("Email já está em uso");
        }

        alunoRepository.save(aluno);
        return aluno.getId();
    }

    public UserDetails autenticar(Aluno aluno){
        UserDetails user = loadUserByUsername(aluno.getEmail());
        boolean senhasBatem = encoder.matches(aluno.getSenha(), user.getPassword());
        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Aluno aluno = alunoRepository.findByEmail(email)
                .orElseThrow( () -> new EntityNotFoundException("Usuário não encontrado"));

        return User
                .builder()
                .username(aluno.getEmail())
                .password(aluno.getSenha())
                .roles("ALUNO")
                .build();
    }

    @Override
    public Aluno findById(Integer id) {
        return alunoRepository.findById(id)
                .map( aluno -> {
                    if (aluno.isPresent() == true){
                        return aluno;
                    }
                    else{
                        throw new EntityNotFoundException("Aluno não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Aluno não encontrado"));
    }

    @Override
    public CompleteAlunoDTO findByIdReturnDTO(Integer id) {
        Aluno aluno = findById(id);

        CompleteAlunoDTO alunoDTO = new CompleteAlunoDTO(aluno.getNome(), aluno.getCpf(), aluno.getIdade());

        return alunoDTO;
    }

    @Override
    public CompleteAlunoDTO findAlunoByIdNota(Integer id) {
        Nota nota = notaService.findById(id);

        Aluno aluno = notaRepository.findAlunoByIdNota(nota.getId());
        CompleteAlunoDTO alunoDTO = findByIdReturnDTO(aluno.getId());

        return alunoDTO;
    }

    @Override
    public List<ReturnAllAlunoDTO> findAll() {
        List<Aluno> alunos = alunoRepository.findAllOrderByIdDesc();

        return alunos.stream()
                .map( aluno -> {
                    Turma turma = turmaService.findByAlunoId(aluno.getId());

                    String nomeTurma = "";
                    if (turma != null){
                        nomeTurma = turma.isPresent() ? turma.getNome() : "";
                    }

                    return new ReturnAllAlunoDTO(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getIdade(), nomeTurma);
                }).collect(Collectors.toList());
    }

    @Override
    public List<ReturnAlunoOnlyNameDTO> findSemTurma() {
        List<Aluno> alunos = alunoRepository.findSemTurma();

        return alunos.stream()
                .map(aluno -> new ReturnAlunoOnlyNameDTO(aluno.getId(), aluno.getNome()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Aluno update(Integer id, Aluno novoAluno) {
        Aluno alunoBanco = findById(id);

        novoAluno.setId(alunoBanco.getId());
        novoAluno.setEmail(alunoBanco.getEmail());
        novoAluno.setSenha(alunoBanco.getSenha());
        novoAluno.setPresent(true);
        return alunoRepository.save(novoAluno);
    }

    @Override
    public void deleteById(Integer id) {
        Aluno aluno = findById(id);

        aluno.setPresent(false);
        alunoRepository.save(aluno);
    }
}
