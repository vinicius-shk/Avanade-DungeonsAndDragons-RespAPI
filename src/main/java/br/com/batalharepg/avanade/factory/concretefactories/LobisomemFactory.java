package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class LobisomemFactory extends PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        Personagem personagem = new Personagem(nome, TipoPersonagem.LOBISOMEM);
        return completaCriacaoPersonagem(personagem);
    }

    @Override
    public Integer getVida() {
        return 34;
    }

    @Override
    public Integer getForca() {
        return 7;
    }

    @Override
    public Integer getDefesa() {
        return 4;
    }

    @Override
    public Integer getAgilidade() {
        return 7;
    }

    @Override
    public Integer getQuantidadeDadosAtaque() {
        return 2;
    }

    @Override
    public Integer getQuantidadeFacesDadosAtaque() {
        return 4;
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.LOBISOMEM;
    }
}
