package org.example.domain.entity;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity(name = "USUARIOADM")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAdm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    private String email;

    private String senha;
}
