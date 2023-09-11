package br.com.batalharepg.avanade.dto.response;

import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;

import java.util.UUID;

public record PersonagemResponse(UUID uuid,
                                 String nome,
                                 TipoPersonagem tipoPersonagem,
                                 TipoClassePersonagem tipoClassePersonagem,
                                 Integer vida,
                                 Integer forca,
                                 Integer defesa,
                                 Integer agilidade,
                                 Integer quantidadeDadosAtaque,
                                 Integer quantidadeFacesDadosAtaque) {
}
