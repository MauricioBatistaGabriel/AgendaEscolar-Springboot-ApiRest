package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.enums.Periodo;
import org.hibernate.validator.constraints.br.CPF;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity(name = "PROFESSOR")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Integer id;

    @Column(name = "nome_professor")
    @Size(min = 3, max = 100, message = "{campo.nome.validation}")
    @NotEmpty(message = "{campo.nome}")
    private String nome;

    @Column(name = "cpf_professor")
    @CPF(message = "{campo.cpf.validation}")
    @NotEmpty(message = "{campo.cpf}")
    private String cpf;

    @ElementCollection(targetClass = Periodo.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "professor_periodos",
            joinColumns =
            @JoinColumn(name = "professor_id"))
    @Column(name = "PERIODO")
    private Set<Periodo> periodosDeTrabalho;

    private boolean isPresent = true;

    public Professor(String nome, String cpf, Set<Periodo> periodosDeTrabalho){
        this.nome = nome;
        this.cpf = cpf;
        this.periodosDeTrabalho = periodosDeTrabalho;
    }

    public Professor() {
    }
}
