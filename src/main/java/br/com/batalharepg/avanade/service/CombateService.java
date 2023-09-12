package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.configuration.AcaoFactoryConfiguration;
import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.DadosTurnoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CombateService {
    private final DadosTurnoRepository turnoRepository;
    private final BatalhaRepository batalhaRepository;
    private final AcaoFactoryConfiguration factoryConfiguration;

    @Transactional
    public DadosTurnoResponse calcularValorTotalAcaoPersonagens(UUID batalhaUuid, TipoAcao tipoAcao) {
        Batalha batalha = obterBatalhaPorid(batalhaUuid);
        if (Boolean.TRUE.equals(batalha.getBatalhaFinalizada())) {
            throw new EventoJaRealizadoException("Batalha já finalizada");
        }
        DadosTurno dadosTurnoAtual = obterDadosTurnoPorId(batalha);
        AcaoFactory factory = factoryConfiguration.getFactory(tipoAcao);
        if (factory != null) {
            DadosTurno dadosTurnoModificado = factory
                .executarAcao(batalha.getAtacante(), batalha.getDefensor(), dadosTurnoAtual);
            turnoRepository.save(dadosTurnoModificado);
            return dadosTurnoModificado.getDadosTurnoDto();
        }
        throw new NotFoundException("Tipo de ação não encontrado");
    }

    private Batalha obterBatalhaPorid(UUID uuid) {
        return batalhaRepository.findByIdWithExceptionIfNotFound(uuid);
    }

    private DadosTurno obterDadosTurnoPorId(Batalha batalha) {
        return batalha.getDadosTurnosList().stream()
            .filter(turno -> turno.getNumeroTurno().equals(batalha.getNumeroTurnoAtual()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Turno não encontrado"));
    }
}
