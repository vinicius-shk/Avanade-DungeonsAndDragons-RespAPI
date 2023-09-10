package br.com.batalharepg.avanade.configuration;

import br.com.batalharepg.avanade.exceptions.AtaqueJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.NomeExistenteException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionConfiguration {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(montarObjetoDeErro(e.getMessage()));
    }

    @ExceptionHandler(NomeExistenteException.class)
    public ResponseEntity<Object> handleNomeExistenteException(NomeExistenteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarObjetoDeErro(e.getMessage()));
    }

    @ExceptionHandler(AtaqueJaRealizadoException.class)
    public ResponseEntity<Object> handleAtaqueJaRealizadoException(AtaqueJaRealizadoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarObjetoDeErro(e.getMessage()));
    }

    private Map<String, String> montarObjetoDeErro(String mensagem) {
        Map<String, String> response = new HashMap<>();
        response.put("error", mensagem);
        return response;
    }
}
