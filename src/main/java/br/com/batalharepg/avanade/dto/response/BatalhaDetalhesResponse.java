package br.com.batalharepg.avanade.dto.response;

import java.util.List;
import java.util.UUID;

public record BatalhaDetalhesResponse(UUID uuid,
                                      String nomeJogadorAtacante,
                                      String nomeMonstroDefensor,
                                      Boolean jogadorAtacanteVenceuIniciativa,
                                      Integer numeroTurnoAtual,
                                      Boolean batalhaFinalizada,
                                      String nomeVencedor,
                                      List<DadosTurnoResponse> dadosTurnosList) {
}
