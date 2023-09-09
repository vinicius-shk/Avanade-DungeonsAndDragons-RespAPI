package br.com.batalharepg.avanade.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personagem {
    private String nome;
    private TipoPersonagem tipoPersonagem;
    private Integer vida;
    private Integer forca;
    private Integer defesa;
    private Integer agilidade;
    private Integer quantidadeDadosAtaque;
    private Integer quantidadeFacesDadosAtaque;
}
