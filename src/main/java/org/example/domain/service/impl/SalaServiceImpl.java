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
                        throw new EntityNotFoundException("Sala não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Sala não encontrada"));
    }

    @Override
    public CompleteSalaDTO findByIdReturnDTO(Integer id) {
        Sala sala = findById(id);

        CompleteSalaDTO salaDTO = new CompleteSalaDTO(sala.getId(), sala.getSala(), sala.getPeriodosDisponiveis());

        return salaDTO;
    }

    @Override
    public List<CompleteSalaDTO> findByPeriodo(String periodo) {
        Periodo periodoObj = Periodo.valueOf(periodo);
        List<Sala> salasDisponiveis = salaRepository.findByPeriodo(periodoObj);

        return salasDisponiveis.stream()
                .map( sala -> new CompleteSalaDTO(sala.getId(), sala.getSala()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompleteSalaDTO> findAll() {
        List<Sala> salas = salaRepository.findAllOrderByIdDesc();

        return salas.stream()
                .map( sala1 -> new CompleteSalaDTO(sala1.getId(), sala1.getSala(), sala1.getPeriodosDisponiveis()))
                .collect(Collectors.toList());
    }

    @Override
    public Sala update(Integer id, Sala sala) {
        Sala salabanco = findById(id);

        sala.setId(salabanco.getId());
        sala.setPresent(true);

        return salaRepository.save(sala);
    }

    @Override
    public void deleteById(Integer id) {
        Sala sala = findById(id);

        sala.setPresent(false);

        salaRepository.save(sala);
    }
}
