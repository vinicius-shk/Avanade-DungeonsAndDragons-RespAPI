package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.request.FinalizarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.service.BatalhaService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<BatalhaResponse> criarBatalha(@RequestBody CriarBatalhaRequest criarBatalhaRequest) {
        return ResponseEntity.ok(batalhaService.criarBatalha(criarBatalhaRequest));
    }

    @GetMapping
    public ResponseEntity<List<BatalhaResponse>> buscarBatalha() {
        return ResponseEntity.ok(batalhaService.buscarBatalha());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BatalhaDetalhesResponse> buscarBatalhaPorId(@PathVariable UUID uuid) {
        return ResponseEntity.ok(batalhaService.buscarBatalhaPorUuid(uuid));
    }

    @PatchMapping("/atualizar/{uuid}")
    public ResponseEntity<BatalhaResponse> atualizarTurnoBatalha(@PathVariable UUID uuid) {
        return ResponseEntity.ok(batalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @PatchMapping("/finalizar/{uuid}")
    public ResponseEntity<BatalhaResponse> finalizarBatalha(@PathVariable UUID uuid,
                                                            @RequestBody FinalizarBatalhaRequest finalizarBatalhaRequest) {
        return ResponseEntity.ok(batalhaService.finalizarBatalha(uuid, finalizarBatalhaRequest));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletarBatalha(@PathVariable UUID uuid) {
        batalhaService.deletarBatalha(uuid);
        return ResponseEntity.ok().build();
    }
}
