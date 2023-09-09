package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class GiganteFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        return new Personagem(nome,
            TipoPersonagem.GIGANTE,
            34,
            10,
            4,
            4,
            2,
            6);
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.GIGANTE;
    }
}
