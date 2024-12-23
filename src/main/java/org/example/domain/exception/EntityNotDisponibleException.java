package org.example.domain.exception;

public class EntityNotDisponibleException extends RuntimeException{
    public EntityNotDisponibleException(String message){
        super(message);
    }
}
