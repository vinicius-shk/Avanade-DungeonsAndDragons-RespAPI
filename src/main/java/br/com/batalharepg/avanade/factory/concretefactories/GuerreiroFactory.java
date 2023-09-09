package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.factory.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;

public class GuerreiroFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome, TipoPersonagem tipoPersonagem) {
        if (tipoPersonagem != TipoPersonagem.GUERREIRO) {
            throw new IllegalArgumentException("Tipo de personagem inválido");
        }
        return new Personagem(nome,
            tipoPersonagem,
            20,
            7,
            5,
            6,
            1,
            12);
    }
}
