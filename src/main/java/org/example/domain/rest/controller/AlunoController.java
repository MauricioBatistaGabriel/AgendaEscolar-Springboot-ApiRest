package org.example.domain.rest.controller;

import org.example.domain.entity.Aluno;
import org.example.domain.rest.dto.CompleteAlunoDTO;
import org.example.domain.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody CompleteAlunoDTO alunoDTO){
        return alunoService.save(alunoDTO);
    }

    @GetMapping("{id}")
    public CompleteAlunoDTO findById(@PathVariable Integer id){
        return alunoService.findByIdReturnDTO(id);
    }

    @GetMapping
    public List<CompleteAlunoDTO> filterAll(@RequestBody CompleteAlunoDTO alunoDTO){
        return alunoService.filterAll(alunoDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Aluno update(@PathVariable Integer id, @RequestBody Aluno aluno){
        return alunoService.update(id, aluno);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        alunoService.deleteById(id);
    }
}
