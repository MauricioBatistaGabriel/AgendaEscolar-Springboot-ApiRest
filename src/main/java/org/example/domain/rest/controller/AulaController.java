package org.example.domain.rest.controller;

import org.example.domain.entity.Aula;
import org.example.domain.rest.dto.CompleteAulaDTO;
import org.example.domain.rest.dto.ReturnAulaDTO;
import org.example.domain.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/aula")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid CompleteAulaDTO aulaDTO){
        return aulaService.save(aulaDTO);
    }

    @GetMapping("{id}")
    public ReturnAulaDTO findById(@PathVariable Integer id){
        return aulaService.findByIdReturnDTO(id);
    }

    @GetMapping
    public List<ReturnAulaDTO> findAll(){
        return aulaService.findAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public ReturnAulaDTO update(@PathVariable Integer id, @RequestBody @Valid CompleteAulaDTO aula){
        return aulaService.update(id, aula);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        aulaService.deleteById(id);
    }
}
