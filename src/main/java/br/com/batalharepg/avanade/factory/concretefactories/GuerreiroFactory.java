package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class GuerreiroFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        return new Personagem(nome,
            TipoPersonagem.GUERREIRO,
            20,
            7,
            5,
            6,
            1,
            12);
    }
    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.GUERREIRO;
    }

}
