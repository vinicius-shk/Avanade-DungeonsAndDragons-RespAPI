package br.com.batalharepg.avanade.dto.response;

import java.util.UUID;

public record BatalhaResponse(UUID uuid,
                              String nomeAtacante,
                              String nomeDefensor,
                              Boolean atacanteVenceuIniciativa,
                              Integer numeroTurnoAtual,
                              Boolean batalhaFinalizada,
                              String nomeVencedor) {
}
