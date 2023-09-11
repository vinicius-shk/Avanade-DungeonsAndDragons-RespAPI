package br.com.batalharepg.avanade.factory.personagem.implementacoes;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class GiganteFactory extends PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        Personagem personagem = new Personagem(nome, this.getTipoPersonagem(), this.getTipoClassePersonagem());
        return completaCriacaoPersonagem(personagem);
    }

    @Override
    public Integer getVida() {
        return 34;
    }

    @Override
    public Integer getForca() {
        return 10;
    }

    @Override
    public Integer getDefesa() {
        return 4;
    }

    @Override
    public Integer getAgilidade() {
        return 4;
    }

    @Override
    public Integer getQuantidadeDadosAtaque() {
        return 2;
    }

    @Override
    public Integer getQuantidadeFacesDadosAtaque() {
        return 6;
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.MONSTRO;
    }

    @Override
    public TipoClassePersonagem getTipoClassePersonagem() { return TipoClassePersonagem.GIGANTE; }
}
