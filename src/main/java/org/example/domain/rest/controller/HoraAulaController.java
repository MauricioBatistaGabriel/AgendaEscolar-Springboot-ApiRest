package org.example.domain.rest.controller;

import org.example.domain.entity.HoraAula;
import org.example.domain.rest.dto.CompleteHoraAulaDTO;
import org.example.domain.rest.dto.ReturnHoraAulaDTO;
import org.example.domain.service.HoraAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/horaaula")
public class HoraAulaController {

    @Autowired
    private HoraAulaService horaAulaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody CompleteHoraAulaDTO horaAulaDTO){
        return horaAulaService.save(horaAulaDTO);
    }

    @GetMapping("{id}")
    public HoraAula findById(@PathVariable Integer id){
        return horaAulaService.findById(id);
    }

    @GetMapping
    public List<ReturnHoraAulaDTO> findAll(){
        return horaAulaService.findAll();
    }

    @GetMapping("/byTurma/{id}")
    public List<ReturnHoraAulaDTO> findByTurmaId(@PathVariable Integer id){
        return horaAulaService.findByTurma(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public ReturnHoraAulaDTO update(@PathVariable Integer id, @RequestBody CompleteHoraAulaDTO horaAula){
        return horaAulaService.update(id, horaAula);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        horaAulaService.deleteById(id);
    }
}
