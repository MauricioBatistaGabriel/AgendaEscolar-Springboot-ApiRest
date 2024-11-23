package org.example.domain.rest.controller;

import org.example.domain.entity.Avaliacao;
import org.example.domain.rest.dto.CompleteAvaliacaoDTO;
import org.example.domain.rest.dto.ReturnAvaliacaoDTO;
import org.example.domain.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody CompleteAvaliacaoDTO avaliacaoDTO){
        return avaliacaoService.save(avaliacaoDTO);
    }

    @GetMapping("{id}")
    public ReturnAvaliacaoDTO findById(@PathVariable Integer id){
        return avaliacaoService.findByIdReturnDTO(id);
    }

    @GetMapping
    public List<ReturnAvaliacaoDTO> filterAll(@RequestBody CompleteAvaliacaoDTO avaliacaoDTO){
        return avaliacaoService.filterAll(avaliacaoDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Avaliacao update(@PathVariable Integer id, @RequestBody Avaliacao avaliacao){
        return avaliacaoService.update(id, avaliacao);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        avaliacaoService.deleteById(id);
    }
}
