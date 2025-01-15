package org.example.domain.service.impl;

import org.example.domain.entity.HoraAula;
import org.example.domain.repository.HoraAulaRepository;
import org.example.domain.rest.dto.CompleteHoraAulaDTO;
import org.example.domain.rest.dto.ReturnHoraAulaDTO;
import org.example.domain.service.HoraAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoraAulaServiceImpl implements HoraAulaService {

    @Autowired
    private HoraAulaRepository horaAulaRepository;

    @Override
    public Integer save(CompleteHoraAulaDTO horaAulaDTO) {
        HoraAula horaAula = HoraAula.builder()
                .horaInicial(horaAulaDTO.getHoraInicial())
                .horaFinal(horaAulaDTO.getHoraFinal())
                .periodoDaHoraAula(horaAulaDTO.getPeriodoDaHoraAula())
                .isPresent(true)
                .build();

        horaAulaRepository.save(horaAula);
        return horaAula.getId();
    }

    @Override
    public HoraAula findById(Integer id) {
        return horaAulaRepository.findById(id)
                .map(horaAula -> {
                    if (horaAula.isPresent() == true){
                        return horaAula;
                    }
                    else {
                        throw new EntityNotFoundException("Hora aula não existe");
                    }
                }).orElseThrow( () ->
                        new EntityNotFoundException("Hora aula não encontrada"));
    }

    @Override
    public CompleteHoraAulaDTO findByIdReturnDTO(Integer id) {
        HoraAula horaAula = findById(id);

        CompleteHoraAulaDTO horaAulaDTO = CompleteHoraAulaDTO.builder()
                .horaInicial(horaAula.getHoraInicial())
                .horaFinal(horaAula.getHoraFinal())
                .periodoDaHoraAula(horaAula.getPeriodoDaHoraAula())
                .isPresent(horaAula.isPresent())
                .build();

        return horaAulaDTO;
    }

    @Override
    public List<ReturnHoraAulaDTO> findAll() {
        List<HoraAula> horaAulas = horaAulaRepository.findAllOrderByIdDesc();

        List<ReturnHoraAulaDTO> horasAulaDTO = horaAulas.stream()
                .map(horaAula -> new ReturnHoraAulaDTO(horaAula.getId(), horaAula.getHoraInicial(), horaAula.getHoraFinal(), horaAula.getPeriodoDaHoraAula()))
                .collect(Collectors.toList());

        return horasAulaDTO;
    }

    @Override
    public List<ReturnHoraAulaDTO> findByTurma(Integer id) {
        List<HoraAula> horaAulas = horaAulaRepository.findByTurmaId(id);

        return horaAulas.stream()
                .map( horaAula -> new ReturnHoraAulaDTO(horaAula.getId(), horaAula.getHoraInicial(), horaAula.getHoraFinal(), horaAula.getPeriodoDaHoraAula()))
                .collect(Collectors.toList());
    }

    @Override
    public ReturnHoraAulaDTO update(Integer id, CompleteHoraAulaDTO horaAulaDTO) {
        HoraAula horaAulaBanco = findById(id);

        HoraAula horaAulaNova = HoraAula.builder()
                        .id(horaAulaBanco.getId())
                        .horaInicial(horaAulaDTO.getHoraInicial())
                        .horaFinal(horaAulaDTO.getHoraFinal())
                        .periodoDaHoraAula(horaAulaDTO.getPeriodoDaHoraAula())
                        .isPresent(true)
                        .build();

        horaAulaNova.setId(horaAulaBanco.getId());
        horaAulaNova.setPresent(true);

        horaAulaRepository.save(horaAulaNova);

        ReturnHoraAulaDTO returnHoraAulaDTO = new ReturnHoraAulaDTO(horaAulaNova.getId(), horaAulaNova.getHoraInicial(), horaAulaNova.getHoraFinal(), horaAulaNova.getPeriodoDaHoraAula());

        return returnHoraAulaDTO;
    }

    @Override
    public void deleteById(Integer id) {
        HoraAula horaAula = findById(id);

        horaAula.setPresent(false);
        horaAulaRepository.save(horaAula);
    }
}
