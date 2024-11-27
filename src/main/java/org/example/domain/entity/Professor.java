package org.example.domain.entity;

import lombok.Builder;
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
@Builder
public class Professor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Integer id;

    private String email;

    private String senha;

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

    public Professor(String email, String senha, String nome, String cpf, Set<Periodo> periodosDeTrabalho){
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.periodosDeTrabalho = periodosDeTrabalho;
    }

    public Professor(String nome, String cpf, Set<Periodo> periodosDeTrabalho){
        this.nome = nome;
        this.cpf = cpf;
        this.periodosDeTrabalho = periodosDeTrabalho;
    }

    public Professor(Integer id, String email, String senha, String nome, String cpf, Set<Periodo> periodosDeTrabalho, boolean isPresent) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.periodosDeTrabalho = periodosDeTrabalho;
        this.isPresent = isPresent;
    }

    public Professor() {
    }
}
