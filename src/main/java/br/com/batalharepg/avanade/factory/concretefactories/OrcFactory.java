package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.factory.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;

public class OrcFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome, TipoPersonagem tipoPersonagem) {
        if (tipoPersonagem != TipoPersonagem.ORC) {
            throw new IllegalArgumentException("Tipo de personagem inválido");
        }
        return new Personagem(nome,
            tipoPersonagem,
            42,
            7,
            1,
            2,
            3,
            4);
    }
}
