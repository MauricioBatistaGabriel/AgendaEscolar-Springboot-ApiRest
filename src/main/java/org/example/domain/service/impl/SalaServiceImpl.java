package org.example.domain.service.impl;

import org.example.domain.entity.Sala;
import org.example.domain.enums.Periodo;
import org.example.domain.repository.SalaRepository;
import org.example.domain.rest.dto.CompleteSalaDTO;
import org.example.domain.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaServiceImpl implements SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public Integer save(CompleteSalaDTO salaDTO) {
        Sala sala = new Sala(salaDTO.getSala());
        return salaRepository.save(sala).getId();
    }

    @Override
    public Sala findById(Integer id) {
        return salaRepository.findById(id)
                .map( sala -> {
                    if (sala.isPresent() == true) {
                        return sala;
                    }
                    else {
                        throw new EntityNotFoundException("Sala com o ID:" + id + " foi deletada");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Sala com o ID:" + id + " não encontrada"));
    }

    @Override
    public CompleteSalaDTO findByIdReturnDTO(Integer id) {
        Sala sala = findById(id);

        CompleteSalaDTO salaDTO = new CompleteSalaDTO(sala.getSala(), sala.getPeriodosDisponiveis());

        return salaDTO;
    }

    @Override
    public List<CompleteSalaDTO> findByPeriodo(Periodo periodo) {
        List<Sala> salasDisponiveis = salaRepository.findByPeriodo(periodo);

        return salasDisponiveis.stream()
                .map( sala -> new CompleteSalaDTO(sala.getSala(), sala.getPeriodosDisponiveis()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompleteSalaDTO> filterAll(CompleteSalaDTO salaDTO) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Sala sala = new Sala(salaDTO.getSala());

        Example example = Example.of(sala, matcher);
        List<Sala> salas = salaRepository.findAll(example);

        return salas.stream()
                .map( sala1 -> new CompleteSalaDTO(sala1.getSala(), sala1.getPeriodosDisponiveis()))
                .collect(Collectors.toList());
    }

    @Override
    public Sala update(Integer id, Sala sala) {
        Sala sala1 = findById(id);

        sala.setId(sala1.getId());

        return salaRepository.save(sala);
    }

    @Override
    public void deleteById(Integer id) {
        Sala sala = findById(id);

        sala.setPresent(false);

        salaRepository.save(sala);
    }
}
