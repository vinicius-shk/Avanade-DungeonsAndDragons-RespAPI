package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class OrcFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        return new Personagem(nome,
            TipoPersonagem.ORC,
            42,
            7,
            1,
            2,
            3,
            4);
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.ORC;
    }
}
