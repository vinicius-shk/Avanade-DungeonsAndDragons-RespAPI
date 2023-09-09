package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.factory.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;

public class LobisomenFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome, TipoPersonagem tipoPersonagem) {
        if (tipoPersonagem != TipoPersonagem.LOBISOMEN) {
            throw new IllegalArgumentException("Tipo de personagem inválido");
        }
        return new Personagem(nome,
            tipoPersonagem,
            34,
            7,
            4,
            7,
            2,
            4);
    }
}
