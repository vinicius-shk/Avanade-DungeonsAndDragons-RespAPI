package br.com.batalharepg.avanade.factory.combate.implementacoes;

import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.util.RolagemDados;
import org.springframework.stereotype.Component;

@Component
public class DanoFactory extends AcaoFactory {

    @Override
    public DadosTurno executarAcao(Personagem atacante, Personagem defensor, DadosTurno dadosTurno) {
        final var VALOR_ATAQUE_DEFENDIDO = 0;
        if (verificarSeAcaoDeveOcorrer(dadosTurno)) {
            if (dadosTurno.getValorDoAtaqueAtacante() > dadosTurno.getValorDaDefesaDefensor()) {
                dadosTurno.setValorDoDanoAtacante(calcularValorTotalAcao(atacante));
            } else { dadosTurno.setValorDoDanoAtacante(VALOR_ATAQUE_DEFENDIDO); }
            if (dadosTurno.getValorDoAtaqueDefensor() > dadosTurno.getValorDaDefesaAtacante()) {
                dadosTurno.setValorDoDanoDefensor(calcularValorTotalAcao(defensor));
            } else { dadosTurno.setValorDoDanoDefensor(VALOR_ATAQUE_DEFENDIDO); }

            return dadosTurno;
        }
        throw new EventoJaRealizadoException("Dano já computado");
    }

    @Override
    public TipoAcao getTipoAcao() {
        return TipoAcao.DANO;
    }

    @Override
    protected boolean verificarSeAcaoDeveOcorrer(DadosTurno dadosTurno) {
        final var VALOR_MINIMO_DANO = 0;
        return dadosTurno.getValorDoDanoAtacante() < VALOR_MINIMO_DANO
            && dadosTurno.getValorDoDanoDefensor() < VALOR_MINIMO_DANO;
    }

    @Override
    protected Integer calcularValorTotalAcao(Personagem personagem) {
        Integer valorDano = RolagemDados
            .rolarDados(personagem.getQuantidadeDadosAtaque(), personagem.getQuantidadeFacesDadosAtaque());
        return valorDano + personagem.getForca();
    }
}
