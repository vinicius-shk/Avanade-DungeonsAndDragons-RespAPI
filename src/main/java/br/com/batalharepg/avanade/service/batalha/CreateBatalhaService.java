package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.exceptions.TipoPersonagemDefensorIncorretoException;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import br.com.batalharepg.avanade.service.TurnoService;
import br.com.batalharepg.avanade.util.RolagemDados;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBatalhaService {
    private final BatalhaRepository batalhaRepository;
    private final PersonagemRepository personagemRepository;
    private final TurnoService turnoService;

    public BatalhaResponse criarBatalha(CriarBatalhaRequest batalhaRequest) {
        Personagem defensor;
        Personagem atacante = personagemRepository.findByNome(batalhaRequest.nomeJogadorAtacante())
            .orElseThrow(() -> new NotFoundException("Atacante não encontrado"));
        if (batalhaRequest.nomeMonstroDefensor() == null) {
            defensor = personagemRepository.sorteiaMonstroDefaultParaCombate();
        } else {
            defensor = personagemRepository.findByNome(batalhaRequest.nomeMonstroDefensor())
                .orElseThrow(() -> new NotFoundException("Defensor não encontrado"));
        }
        if (!defensor.getTipoPersonagem().equals(TipoPersonagem.MONSTRO)) {
            throw new TipoPersonagemDefensorIncorretoException("Defensor não é um monstro");
        }
        Batalha batalha = new Batalha(atacante, defensor, atacanteVenceuIniciativa());
        Batalha batalhaSalva = batalhaRepository.save(batalha);
        turnoService.criarTurnoInicial(batalhaSalva);
        return batalhaSalva .getResponseDto();
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
}
