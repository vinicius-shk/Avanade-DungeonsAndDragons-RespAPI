package br.com.batalharepg.avanade.factory.combate.implementacoes;

import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.util.RolagemDados;
import org.springframework.stereotype.Component;

@Component
public class AtaqueFactory extends AcaoFactory {

    @Override
    public DadosTurno executarAcao(Personagem atacante, Personagem defensor, DadosTurno dadosTurno) {
        if (verificarSeAcaoDeveOcorrer(dadosTurno)) {
            dadosTurno.setValorDoAtaqueAtacante(calcularValorTotalAcao(atacante));
            dadosTurno.setValorDoAtaqueDefensor(calcularValorTotalAcao(defensor));
            return dadosTurno;
        }
        throw new EventoJaRealizadoException("Ataque já realizado");
    }

    @Override
    public TipoAcao getTipoAcao() {
        return TipoAcao.ATAQUE;
    }

    @Override
    protected boolean verificarSeAcaoDeveOcorrer(DadosTurno dadosTurno) {
        return dadosTurno.getValorDoAtaqueAtacante() == null && dadosTurno.getValorDoAtaqueDefensor() == null;
    }

    @Override
    protected Integer calcularValorTotalAcao(Personagem personagem) {
        Integer valorDadoDeAtaque = RolagemDados.rolarD12();
        return valorDadoDeAtaque + personagem.getForca() + personagem.getAgilidade();
    }
}
