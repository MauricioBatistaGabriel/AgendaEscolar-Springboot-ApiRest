package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTurmaInOtherClassDTO {

    private String nome;

    private CompleteSalaDTO sala;
}
