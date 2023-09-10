package br.com.batalharepg.avanade.dto.response;

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
