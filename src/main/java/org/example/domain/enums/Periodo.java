package org.example.domain.enums;

public enum Periodo {
    matutino("matutino"),
    vespertino("vespertino"),
    noturno("noturno");

    private String valor;

    Periodo(String valor){
        this.valor = valor;
    }
}
