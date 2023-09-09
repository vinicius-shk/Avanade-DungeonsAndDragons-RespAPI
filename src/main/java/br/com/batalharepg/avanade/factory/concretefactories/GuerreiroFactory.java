package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class GuerreiroFactory extends PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        Personagem personagem = new Personagem(nome, TipoPersonagem.GUERREIRO);
        return completaCriacaoPersonagem(personagem);
    }

    @Override
    public Integer getVida() {
        return 20;
    }

    @Override
    public Integer getForca() {
        return 7;
    }

    @Override
    public Integer getDefesa() {
        return 5;
    }

    @Override
    public Integer getAgilidade() {
        return 6;
    }

    @Override
    public Integer getQuantidadeDadosAtaque() {
        return 1;
    }

    @Override
    public Integer getQuantidadeFacesDadosAtaque() {
        return 12;
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.GUERREIRO;
    }

}
