package br.com.batalharepg.avanade.dto.response;

import java.util.UUID;

public record PersonagemResponse(UUID uuid,
                                 String nome,
                                 String tipoPersonagem,
                                 Integer vida,
                                 Integer forca,
                                 Integer defesa,
                                 Integer agilidade,
                                 Integer quantidadeDadosAtaque,
                                 Integer quantidadeFacesDadosAtaque) {
}
