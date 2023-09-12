package br.com.batalharepg.avanade.dto.response;

import java.util.UUID;

public record BatalhaResumoResponse(UUID uuid,
                                    Integer numeroTurnoAtual,
                                    String nomeAtacante,
                                    String nomeDefensor,
                                    String nomeVencedor,
                                    Boolean batalhaFinalizada) {
}
