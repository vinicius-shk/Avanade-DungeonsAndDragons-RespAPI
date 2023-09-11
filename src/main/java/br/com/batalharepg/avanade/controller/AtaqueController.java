package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.service.CombateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ataque")
@RequiredArgsConstructor
public class AtaqueController {
    private final CombateService combateService;

    @Operation(summary = "Executa a fase de rolagem de dados de ataque da batalha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "calculado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = BatalhaDetalhesResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Ataque já realizado",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "batalha/tipo de acao nao encontrado",
            content = @Content) })
    @PatchMapping("/atacar/{batalhaUuid}")
    public ResponseEntity<BatalhaDetalhesResponse> calcularValorTotalAtaquePersonagens(@PathVariable UUID batalhaUuid) {
        return ResponseEntity.ok(combateService.calcularValorTotalAcaoPersonagens(batalhaUuid, TipoAcao.ATAQUE));
    }
}
