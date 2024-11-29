package org.example.domain.service.impl;

import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.AvaliacaoTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AvaliacaoRepository;
import org.example.domain.repository.AvaliacaoTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteAvaliacaoTurmaDTO;
import org.example.domain.rest.dto.CompleteSalaDTO;
import org.example.domain.rest.dto.ReturnTurmaInOtherClassDTO;
import org.example.domain.service.AvaliacaoService;
import org.example.domain.service.AvaliacaoTurmaService;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AvaliacaoTurmaServiceImpl implements AvaliacaoTurmaService {

    @Autowired
    private AvaliacaoTurmaRepository avaliacaoTurmaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService turmaService;

    @Override
    public Integer save(CompleteAvaliacaoTurmaDTO avaliacaoTurmaDTO) {
        Avaliacao avaliacao = avaliacaoService.findById(avaliacaoTurmaDTO.getAvaliacao());

        Turma turma = turmaService.findById(avaliacaoTurmaDTO.getTurma());

        AvaliacaoTurma avaliacaoTurma = new AvaliacaoTurma(avaliacao, turma);
        return avaliacaoTurmaRepository.save(avaliacaoTurma).getId();
    }
}
