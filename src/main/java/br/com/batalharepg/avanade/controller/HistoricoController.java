package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResumoResponse;
import br.com.batalharepg.avanade.service.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoController {
    private final HistoricoService historicoService;

    @Operation(summary = "Busca o histórico detalhado de uma batalha por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "listado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = BatalhaDetalhesResponse.class)) })})
    @GetMapping("/completo/{uuid}")
    public ResponseEntity<BatalhaDetalhesResponse> buscarHistoricoDetalhadoDeBatalha(@PathVariable UUID uuid) {
        return ResponseEntity.ok(historicoService.buscarHistoricoDetalhadoDeBatalha(uuid));
    }

    @Operation(summary = "Busca o histórico resumido de uma batalha por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "listado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = BatalhaResumoResponse.class)) })})
    @GetMapping("/resumo/{uuid}")
    public ResponseEntity<BatalhaResumoResponse> buscarHistoricoResumidoDeBatalha(@PathVariable UUID uuid) {
        return ResponseEntity.ok(historicoService.listarTodosTurnosExistentesPorBatalha((uuid)));
    }
}
