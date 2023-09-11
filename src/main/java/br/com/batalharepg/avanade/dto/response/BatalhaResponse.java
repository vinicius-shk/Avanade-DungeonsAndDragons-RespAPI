package br.com.batalharepg.avanade.dto.response;

import java.util.UUID;

public record BatalhaResponse(UUID uuid,
                              String nomeJogadorAtacante,
                              String nomeMonstroDefensor,
                              Boolean jogadorAtacanteVenceuIniciativa,
                              Integer numeroTurnoAtual,
                              Boolean batalhaFinalizada,
                              String nomeVencedor) {
}
