package org.example.domain.service;

import org.example.domain.entity.Aluno;
import org.example.domain.rest.dto.CompleteAlunoDTO;
import org.example.domain.rest.dto.ReturnAllAlunoDTO;
import org.example.domain.rest.dto.ReturnAlunoOnlyNameDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AlunoService {

    Integer save(CompleteAlunoDTO alunoDTO);
    UserDetails autenticar(Aluno aluno);
    Aluno findById(Integer id);
    CompleteAlunoDTO findByIdReturnDTO(Integer id);
    CompleteAlunoDTO findAlunoByIdNota(Integer id);
    List<ReturnAllAlunoDTO> findAll();
    List<ReturnAlunoOnlyNameDTO> findSemTurma();
    Aluno update(Integer id, Aluno aluno);

    void deleteById(Integer id);
}
