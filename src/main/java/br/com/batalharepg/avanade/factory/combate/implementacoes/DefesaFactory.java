package br.com.batalharepg.avanade.factory.combate.implementacoes;

import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.util.RolagemDados;
import org.springframework.stereotype.Component;

@Component
public class DefesaFactory extends AcaoFactory {

    @Override
    public DadosTurno executarAcao(Personagem atacante, Personagem defensor, DadosTurno dadosTurno) {
        if (verificarSeAcaoDeveOcorrer(dadosTurno)) {
            dadosTurno.setValorDaDefesaAtacante(calcularValorTotalAcao(atacante));
            dadosTurno.setValorDaDefesaDefensor(calcularValorTotalAcao(defensor));
            return dadosTurno;
        }
        throw new EventoJaRealizadoException("Defesa já realizada");
    }

    @Override
    public TipoAcao getTipoAcao() {
        return TipoAcao.DEFESA;
    }

    @Override
    protected boolean verificarSeAcaoDeveOcorrer(DadosTurno dadosTurno) {
        return dadosTurno.getValorDaDefesaAtacante() == null && dadosTurno.getValorDaDefesaDefensor() == null;
    }

    @Override
    protected Integer calcularValorTotalAcao(Personagem personagem) {
        Integer valorDadoDeDefesa = RolagemDados.rolarD12();
        return valorDadoDeDefesa + personagem.getDefesa() + personagem.getAgilidade();
    }
}
