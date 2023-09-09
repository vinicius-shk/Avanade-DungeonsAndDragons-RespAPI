package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.factory.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;

public class BarbaroFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome, TipoPersonagem tipoPersonagem) {
        if (tipoPersonagem != TipoPersonagem.BARBARO) {
            throw new IllegalArgumentException("Tipo de personagem inválido");
        }
        return new Personagem(nome,
            tipoPersonagem,
            21,
            10,
            2,
            5,
            2,
            8);
    }
}
