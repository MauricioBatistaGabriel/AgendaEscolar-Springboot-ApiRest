package org.example.domain.service.impl;

import org.example.domain.entity.*;
import org.example.domain.exception.RegraNegocioException;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.NotaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private AvaliacaoTurmaService avaliacaoTurmaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private NotaService notaService;

    @Override
    @Transactional
    public Integer save(CompleteAvaliacaoDTO avaliacaoDTO) {
        Materia materia = materiaService.findById(avaliacaoDTO.getMateria());
        Turma turma = turmaService.findById(avaliacaoDTO.getTurma());

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);
        avaliacaoRepository.save(avaliacao);

        CompleteAvaliacaoTurmaDTO avaliacaoTurmaDTO = new CompleteAvaliacaoTurmaDTO(avaliacao.getId(), turma.getId());
        avaliacaoTurmaService.save(avaliacaoTurmaDTO);
        return avaliacao.getId();
    }

    @Override
    public Avaliacao findById(Integer id) {
        return avaliacaoRepository.findById(id)
                .map( avaliacao -> {
                    if (avaliacao.isPresent() == true) {
                        return avaliacao;
                    }
                    else {
                        throw new EntityNotFoundException("Avaliação com o ID:" + id + " foi deletada");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Avaliacão com o ID:" + id + " não encontrada"));
    }

    @Override
    public ReturnAvaliacaoDTO findByIdReturnDTO(Integer id) {
        Avaliacao avaliacao = findById(id);

        ReturnTurmaInOtherClassDTO turmaDTO = turmaService.findTurmaByIdAvaliacao(avaliacao.getId());
        CompleteMateriaDTO materiaDTO = materiaService.findByIdReturnDTO(avaliacao.getMateria().getId());
        ReturnAvaliacaoDTO avaliacaoDTO = new ReturnAvaliacaoDTO(avaliacao.getNumeroAvaliacao(), materiaDTO, turmaDTO);

        return avaliacaoDTO;
    }

    @Override
    public ReturnAvaliacaoDTO findByNotaId(Integer id) {
        Nota nota = notaService.findById(id);
        Avaliacao avaliacao = notaRepository.findAvalicaoByIdNota(nota.getId());
        ReturnAvaliacaoDTO avaliacaoDTO = findByIdReturnDTO(avaliacao.getId());

        return avaliacaoDTO;
    }

    @Override
    public List<Avaliacao> findByMateriaId(Integer id) {
        Materia materia = materiaService.findById(id);
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findByMateriaId(materia.getId());

        return avaliacaoList;
    }

    @Override
    public List<ReturnAvaliacaoDTO> filterAll(CompleteAvaliacaoDTO avaliacaoDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        CompleteMateriaDTO materiaDTO = (avaliacaoDTO.getMateria() != null) ?
                materiaService.findByIdReturnDTO(avaliacaoDTO.getMateria()) :
                new CompleteMateriaDTO();
        Materia materia = new Materia(materiaDTO.getNome());

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);

        Example example = Example.of(avaliacao, matcher);
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll(example);

        return avaliacoes.stream()
                .map(avaliacao1 -> {
                    ReturnAvaliacaoDTO avaliacaoDTO1 = findByIdReturnDTO(avaliacao1.getId());
                    return avaliacaoDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public Avaliacao update(Integer id, Avaliacao avaliacao) {
        Avaliacao avaliacao1 = findById(id);
        avaliacao.setId(avaliacao1.getId());

        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public void deleteById(Integer id) {
        Avaliacao avaliacao = findById(id);

//        AvaliacaoTurma avaliacaoTurma = avaliacaoTurmaService.findAvaliacaoTurmaByAvaliacaoId(avaliacao.getId());
//        if (avaliacaoTurma != null){
//            String nomeTurma = avaliacaoTurma.getTurma().getNome();
//
//            throw new RegraNegocioException("Avaliação não pode ser excluida, pois relação com a turma: " + nomeTurma);
//        }

        avaliacao.setPresent(false);
        avaliacaoRepository.save(avaliacao);
    }
}
