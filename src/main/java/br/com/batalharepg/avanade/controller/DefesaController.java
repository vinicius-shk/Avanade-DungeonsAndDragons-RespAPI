package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.service.CombateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/defesa")
@RequiredArgsConstructor
public class DefesaController {
    private final CombateService combateService;

    @PatchMapping("/defender/{uuid}")
    public ResponseEntity<BatalhaDetalhesResponse> calcularValorTotalDefesaPersonagens(@PathVariable UUID uuid) {
        return ResponseEntity.ok(combateService.calcularValorTotalAcaoPersonagens(uuid, TipoAcao.DEFESA));
    }
}
