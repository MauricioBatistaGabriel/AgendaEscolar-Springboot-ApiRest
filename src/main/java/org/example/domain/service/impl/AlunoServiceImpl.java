package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.Nota;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.NotaRepository;
import org.example.domain.rest.dto.CompleteAlunoDTO;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService, UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private NotaService notaService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Integer save(CompleteAlunoDTO alunoDTO) {
        Aluno aluno = new Aluno(alunoDTO.getEmail(), alunoDTO.getSenha(), alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());
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
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado"));

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
                        throw new EntityNotFoundException("Aluno com o ID:" + id + " foi deletado");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Aluno com o ID:" + id + " não encontrado"));
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
    public List<CompleteAlunoDTO> filterAll(CompleteAlunoDTO alunoDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Aluno aluno = new Aluno(alunoDTO.getEmail(), alunoDTO.getSenha(), alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());

        Example example = Example.of(aluno, matcher);
        List<Aluno> alunos = alunoRepository.findAll(example);

        return alunos.stream()
                .map( aluno1 -> new CompleteAlunoDTO(aluno1.getNome(), aluno1.getCpf(), aluno1.getIdade()))
                .collect(Collectors.toList());
    }

    @Override
    public Aluno update(Integer id, Aluno aluno) {
        Aluno aluno1 = findById(id);
        aluno.setId(aluno1.getId());
        return alunoRepository.save(aluno);
    }

    @Override
    public void deleteById(Integer id) {
        Aluno aluno = findById(id);

        aluno.setPresent(false);
        alunoRepository.save(aluno);
    }
}
