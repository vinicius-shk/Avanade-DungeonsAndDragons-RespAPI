package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.request.FinalizarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
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

    public BatalhaResponse atualizarTurnoBatalha(UUID uuid) {
        Batalha batalha = batalhaRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException(BATALHA_NAO_ENCONTRADA));
        batalha.setNumeroTurnoAtual(batalha.getNumeroTurnoAtual() + 1);
        return batalhaRepository.save(batalha).getResponseDto();
    }

    public BatalhaResponse finalizarBatalha(UUID uuid, FinalizarBatalhaRequest finalizarBatalhaRequest) {
        Batalha batalha = batalhaRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException(BATALHA_NAO_ENCONTRADA));
        batalha.setBatalhaFinalizada(true);
        batalha.setNomeVencedor(finalizarBatalhaRequest.nomeVencedor());
        return batalhaRepository.save(batalha).getResponseDto();
    }

    private Boolean atacanteVenceuIniciativa() {
        Integer iniciativaAtacante = RolagemDados.rolarDadoIniciativa();
        Integer iniciativaDefensor = RolagemDados.rolarDadoIniciativa();
        while (iniciativaAtacante.equals(iniciativaDefensor)) {
            iniciativaAtacante = RolagemDados.rolarDadoIniciativa();
            iniciativaDefensor = RolagemDados.rolarDadoIniciativa();
        }
        return iniciativaAtacante > iniciativaDefensor;
    }
}
