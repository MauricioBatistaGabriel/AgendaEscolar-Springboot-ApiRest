package org.example.domain.service.impl;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.AlunoTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.AlunoRepository;
import org.example.domain.repository.AlunoTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;
import org.example.domain.service.AlunoService;
import org.example.domain.service.AlunoTurmaService;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AlunoTurmaServiceImpl implements AlunoTurmaService {

    @Autowired
    private AlunoTurmaRepository alunoTurmaRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @Override
    public Integer save(AlunoTurma alunoTurma) {
        return alunoTurmaRepository.save(alunoTurma).getId();
    }

    @Override
    public List<AlunoTurma> findByTurmaId(Integer turmaId) {
        return alunoTurmaRepository.findByTurmaId(turmaId);
    }

    @Override
    public AlunoTurma findById(Integer alunoTurmaId) {
        return alunoTurmaRepository.findById(alunoTurmaId)
                .map( alunoTurma -> {
                    if (alunoTurma.isPresent()) {
                        return alunoTurma;
                    }
                    else {
                        throw new EntityNotFoundException("Relação aluno_turma não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Relação aluno_turma não encontrada"));
    }

    @Override
    public AlunoTurma update(AlunoTurma alunoTurma) {
        AlunoTurma alunoTurmaDb = findById(alunoTurma.getId());

        alunoTurma.setId(alunoTurmaDb.getId());

        return alunoTurmaRepository.save(alunoTurma);
    }

    @Override
    public void delete(AlunoTurma alunoTurma) {
        alunoTurma.setPresent(false);

        update(alunoTurma);
    }
}
