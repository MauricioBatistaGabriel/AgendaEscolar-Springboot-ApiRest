package org.example.domain.entity;

import lombok.*;
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
    private boolean isPresent = true;

    public UsuarioAdm(String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.isPresent = true;
    }
}
