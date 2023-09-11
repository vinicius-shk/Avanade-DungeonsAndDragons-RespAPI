package br.com.batalharepg.avanade.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosTurnoResponse(Long id,
                                 Integer numeroTurno,
                                 Integer valorDoAtaqueAtacante,
                                 Integer valorDoAtaqueDefensor,
                                 Integer valorDaDefesaAtacante,
                                 Integer valorDaDefesaDefensor,
                                 Integer valorDoDanoAtacante,
                                 Integer valorDoDanoDefensor,
                                 Integer vidaAtualAtacante,
                                 Integer vidaAtualDefensor) {
}
