package br.com.batalharepg.avanade.dto.response;

import java.util.List;
import java.util.UUID;

public record BatalhaDetalhesResponse(UUID uuid,
                                      String nomeAtacante,
                                      String nomeDefensor,
                                      Boolean atacanteVenceuIniciativa,
                                      Integer numeroTurnoAtual,
                                      Boolean batalhaFinalizada,
                                      String nomeVencedor,
                                      List<DadosTurnoResponse> dadosTurnosList) {
}
