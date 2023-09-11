package br.com.batalharepg.avanade.dto.request;

import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;

public record PersonagemRequest(String nome,
                                TipoClassePersonagem tipoClassePersonagem) {
}
