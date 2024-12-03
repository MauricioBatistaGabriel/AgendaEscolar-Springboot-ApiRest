package org.example.domain.service.impl;

import org.example.domain.entity.Aula;
import org.example.domain.entity.Materia;
import org.example.domain.entity.Professor;
import org.example.domain.entity.Turma;
import org.example.domain.enums.Periodo;
import org.example.domain.exception.RegraNegocioException;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.repository.AulaRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.repository.ProfessorTurmaRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import javax.print.attribute.standard.DateTimeAtCompleted;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService, UserDetailsService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfessorTurmaRepository professorTurmaRepository;

    @Autowired
    private MateriaServiceImpl materiaService;

    @Autowired
    private TurmaServiceImpl turmaService;

    @Autowired
    private MateriaProfessorService materiaProfessorService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public Integer save(CompleteProfessorDTO professorDTO) {
        Professor professor = new Professor(professorDTO.getEmail(), professorDTO.getSenha(), professorDTO.getNome(), professorDTO.getCpf(), professorDTO.getPeriodosDeTrabalho());
        Professor professor1 = professorRepository.save(professor);

        if (professorDTO.getMaterias().size() != 0){
            professorDTO.getMaterias().stream()
                    .map(materiaId -> materiaService.findById(materiaId))
                    .map(materia -> new CompleteMateriaProfessorDTO(materia.getId(), professor1.getId()))
                    .forEach(materiaProfessorDTO -> materiaProfessorService.save(materiaProfessorDTO));
        }
        else{
            throw new EntityNotFoundException("Nenhuma máteria foi selecionada");
        }

        return professor.getId();
    }

    public UserDetails autenticar(Professor professor){
        UserDetails user = loadUserByUsername(professor.getEmail());
        boolean senhasBatem = encoder.matches(professor.getSenha(), user.getPassword());
        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado"));

        return User
                .builder()
                .username(professor.getEmail())
                .password(professor.getSenha())
                .roles("PROFESSOR")
                .build();
    }

    @Override
    public Professor findById(Integer id) {
        return professorRepository.findById(id)
                .map( professor -> {
                    if (professor.isPresent() == true){
                        return professor;
                    }
                    else {
                        throw new EntityNotFoundException("Professor com o ID:" + id + " foi deletado");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Professor com o ID:" + id + " não encontrado"));
    }

    @Override
    public ReturnProfessorDTO findByIdReturnDTO(Integer id) {
        Professor professor = findById(id);

        ReturnProfessorDTO professorDTO = new ReturnProfessorDTO(professor.getId(), professor.getNome(), professor.getCpf(), professor.getPeriodosDeTrabalho());

        return professorDTO;
    }

    @Override
    public ReturnCompleteProfessorDTO findByIdReturnDTOComplete(Integer id) {
        Professor professor = findById(id);

        List<Materia> materias = materiaService.findMateriasByProfessorId(professor.getId());

        List<CompleteMateriaDTO> materiasDTO = new ArrayList<>();

        materias.stream()
                .map( materia -> materiasDTO.add(new CompleteMateriaDTO(materia.getNome())))
                .collect(Collectors.toList());

        ReturnCompleteProfessorDTO professoresDTO = new ReturnCompleteProfessorDTO(professor.getNome(), professor.getCpf(), materiasDTO, professor.getPeriodosDeTrabalho());

        return professoresDTO;
    }

    @Override
    public List<ReturnProfessorDTO> findProfessorDTOByMateriaAndPeriodo(Integer idMateria, Periodo periodo) {
        Materia materia = materiaService.findById(idMateria);

        List<Professor> professores = professorRepository.findProfessorByPeriodoAndMateria(idMateria, periodo);

        List<ReturnProfessorDTO> professoresDTO =
                professores.stream()
                        .map(professor -> new ReturnProfessorDTO(professor.getId(), professor.getNome(), professor.getCpf(), professor.getPeriodosDeTrabalho()))
                        .collect(Collectors.toList());

        return professoresDTO;
    }

    @Override
    public List<ReturnProfessorDTO> findProfessoresDTOByIdTurma(Integer id) {
        Turma turma = turmaService.findById(id);

        List<ReturnProfessorDTO> professoresDTO = new ArrayList<>();

        List<Professor> professores = professorTurmaRepository.findProfessoresByTurmaId(turma.getId());

        professores.stream()
                .map( professor -> professoresDTO.add(new ReturnProfessorDTO(professor.getId(), professor.getNome(), professor.getCpf(), professor.getPeriodosDeTrabalho())))
                .collect(Collectors.toList());

        return professoresDTO;
    }

    @Override
    public List<Professor> findByMateriaId(Integer id) {
        Materia materia = materiaService.findById(id);

        List<Professor> professores = professorRepository.findByMateriaId(materia.getId());

        return professores;
    }

    @Override
    public List<Professor> findByTurmaId(Integer id) {
        Turma turma = turmaService.findById(id);

        List<Professor> professores = professorRepository.findByTurmaId(turma.getId());
        return professores;
    }

    @Override
    public List<ReturnProfessorDTO> findAll() {
        List<Professor> professores = professorRepository.findAllOrderByIdDesc();

        return professores.stream()
                .map( professor1 -> new ReturnProfessorDTO(professor1.getId(), professor1.getNome(), professor1.getCpf(), professor1.getPeriodosDeTrabalho()))
                .collect(Collectors.toList());
    }

    @Override
    public Professor update(Integer id, Professor professor) {
        Professor professor1 = findById(id);

        professor.setId(professor1.getId());

        return professorRepository.save(professor);
    }

    @Override
    public void deleteById(Integer id) {
        Professor professor = findById(id);

        List<Aula> aulas = aulaRepository.findByProfessorId(professor.getId()).orElse(Collections.emptyList());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date dataAtual = new Date();
        String dataAtualFormatter = formatter.format(dataAtual);

        Date dataAula;
        aulas.stream()
                .forEach(aula -> {
                            try {
                                if (formatter.parse(aula.getData()).after(formatter.parse(dataAtualFormatter))) {
                                    throw new RegraNegocioException("Não foi possível realizar a operação. O professor possui aula agendada para data posterior à atual.");
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException("Erro ao parsear a data", e);
                            }
                        });

        professor.setPresent(false);

        professorRepository.save(professor);
    }
}
