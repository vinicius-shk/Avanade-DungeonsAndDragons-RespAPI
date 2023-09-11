package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.service.BatalhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/batalha")
@RequiredArgsConstructor
public class BatalhaController {
    private final BatalhaService batalhaService;

    @Operation(summary = "Cria uma batalha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "criado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = BatalhaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Defensor não é um monstro",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Atacante/Defensor não encontrado",
            content = @Content) })
    @PostMapping
    public ResponseEntity<BatalhaResponse> criarBatalha(@Valid @RequestBody CriarBatalhaRequest criarBatalhaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(batalhaService.criarBatalha(criarBatalhaRequest));
    }

    @Operation(summary = "Lista todas as batalhas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "listado com sucesso",
            content = { @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = BatalhaResponse.class))) })})
    @GetMapping
    public ResponseEntity<List<BatalhaResponse>> buscarTodasBatalhas() {
        return ResponseEntity.ok(batalhaService.buscarTodasBatalhas());
    }

    @Operation(summary = "Busca uma batalha por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "encontrado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = BatalhaDetalhesResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Batalha não encontrada",
            content = @Content) })
    @GetMapping("/{uuid}")
    public ResponseEntity<BatalhaDetalhesResponse> buscarBatalhaPorId(@PathVariable UUID uuid) {
        return ResponseEntity.ok(batalhaService.buscarBatalhaPorUuid(uuid));
    }

    @Operation(summary = "Verifica se houve vencedor e encerra batalha ou abre novo turno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "batalha atualizada com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = BatalhaResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Batalha não encontrada",
            content = @Content),
        @ApiResponse(responseCode = "400",
            description = "Ação de Ataque/Defesa/Dano não computado, realize a ação primeiro",
            content = @Content) })
    @PatchMapping("/atualizar/{uuid}")
    public ResponseEntity<BatalhaResponse> atualizarTurnoBatalha(@PathVariable UUID uuid) {
        return ResponseEntity.ok(batalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Operation(summary = "Deleta uma batalha por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "batalha deletada com sucesso",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Batalha não encontrada",
            content = @Content) })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletarBatalha(@PathVariable UUID uuid) {
        batalhaService.deletarBatalha(uuid);
        return ResponseEntity.ok().build();
    }
}
