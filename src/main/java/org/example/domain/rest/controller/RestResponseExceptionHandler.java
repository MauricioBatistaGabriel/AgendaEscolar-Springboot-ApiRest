package org.example.domain.rest.controller;

import org.example.domain.exception.EntityNotDisponibleException;
import org.example.domain.exception.RegraNegocioException;
import org.example.domain.exception.SenhaInvalidaException;
import org.example.domain.exception.ValorNuloEnviadoException;
import org.example.domain.rest.ApiError;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError =
                new ApiError(NOT_FOUND, ex.getLocalizedMessage(),
                        "EntityNotFound");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(value = { RegraNegocioException.class })
    protected ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex) {
        ApiError apiError =
                new ApiError(CONFLICT, ex.getLocalizedMessage(),
                        "ErroRegraNegocio");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(value = { SenhaInvalidaException.class})
    protected ResponseEntity<Object> handleSenhaInvalidaException(SenhaInvalidaException ex) {
        ApiError apiError =
                new ApiError(UNAUTHORIZED, ex.getLocalizedMessage(),
                        "InvalidPassword");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(value = { ValorNuloEnviadoException.class })
    protected ResponseEntity<Object> handleValorNuloEnviadoException(ValorNuloEnviadoException exception){
        ApiError apiError =
                new ApiError(BAD_REQUEST, exception.getLocalizedMessage(),
                        "Nenhum valor foi enviado");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(value = { EntityNotDisponibleException.class })
    protected  ResponseEntity<Object> handleEntityNotDisponibleException(EntityNotDisponibleException e){
        ApiError apiError =
                new ApiError(BAD_REQUEST, e.getLocalizedMessage(),
                        "Entidade não disponivel para relação");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
