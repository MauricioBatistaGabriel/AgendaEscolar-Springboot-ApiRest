package org.example.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity(name = "ALUNO")
public class Aluno{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Integer id;

    private String email;
    private String senha;

    private String nome;

    private String cpf;

    private String idade;

    private boolean isPresent = true;

    public Aluno(String email, String senha, String nome, String cpf, String idade) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public Aluno(Integer id, String email, String senha, String nome, String cpf, String idade, boolean isPresent) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.isPresent = isPresent;
    }

    public Aluno(){}

}
