package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import br.com.batalharepg.avanade.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoController {
    private final HistoricoService historicoService;

    @GetMapping("/completo")
    public ResponseEntity<List<DadosTurnoResponse>> listarTodosTurnosExistentes() {
        return ResponseEntity.ok(historicoService.listarTodosTurnosExistentes());
    }
}
