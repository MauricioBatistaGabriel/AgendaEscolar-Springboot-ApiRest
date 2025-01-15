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
    private ProfessorServiceImpl professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MateriaServiceImpl materiaService;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TurmaServiceImpl turmaService;

    @Autowired
    private HoraAulaServiceImpl horaAulaService;

    @Override
    public Integer save(CompleteAulaDTO aulaDTO) {

        Professor professor = professorService.findById(aulaDTO.getProfessor());
        Materia materia = materiaService.findById(aulaDTO.getMateria());
        Turma turma = turmaService.findById(aulaDTO.getTurma());
        HoraAula horaAula = horaAulaService.findById(aulaDTO.getHoraaula());
        Aula aula = new Aula(aulaDTO.getData(), professor, materia, turma, horaAula);

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

        ReturnProfessorDTOInAula professorDTO = new ReturnProfessorDTOInAula(aula.getProfessor().getId(), aula.getProfessor().getNome());
        ReturnMateriaDTO materiaDTO = new ReturnMateriaDTO(aula.getMateria().getId(), aula.getMateria().getNome());
        CompleteSalaDTO salaDTO = new CompleteSalaDTO(aula.getTurma().getSala().getSala(), aula.getTurma().getSala().getPeriodosDisponiveis());
        ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(aula.getTurma().getId(), aula.getTurma().getNome(), salaDTO);
        ReturnHoraAulaDTO horaAulaDTO = new ReturnHoraAulaDTO(aula.getHoraAula().getId(), aula.getHoraAula().getHoraInicial(), aula.getHoraAula().getHoraFinal(), aula.getHoraAula().getPeriodoDaHoraAula());
        ReturnAulaDTO aulaDTO = ReturnAulaDTO.builder()
                .id(aula.getId())
                .turma(turmaDTO)
                .materia(materiaDTO)
                .professor(professorDTO)
                .horaaula(horaAulaDTO)
                .data(aula.getData())
                .build();

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
    public List<ReturnAulaDTO> findAll() {
        List<Aula> aulas = aulaRepository.findAllOrderByIdDesc();

        return aulas.stream()
                .map( aula1 -> {
                    ReturnAulaDTO aulaDTO1 = findByIdReturnDTO(aula1.getId());
                    return aulaDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public ReturnAulaDTO update(Integer id, CompleteAulaDTO aulaDTO) {
        Aula aulaBanco = findById(id);

        Professor professor = professorService.findById(aulaDTO.getProfessor());
        Materia materia = materiaService.findById(aulaDTO.getMateria());
        Turma turma = turmaService.findById(aulaDTO.getTurma());
        HoraAula horaAula = horaAulaService.findById(aulaDTO.getHoraaula());

        ReturnTurmaInOtherClassDTO turmaDTO = turmaService.findByIdTurmaInOtherClass(turma.getId());
        ReturnMateriaDTO materiaDTO = new ReturnMateriaDTO(materia.getId(), materia.getNome());
        ReturnProfessorDTOInAula professorDTO = new ReturnProfessorDTOInAula(professor.getId(), professor.getNome());
        ReturnHoraAulaDTO horaAulaDTO = new ReturnHoraAulaDTO(horaAula.getId(), horaAula.getHoraInicial(), horaAula.getHoraFinal(), horaAula.getPeriodoDaHoraAula());

        Aula aulaNova = Aula.builder()
                .id(aulaBanco.getId())
                .data(aulaDTO.getData())
                .professor(professor)
                .materia(materia)
                .turma(turma)
                .horaAula(horaAula)
                .isPresent(true)
                .build();

        aulaRepository.save(aulaNova);

        ReturnAulaDTO returnAulaDTO = ReturnAulaDTO.builder()
                .id(aulaNova.getId())
                .turma(turmaDTO)
                .materia(materiaDTO)
                .professor(professorDTO)
                .horaaula(horaAulaDTO)
                .data(aulaNova.getData())
                .build();

        return returnAulaDTO;
    }

    @Override
    public void deleteById(Integer id) {
        Aula aula = findById(id);

        aula.setPresent(false);
        aulaRepository.save(aula);
    }
}
