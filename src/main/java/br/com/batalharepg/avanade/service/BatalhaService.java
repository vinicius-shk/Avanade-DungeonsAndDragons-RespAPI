package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.request.FinalizarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import br.com.batalharepg.avanade.util.RolagemDados;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BatalhaService {
    private final BatalhaRepository batalhaRepository;
    private final PersonagemRepository personagemRepository;
    private final TurnoService turnoService;
    public static final String BATALHA_NAO_ENCONTRADA = "Batalha não encontrada";

    public BatalhaResponse criarBatalha(CriarBatalhaRequest batalhaRequest) {
        Personagem atacante = personagemRepository.findByNome(batalhaRequest.nomeAtacante())
            .orElseThrow(() -> new NotFoundException("Atacante não encontrado"));
        Personagem defensor = personagemRepository.findByNome(batalhaRequest.nomeDefensor())
            .orElseThrow(() -> new NotFoundException("Defensor não encontrado"));
        Batalha batalha = new Batalha(atacante, defensor, atacanteVenceuIniciativa());
        Batalha batalhaSalva = batalhaRepository.save(batalha);
        turnoService.criarTurnoInicial(batalhaSalva);
        return batalhaSalva .getResponseDto();
    }

    public List<BatalhaResponse> buscarBatalha() {
        return batalhaRepository.findAll().stream().map(Batalha::getResponseDto).toList();
    }

    @Transactional
    public BatalhaDetalhesResponse buscarBatalhaPorUuid(UUID uuid) {
        return batalhaRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException(BATALHA_NAO_ENCONTRADA))
            .getDetalhesResponseDto();
    }

    public void deletarBatalha(UUID uuid) {
        batalhaRepository.deleteByIdWithExceptionIfNotFound(uuid);
    }

    @Transactional
    public BatalhaResponse verificarSeBatalhaAcabou(UUID uuid) {
        Batalha batalha = batalhaRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException(BATALHA_NAO_ENCONTRADA));
        DadosTurno dadosTurnoAtual = obterDadosTurnoPorId(batalha);
        batalha = verificarSeHouveVencedor(batalha, dadosTurnoAtual);
        if (Boolean.TRUE.equals(batalha.getBatalhaFinalizada())) {
            return batalha.getResponseDto();
        }
        batalha.setNumeroTurnoAtual(batalha.getNumeroTurnoAtual() + 1);
        batalhaRepository.save(batalha);
        turnoService.criarTurnoAdicional(batalha, dadosTurnoAtual);
        return batalha.getResponseDto();
    }

    public BatalhaResponse finalizarBatalha(UUID uuid, FinalizarBatalhaRequest finalizarBatalhaRequest) {
        Batalha batalha = batalhaRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException(BATALHA_NAO_ENCONTRADA));
        batalha.setBatalhaFinalizada(true);
        batalha.setNomeVencedor(finalizarBatalhaRequest.nomeVencedor());
        return batalhaRepository.save(batalha).getResponseDto();
    }

    private Boolean atacanteVenceuIniciativa() {
        Integer iniciativaAtacante = RolagemDados.rolarD20();
        Integer iniciativaDefensor = RolagemDados.rolarD20();
        while (iniciativaAtacante.equals(iniciativaDefensor)) {
            iniciativaAtacante = RolagemDados.rolarD20();
            iniciativaDefensor = RolagemDados.rolarD20();
        }
        return iniciativaAtacante > iniciativaDefensor;
    }

    private DadosTurno obterDadosTurnoPorId(Batalha batalha) {
        return batalha.getDadosTurnosList().stream()
            .filter(turno -> turno.getNumeroTurno().equals(batalha.getNumeroTurnoAtual()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Turno não encontrado"));
    }
    private Batalha verificarSeHouveVencedor(Batalha batalha, DadosTurno dadosTurno) {
        if (dadosTurno.getVidaAtualAtacante() <= 0 && dadosTurno.getVidaAtualDefensor() <= 0) {
            batalha.setBatalhaFinalizada(true);
            batalha.setNomeVencedor(Boolean.TRUE.equals(batalha.getAtacanteVenceuIniciativa()) ?
                batalha.getAtacante().getNome() :
                batalha.getDefensor().getNome());
            return batalhaRepository.save(batalha);
        } else if (dadosTurno.getVidaAtualAtacante() <= 0 || dadosTurno.getVidaAtualDefensor() <= 0) {
            batalha.setBatalhaFinalizada(true);
            batalha.setNomeVencedor(dadosTurno.getVidaAtualAtacante() <= 0 ? batalha.getDefensor().getNome() :
                batalha.getAtacante().getNome());
            return batalhaRepository.save(batalha);
        }
        return batalha;
    }
}
