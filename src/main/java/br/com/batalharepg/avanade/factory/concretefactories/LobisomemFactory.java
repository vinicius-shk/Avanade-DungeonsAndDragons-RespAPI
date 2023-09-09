package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class LobisomemFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        return new Personagem(nome,
            TipoPersonagem.LOBISOMEM,
            34,
            7,
            4,
            7,
            2,
            4);
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.LOBISOMEM;
    }
}
