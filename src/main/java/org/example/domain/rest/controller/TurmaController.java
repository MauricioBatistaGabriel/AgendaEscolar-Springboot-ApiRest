package org.example.domain.rest.controller;

import org.example.domain.entity.Turma;
import org.example.domain.rest.dto.CompleteTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaDTO;
import org.example.domain.rest.dto.UpdateTurmaDTO;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid CompleteTurmaDTO turmaDTO){
        return turmaService.save(turmaDTO);
    }

    @GetMapping("{id}")
    public ReturnTurmaDTO findById(@PathVariable Integer id){
        return turmaService.findByIdReturnDTO(id);
    }

    @GetMapping
    public List<ReturnTurmaDTO> findAll(){
        return turmaService.findAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public ReturnTurmaDTO update(@PathVariable Integer id, @RequestBody @Valid UpdateTurmaDTO turma){
        return turmaService.update(id, turma);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        turmaService.deleteById(id);
    }
}
