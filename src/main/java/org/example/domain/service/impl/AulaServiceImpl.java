package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TurmaService turmaService;

    @Override
    public Integer save(CompleteAulaDTO aulaDTO) {

        Professor professor = professorService.findById(aulaDTO.getProfessor());
        Materia materia = materiaService.findById(aulaDTO.getMateria());
        Turma turma = turmaService.findById(aulaDTO.getTurma());
        Aula aula = new Aula(aulaDTO.getData(), professor, materia, turma);

        return aulaRepository.save(aula).getId();
    }

    @Override
    public Aula findById(Integer id) {
        return aulaRepository.findById(id)
                .map( aula -> {
                    if (aula.isPresent() == true){
                        return aula;
                    }
                    else {
                        throw new EntityNotFoundException("Aula não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Aula não encontrada"));
    }

    @Override
    public ReturnAulaDTO findByIdReturnDTO(Integer id) {
        Aula aula = findById(id);

        ReturnProfessorDTO professorDTO = new ReturnProfessorDTO(aula.getProfessor().getId(), aula.getProfessor().getNome(), aula.getProfessor().getCpf(), aula.getProfessor().getPeriodosDeTrabalho());
        CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(aula.getMateria().getNome());
        CompleteSalaDTO salaDTO = new CompleteSalaDTO(aula.getTurma().getSala().getSala(), aula.getTurma().getSala().getPeriodosDisponiveis());
        ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(aula.getTurma().getNome(), salaDTO);
        ReturnAulaDTO aulaDTO = new ReturnAulaDTO(aula.getData(), professorDTO, materiaDTO, turmaDTO);

        return aulaDTO;
    }

    @Override
    public List<ReturnAulaInProfessorDTO> findByProfessorId(Integer id) {
        List<ReturnAulaInProfessorDTO> informacoesAulaByIdProfessorDTO = new ArrayList<>();

        return professorRepository.findById(id)
                .map(professor -> {
                    List<Aula> aulas = aulaRepository.findByProfessorId(professor.getId()).orElseThrow(
                            () -> new EntityNotFoundException("O professor selecionado não possui nenhuma aula"));

                    aulas.stream()
                            .map(aula -> {
                                Materia materia = materiaRepository.findById(aula.getMateria().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Materia não encontrada"));
                                CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materia.getNome());
                                return new ReturnAulaInProfessorDTO(aula.getData(), materiaDTO);
                            })
                            .forEach(informacoesAulaByIdProfessorDTO::add);

                    return informacoesAulaByIdProfessorDTO;
                })
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

    }

    @Override
    public List<ReturnAulaDTO> filterAll(CompleteAulaDTO aulaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        ReturnProfessorDTO professorDTO = (aulaDTO.getProfessor() != null) ?
                professorService.findByIdReturnDTO(aulaDTO.getProfessor()) :
                new ReturnProfessorDTO();
        Professor professor = new Professor(professorDTO.getNome(), professorDTO.getCpf(), professorDTO.getPeriodosDeTrabalho());

        CompleteMateriaDTO materiaDTO = (aulaDTO.getMateria() != null) ?
                materiaService.findByIdReturnDTO(aulaDTO.getMateria()) :
                new CompleteMateriaDTO();
        Materia materia = new Materia(materiaDTO.getNome());

        ReturnTurmaDTO turmaDTO = (aulaDTO.getTurma() != null) ?
                turmaService.findByIdReturnDTO(aulaDTO.getTurma()) :
                new ReturnTurmaDTO();

        Sala sala = new Sala(turmaDTO.getSala().getSala());

        Turma turma = new Turma(turmaDTO.getNome(), sala);

        Aula aula = new Aula(aulaDTO.getData(), professor, materia, turma);

        Example example = Example.of(aula, matcher);
        List<Aula> aulas = aulaRepository.findAll(example);

        return aulas.stream()
                .map( aula1 -> {
                    ReturnAulaDTO aulaDTO1 = findByIdReturnDTO(aula1.getId());
                    return aulaDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public Aula update(Integer id, Aula aula) {
        Aula aula1 = findById(id);
        aula.setId(aula1.getId());

        return aulaRepository.save(aula);
    }

    @Override
    public void deleteById(Integer id) {
        Aula aula = findById(id);

        aula.setPresent(false);
        aulaRepository.save(aula);
    }
}
