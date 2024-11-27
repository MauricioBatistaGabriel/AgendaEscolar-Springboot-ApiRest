package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.Materia;
import org.example.domain.entity.Nota;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.NotaRepository;
import org.example.domain.rest.dto.*;
import org.example.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private NotaService notaService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private AvaliacaoTurmaService avaliacaoTurmaService;

    @Override
    public Integer save(CompleteNotaDTO notaDTO) {
        Aluno aluno = alunoService.findById(notaDTO.getAluno());

        Avaliacao avaliacao = avaliacaoService.findById(notaDTO.getAvaliacao());

        Nota nota = new Nota(notaDTO.getNota(), aluno, avaliacao);
        return notaRepository.save(nota).getId();
    }

    @Override
    public Nota findById(Integer id) {
        return notaRepository.findById(id)
                .map( nota -> {
                    if (nota.isPresent() == true){
                        return nota;
                    }
                    else {
                        throw new EntityNotFoundException("Nota com o ID:" + id + " foi deletada");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Nota com o ID:" + id + " não encontrada"));
    }

    @Override
    public ReturnNotaDTO findByIdReturnDTO(Integer id) {
        Nota nota = findById(id);

        ReturnAvaliacaoDTO avaliacaoDTO = avaliacaoService.findAvaliacaoByNotaId(nota.getId());
        CompleteAlunoDTO alunoDTO = alunoService.findAlunoByIdNota(nota.getId());
        ReturnNotaDTO notaDTO = new ReturnNotaDTO(nota.getNota(), avaliacaoDTO, alunoDTO);

        return notaDTO;
    }

    @Override
    public List<ReturnNotaDTO> filterAll(CompleteNotaDTO notaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        CompleteAlunoDTO alunoDTO = (notaDTO.getAluno() != null) ?
                alunoService.findByIdReturnDTO(notaDTO.getAluno()) :
                new CompleteAlunoDTO();
        Aluno aluno = new Aluno(alunoDTO.getEmail(), alunoDTO.getSenha(), alunoDTO.getNome(), alunoDTO.getCpf(), alunoDTO.getIdade());

        CompleteMateriaDTO materiaDTO = (notaDTO.getAvaliacao() != null) ?
                avaliacaoService.findByIdReturnDTO(notaDTO.getAvaliacao()).getMateria() :
                new CompleteMateriaDTO();
        Materia materia = new Materia(materiaDTO.getNome());


        ReturnAvaliacaoDTO avaliacaoDTO = (notaDTO.getAvaliacao() != null) ?
                avaliacaoService.findByIdReturnDTO(notaDTO.getAvaliacao()) :
                new ReturnAvaliacaoDTO();

        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNumeroAvaliacao(), materia);

        Nota nota = new Nota(notaDTO.getNota(), aluno, avaliacao);

        Example example = Example.of(nota, matcher);
        List<Nota> notas = notaRepository.findAll(example);

        return notas.stream()
                .map( nota1 -> {
                    ReturnNotaDTO notaDTO1 = findByIdReturnDTO(nota1.getId());
                    return notaDTO1;
                }).collect(Collectors.toList());
    }

    @Override
    public Nota update(Integer id, Nota nota) {
        Nota nota1 = notaService.findById(id);

        nota.setId(nota1.getId());

        return notaRepository.save(nota);
    }

    @Override
    public void deleteById(Integer id) {
        Nota nota = notaService.findById(id);

        nota.setPresent(false);
        notaRepository.save(nota);
    }
}
