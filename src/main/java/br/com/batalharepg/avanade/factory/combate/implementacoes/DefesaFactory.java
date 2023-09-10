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
        final var VALOR_DEFAULT_DEFESA = 0;
        return dadosTurno.getValorDaDefesaAtacante().equals(VALOR_DEFAULT_DEFESA)
            && dadosTurno.getValorDaDefesaDefensor().equals(VALOR_DEFAULT_DEFESA);
    }

    @Override
    protected Integer calcularValorTotalAcao(Personagem personagem) {
        Integer valorDadoDeDefesa = RolagemDados.rolarDadoAtaque();
        return valorDadoDeDefesa + personagem.getDefesa() + personagem.getAgilidade();
    }
}
