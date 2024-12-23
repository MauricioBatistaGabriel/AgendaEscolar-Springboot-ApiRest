package org.example.domain.exception;

import net.bytebuddy.implementation.bind.annotation.Super;

public class ValorNuloEnviadoException extends RuntimeException{

    public ValorNuloEnviadoException(String message){
        super(message);
    }
}
