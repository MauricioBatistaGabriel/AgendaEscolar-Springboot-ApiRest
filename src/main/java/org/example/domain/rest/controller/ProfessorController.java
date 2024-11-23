package org.example.domain.rest.controller;

import org.example.domain.entity.Professor;
import org.example.domain.enums.Periodo;
import org.example.domain.rest.dto.ReturnAulaInProfessorDTO;
import org.example.domain.rest.dto.CompleteProfessorDTO;
import org.example.domain.rest.dto.ReturnCompleteProfessorDTO;
import org.example.domain.rest.dto.ReturnProfessorDTO;
import org.example.domain.service.ProfessorService;
import org.example.domain.service.ProfessorTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorTurmaService professorTurmaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid CompleteProfessorDTO professorDTO){
        return professorService.save(professorDTO);
    }

    @GetMapping("{id}")
    public ReturnCompleteProfessorDTO findById(@PathVariable Integer id){
        return professorService.findByIdReturnDTOComplete(id);
    }

    @GetMapping("/aulas/{id}")
    public List<ReturnAulaInProfessorDTO> findAulaByIdProfessor(@PathVariable Integer id){
        return professorService.findAulaByIdProfessor(id);
    }

    @GetMapping("/turma/{id}")
    public List<Professor> findProfessorByIdTurma(@PathVariable Integer id){
        return professorTurmaService.findProfessoresByIdTurma(id);
    }

    @GetMapping("/materia/{idMateria}")
    public List<ReturnProfessorDTO> teste(@PathVariable Integer idMateria, @RequestBody Periodo periodo){
        return professorService.findProfessorDTOByMateriaAndPeriodo(idMateria, periodo);
    }

    @GetMapping
    public List<ReturnProfessorDTO> filterAll(@RequestBody CompleteProfessorDTO professorDTO){
        return professorService.filterAll(professorDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Professor update(@PathVariable Integer id, @RequestBody @Valid Professor professor){
        return professorService.update(id, professor);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        professorService.deleteById(id);
    }
}
