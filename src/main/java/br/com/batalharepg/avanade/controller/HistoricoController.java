package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import br.com.batalharepg.avanade.service.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoController {
    private final HistoricoService historicoService;

    @Operation(summary = "Lista todas os turnos de todas as batalhas ordenado por id da batalha e número do turno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "listado com sucesso",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = DadosTurnoResponse.class))) })})
    @GetMapping("/completo")
    public ResponseEntity<List<DadosTurnoResponse>> listarTodosTurnosExistentes() {
        return ResponseEntity.ok(historicoService.listarTodosTurnosExistentes());
    }

    @Operation(summary = "Lista todas os turnos de uma batalha específica ordenado número do turno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "listado com sucesso",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = DadosTurnoResponse.class))) })})
    @GetMapping("/batalha/{uuid}")
    public ResponseEntity<List<DadosTurnoResponse>> listarTodosTurnosExistentesPorBatalha(@PathVariable UUID uuid) {
        return ResponseEntity.ok(historicoService.listarTodosTurnosExistentesPorBatalha((uuid)));
    }
}
