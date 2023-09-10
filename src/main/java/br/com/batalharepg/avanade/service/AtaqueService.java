package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.AtaqueJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.DadosTurnoRepository;
import br.com.batalharepg.avanade.util.RolagemDados;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtaqueService {
    private final DadosTurnoRepository turnoRepository;
    private final BatalhaRepository batalhaRepository;

    @Transactional
    public BatalhaDetalhesResponse calcularValorTotalAtaquePersonagens(UUID batalhaUuid) {
        Batalha batalha = batalhaRepository.findById(batalhaUuid)
            .orElseThrow(() -> new NotFoundException("Batalha não encontrada"));
        DadosTurno dadosTurnoAtual = batalha.getDadosTurnosList().stream()
            .filter(turno -> turno.getNumeroTurno().equals(batalha.getNumeroTurnoAtual()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Turno não encontrado"));
        if (verificarSeAtaqueDeveRealizado(dadosTurnoAtual)) {
            dadosTurnoAtual.setValorDoAtaqueAtacante(calcularValorTotalAtaque(batalha.getAtacante()));
            dadosTurnoAtual.setValorDoAtaqueDefensor(calcularValorTotalAtaque(batalha.getDefensor()));
            turnoRepository.save(dadosTurnoAtual);
            return batalha.getDetalhesResponseDto();
        }
        throw new AtaqueJaRealizadoException("Ataque já realizado");
    }

    private Integer calcularValorTotalAtaque(Personagem personagem) {
        Integer valorDadoDeAtaque = RolagemDados.rolarDadoAtaque();
        return valorDadoDeAtaque + personagem.getForca() + personagem.getAgilidade();
    }

    private boolean verificarSeAtaqueDeveRealizado(DadosTurno dadosTurno) {
        final var VALOR_DEFAULT_ATAQUE = 0;
        return dadosTurno.getValorDoAtaqueAtacante().equals(VALOR_DEFAULT_ATAQUE)
            && dadosTurno.getValorDoAtaqueDefensor().equals(VALOR_DEFAULT_ATAQUE);
    }
}
