package br.com.batalharepg.avanade.factory.personagem.implementacoes;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class BarbaroFactory extends PersonagemFactory {

    @Override
    public Personagem criarPersonagem(String nome) {

        Personagem personagem = new Personagem(nome, TipoPersonagem.BARBARO);
        return completaCriacaoPersonagem(personagem);
    }

    @Override
    public Integer getVida() {
        return 21;
    }

    @Override
    public Integer getForca() {
        return 10;
    }

    @Override
    public Integer getDefesa() {
        return 2;
    }

    @Override
    public Integer getAgilidade() {
        return 5;
    }

    @Override
    public Integer getQuantidadeDadosAtaque() {
        return 2;
    }

    @Override
    public Integer getQuantidadeFacesDadosAtaque() {
        return 8;
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.BARBARO;
    }
}
