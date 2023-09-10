package br.com.batalharepg.avanade.dto.request;

import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;

public record PersonagemRequest(String nome,
                                TipoPersonagem tipoPersonagem) {
}
