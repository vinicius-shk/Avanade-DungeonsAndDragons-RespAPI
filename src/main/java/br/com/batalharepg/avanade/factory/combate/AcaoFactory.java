package br.com.batalharepg.avanade.factory.combate;

import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;

public abstract class AcaoFactory {

    public abstract DadosTurno executarAcao(Personagem atacante, Personagem defensor, DadosTurno dadosTurno);
    public abstract TipoAcao getTipoAcao();
    protected abstract boolean verificarSeAcaoDeveOcorrer(DadosTurno dadosTurno);
    protected abstract Integer calcularValorTotalAcao(Personagem personagem);
}
