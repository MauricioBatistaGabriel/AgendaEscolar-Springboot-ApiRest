package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.enums.Periodo;
import org.example.domain.exception.EntityNotDisponibleException;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TurmaServiceImpl implements TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaTurmaService materiaTurmaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoTurmaService alunoTurmaService;

    @Autowired
    private AvaliacaoTurmaRepository avaliacaoTurmaRepository;

    @Override
    @Transactional
    public Integer save(CompleteTurmaDTO turmaDTO) {
        Sala sala = salaService.findById(turmaDTO.getSala());

        Turma turma = new Turma(turmaDTO.getNome(), sala, turmaDTO.getPeriodo());
        Turma turma1 = turmaRepository.save(turma);

        //Valida se periodo da sala está disponivel para a turma
        if (!sala.getPeriodosDisponiveis().contains(turmaDTO.getPeriodo())){
            throw new EntityNotDisponibleException("A sala já possui turma nesse periodo");
        }

        //Remove o periodo da sala utilizado pela turma criada como fora da lista de periodos disponiveis
        sala.getPeriodosDisponiveis().remove(turmaDTO.getPeriodo());
        salaService.update(sala.getId(), sala);

//      CRIA RELAÇÃO ALUNO-TURMA
        if (turmaDTO.getAlunos().size() != 0){

            for(Integer index = 0; index < turmaDTO.getAlunos().size(); index++){
                Aluno aluno = alunoService.findById(turmaDTO.getAlunos().get(index));

                CompleteAlunoTurmaDTO alunoTurmaDTO = new CompleteAlunoTurmaDTO(aluno.getId(), turma1.getId());

                alunoTurmaService.save(alunoTurmaDTO);
            }

        }
        else{
            throw new EntityNotFoundException("Nenhum aluno foi selecionado");
        }

//      CRIA RELAÇÃO MATÉRIA-TURMA
        if (turmaDTO.getMaterias().size() != 0){
            turmaDTO.getMaterias().stream()
                    .map(materiaId -> materiaService.findById(materiaId))
                    .map(materia -> new CompleteMateriaTurmaDTO(materia.getId(), turma1.getId()))
                    .forEach(materiaTurmaDTO -> materiaTurmaService.save(materiaTurmaDTO));
        }
        else{
            throw new EntityNotDisponibleException("Nenhuma máteria foi selecionada");
        }

        return turma1.getId();
    }

    @Override
    public Turma findById(Integer id) {
        return turmaRepository.findById(id)
                .map( turma -> {
                    if (turma.isPresent() == true) {
                        return turma;
                    }
                    else {
                        throw new EntityNotFoundException("Turma não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Turma não encontrada"));
    }

    @Override
    public ReturnTurmaDTO findByIdReturnDTO(Integer id) {
        Turma turma = findById(id);

        CompleteSalaDTO salaDTOComplete = salaService.findByIdReturnDTO(turma.getSala().getId());
        ReturnSalaDTO salaDTO = new ReturnSalaDTO(salaDTOComplete.getId(), salaDTOComplete.getSala());
        List<ReturnMateriaDTO> materiaDTOS = materiaService.findByIdTurma(turma.getId());
        ReturnTurmaDTO turmaDTO = new ReturnTurmaDTO(turma.getId(), turma.getNome(), turma.getPeriodo(), salaDTO, materiaDTOS);

        return turmaDTO;
    }

    @Override
    public ReturnTurmaInOtherClassDTO findByIdTurmaInOtherClass(Integer id) {
        Turma turma = findById(id);

        CompleteSalaDTO salaDTO = new CompleteSalaDTO(turma.getSala().getId(), turma.getSala().getSala(), turma.getSala().getPeriodosDisponiveis());
        ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(turma.getId(), turma.getNome(), salaDTO);

        return turmaDTO;
    }

    @Override
    public ReturnTurmaInOtherClassDTO findTurmaByIdAvaliacao(Integer id) {
        Turma turma = avaliacaoTurmaRepository.findTurmaByIdAvaliacao(id);
        CompleteSalaDTO salaDTO = new CompleteSalaDTO(turma.getSala().getSala(), turma.getSala().getPeriodosDisponiveis());
        ReturnTurmaInOtherClassDTO turmaDTO = new ReturnTurmaInOtherClassDTO(turma.getNome(), salaDTO);
        return turmaDTO;
    }

    @Override
    public List<Turma> findByMateriaId(Integer id) {
        Materia materia = materiaService.findById(id);

        List<Turma> turmas = turmaRepository.findByMateriaId(materia.getId());
        return turmas;
    }

    @Override
    public Turma findByAlunoId(Integer id) {
        Aluno aluno = alunoService.findById(id);

        return turmaRepository.findByAlunoId(aluno.getId()).orElse(null);
    }


    //MÉTODO NÃO BUSCA TURMA POR MATÉRIA
    @Override
    public List<ReturnTurmaDTO> findAll() {
        List<Turma> turmas = turmaRepository.findAllOrderByIdDesc();

        return turmas.stream()
                .map( turma1 -> {
                    ReturnTurmaDTO turmaDTO1 = findByIdReturnDTO(turma1.getId());
                    return turmaDTO1;
                }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReturnTurmaDTO update(Integer id, UpdateTurmaDTO turmaDTO) {
        Turma turmaBanco = findById(id);

        Turma turmaNova = new Turma();
        turmaNova.setId(turmaBanco.getId());
        turmaNova.setPresent(true);

        Sala salaNova = salaService.findById(turmaDTO.getSala());
        Sala salaOld = salaService.findById(turmaBanco.getSala().getId());
        Set<Periodo> periodoDaSalaNova = new HashSet<>();
        Set<Periodo> periodoDaSalaOld = new HashSet<>();

        //Valida se periodo da sala está disponivel para a turma
        if (!salaNova.getPeriodosDisponiveis().contains(turmaDTO.getPeriodo()) && (salaNova.getId() != turmaBanco.getSala().getId() || turmaDTO.getPeriodo() != turmaBanco.getPeriodo())){
            throw new EntityNotDisponibleException("A sala já possui turma nesse periodo");
        }

        //Valida se a sala foi alterada, para editar ou não os periodos das salas
        if(!salaNova.equals(turmaBanco.getSala())){
            //Caso tenha alterado a sala, pega os periodos que a sala possui e adiciona o periodo da turma
            periodoDaSalaOld.addAll(salaOld.getPeriodosDisponiveis());
            periodoDaSalaOld.add(turmaBanco.getPeriodo());
            salaOld.setPeriodosDisponiveis(periodoDaSalaOld);
            salaService.update(salaOld.getId(), salaOld);

            //Retira o periodo disponivel da nova sala, utiliando o periodo da turma da tela de edição
            periodoDaSalaNova.addAll(salaNova.getPeriodosDisponiveis());
            periodoDaSalaNova.remove(turmaDTO.getPeriodo());
            salaNova.setPeriodosDisponiveis(periodoDaSalaNova);
            salaService.update(salaNova.getId(), salaNova);
        }
        //Valida se a sala é igual e se o periodo foi alterado, atualiza os periodos disponiveis da sala
        if (salaNova.equals(turmaBanco.getSala()) && !turmaDTO.getPeriodo().equals(turmaBanco.getPeriodo())) {
            periodoDaSalaNova.addAll(salaOld.getPeriodosDisponiveis());
            periodoDaSalaNova.add(turmaBanco.getPeriodo());
            periodoDaSalaNova.remove(turmaDTO.getPeriodo());
            salaNova.setPeriodosDisponiveis(periodoDaSalaNova);
            salaService.update(salaNova.getId(), salaNova);
        }

        Sala sala = salaService.findById(turmaDTO.getSala());
        turmaNova.setSala(sala);
        turmaNova.setNome(turmaDTO.getNome());
        turmaNova.setPeriodo(turmaDTO.getPeriodo());

        turmaRepository.save(turmaNova);

        //Materias que a turma ja possui atualmente
        List<ReturnMateriaDTO> materiasDTOBanco = materiaService.findByIdTurma(turmaBanco.getId()).stream()
                .map(materia -> new ReturnMateriaDTO(materia.getId(), materia.getNome()))
                .collect(Collectors.toList());

        //Valida se as matérias presentes na turmaBanco ainda estão presente na turmaDTO
        materiasDTOBanco.stream()
                .forEach(materiaDTOBanco -> {
                    boolean exists = turmaDTO.getMaterias().stream()
                            .anyMatch(materiaNova -> materiaNova.equals(materiaDTOBanco.getId()));
                    if(!exists){
                        materiaTurmaService.deleteByMateriaIdAndTurmaId(materiaDTOBanco.getId(), turmaBanco.getId());
                    }
                });

        //Valida se as matérias presente na turmaDTO ja estão presentes na turmaBanco
        turmaDTO.getMaterias().stream().forEach(materiaNova -> {
            boolean exists = materiasDTOBanco.stream()
                    .anyMatch(materiaDTOBanco -> materiaDTOBanco.getId().equals(materiaNova));
            if(!exists){
                //Salva a matéria nova
                CompleteMateriaTurmaDTO novaMateria = CompleteMateriaTurmaDTO.builder()
                        .materia(materiaNova)
                        .Turma(turmaNova.getId())
                        .build();
                materiaTurmaService.save(novaMateria);
            }
        });

        //Busca as matérias atualizadas e insere no professor para retornar
        List<ReturnMateriaDTO> materiasAtualizadas = materiaService.findByIdTurma(turmaNova.getId()).stream()
                .map(materia -> new ReturnMateriaDTO(materia.getId(), materia.getNome()))
                .collect(Collectors.toList());

        ReturnSalaDTO salaDTO = new ReturnSalaDTO(turmaNova.getSala().getId(), turmaNova.getSala().getSala());
        ReturnTurmaDTO turmaReturnDTO = ReturnTurmaDTO.builder()
                .id(turmaNova.getId())
                .nome(turmaNova.getNome())
                .periodo(turmaNova.getPeriodo())
                .sala(salaDTO)
                .materias(materiasAtualizadas)
                .build();

        return turmaReturnDTO;
    }

    @Override
    public void deleteById(Integer id) {
        Turma turma = findById(id);

        turma.setPresent(false);

        turmaRepository.save(turma);
    }
}
