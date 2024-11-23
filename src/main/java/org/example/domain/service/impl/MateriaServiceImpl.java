package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.exception.RegraNegocioException;
import org.example.domain.repository.*;
import org.example.domain.rest.dto.CompleteMateriaDTO;
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
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private MateriaTurmaRepository materiaTurmaRepository;

    @Autowired
    private MateriaTurmaService materiaTurmaService;

    @Autowired
    private MateriaProfessorRepository materiaProfessorRepository;

    @Autowired
    private MateriaProfessorService materiaProfessorService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Override
    public Integer save(CompleteMateriaDTO materiaDTO) {
        Materia materia = new Materia(materiaDTO.getNome());
        materiaRepository.save(materia);
        return materia.getId();
    }

    @Override
    public Materia findById(Integer id) {
        return materiaRepository.findById(id)
                .map( materia -> {
                    if (materia.isPresent() == true){
                        return materia;
                    }
                    else {
                    throw new EntityNotFoundException("Matéria com o ID:" + id + " foi deletada");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Matéria com o ID:" + id + " não encontrada"));
    }

    @Override
    public CompleteMateriaDTO findByIdReturnDTO(Integer id) {
        Materia materia = findById(id);

        CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materia.getNome());

        return materiaDTO;
    }

    @Override
    public List<CompleteMateriaDTO> findMateriasByIdTurma(Integer id) {
        Turma turma = turmaService.findById(id);

        List<Materia> materias = materiaTurmaRepository.findMateriasByIdTurma(turma.getId());

        List<CompleteMateriaDTO> materiasDTO = new ArrayList<>();

        for (Integer i = 0; i < materias.size(); i++){
            CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materias.get(i).getNome());
            materiasDTO.add(materiaDTO);
        }

        return materiasDTO;
    }

    @Override
    public List<CompleteMateriaDTO> findMateriaByIdTurmaAndIdProfessor(Integer idTurma, Integer idProfessor) {
        Turma turma = turmaService.findById(idTurma);

        Professor professor = professorService.findById(idProfessor);

        List<Materia> materias = materiaRepository.findByIdTurmaAndIdProfessor(idTurma, idProfessor);

        List<CompleteMateriaDTO> materiasDTO = new ArrayList<>();

        for (Integer i = 0; i < materias.size(); i++){
            CompleteMateriaDTO materiaDTO = new CompleteMateriaDTO(materias.get(i).getNome());
            materiasDTO.add(materiaDTO);
        }

        return materiasDTO;
    }

    @Override
    public List<CompleteMateriaDTO> filterAll(CompleteMateriaDTO materiaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Materia materia = new Materia(materiaDTO.getNome());

        Example example = Example.of(materia, matcher);
        List<Materia> materias = materiaRepository.findAll(example);

        return materias.stream()
                .map(m -> new CompleteMateriaDTO(m.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public Materia update(Integer id, Materia materia) {
        Materia materia1 = findById(id);
        materia.setId(materia1.getId());

        return materiaRepository.save(materia);
    }

    @Override
    public void deleteById(Integer id) {
        Materia materia = materiaService.findById(id);

        //VALIDA SE MATÉRIA POSSUI RELAÇÃO COM PROFESSOR
        List<MateriaProfessor> materiaProfessorList = materiaProfessorService.findMateriaProfessorByIdMateria(materia.getId());
       if(!materiaProfessorList.isEmpty()){
           List<String> nomeProfessores = new ArrayList<>();

           materiaProfessorList.forEach(materiaProfessor ->
                   nomeProfessores.add(materiaProfessor.getProfessor().getNome()));

           throw new RegraNegocioException("Matéria não pode ser excluida, pois os professores: " + nomeProfessores.toString() + " possuem relação com matéria");
       }

       //VALIDA SE MATÉRIA POSSUI RELAÇÃO COM TURMA
        List<MateriaTurma> materiaTurmaList = materiaTurmaService.findMateriaTurmaByMateriaId(materia.getId());
        if (!materiaTurmaList.isEmpty()){
            List<String> nomeTurmas = new ArrayList<>();

            materiaTurmaList.forEach(materiaTurma ->
                    nomeTurmas.add(materiaTurma.getMateria().getNome()));

            throw new RegraNegocioException("Matéria não pode ser excluida, pois as turmas: " + nomeTurmas.toString() + " possuem relação com matéria");
        }

        // VALIDA SE MATÉRIA POSSUI RELAÇÃO COM AVALIAÇÃO
        List<Avaliacao> avaliacaoList = avaliacaoService.findAvaliacoesByMateriaId(materia.getId());
        if (!avaliacaoList.isEmpty()){
            List<Integer> idAvaliação = new ArrayList<>();

            avaliacaoList.forEach(avaliacao ->
                    idAvaliação.add(avaliacao.getId()));

            throw new RegraNegocioException("Matéria não pode ser excluida, pois as turmas com ID: " + idAvaliação + " possuem relação com matéria");
        }

        materia.setPresent(false);
        materiaRepository.save(materia);
    }
}
