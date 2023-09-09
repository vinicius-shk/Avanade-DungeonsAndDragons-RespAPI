package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.factory.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;

public class CavaleiroFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome, TipoPersonagem tipoPersonagem) {
        if (tipoPersonagem != TipoPersonagem.CAVALEIRO) {
            throw new IllegalArgumentException("Tipo de personagem inválido");
        }
        return new Personagem(nome,
            tipoPersonagem,
            26,
            6,
            8,
            3,
            2,
            6);
    }
}
