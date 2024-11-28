package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteUsuarioAdmDTO {

    @Email
    @NotEmpty(message = "{campo.email}")
    private String email;

    @NotEmpty(message = "{campo.senha}")
    private String senha;
}
