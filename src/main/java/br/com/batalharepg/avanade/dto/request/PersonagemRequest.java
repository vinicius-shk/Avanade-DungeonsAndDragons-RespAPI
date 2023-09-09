package br.com.batalharepg.avanade.dto.request;

public record PersonagemRequest(String nome,
                                String tipoPersonagem,
                                Integer vida,
                                Integer forca,
                                Integer defesa,
                                Integer agilidade,
                                Integer quantidadeDadosAtaque,
                                Integer quantidadeFacesDadosAtaque) {
}
