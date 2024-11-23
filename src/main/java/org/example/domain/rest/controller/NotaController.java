package org.example.domain.rest.controller;

import org.example.domain.rest.dto.CompleteNotaDTO;
import org.example.domain.rest.dto.ReturnNotaDTO;
import org.example.domain.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/nota")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody CompleteNotaDTO notaDTO){
        return notaService.save(notaDTO);
    }

    @GetMapping("{id}")
    public ReturnNotaDTO findById(@PathVariable Integer id){
        return notaService.findByIdReturnDTO(id);
    }

    @GetMapping
    public List<ReturnNotaDTO> filterAll(@RequestBody CompleteNotaDTO notaDTO){
        return notaService.filterAll(notaDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public void deleteById(@PathVariable Integer id){
        notaService.deleteById(id);
    }
}
