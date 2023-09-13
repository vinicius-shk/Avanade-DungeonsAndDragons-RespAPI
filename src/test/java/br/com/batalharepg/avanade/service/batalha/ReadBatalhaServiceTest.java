package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.CavaleiroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReadBatalhaServiceTest {
    @Mock
    private BatalhaRepository batalhaRepository;
    @InjectMocks
    private ReadBatalhaService readBatalhaService;

    @Test
    void deveriaListarTodasBatalhas() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");

        Mockito.when(batalhaRepository.findAll()).thenReturn(List.of(new Batalha(atacante, defensor, true),
            new Batalha(atacante, defensor, true)));
        List<BatalhaResponse> batalhaResponse = readBatalhaService.buscarTodasBatalhas();

        Mockito.verify(batalhaRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(2, batalhaResponse.size());
    }

    @Test
    void deveriaBuscarBatalhaPorIdComSucesso() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno turnoInicial = new DadosTurno(batalha);
        batalha.setDadosTurnosList(List.of(turnoInicial));
        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        BatalhaDetalhesResponse batalhaResponse = readBatalhaService.buscarBatalhaPorUuid(ArgumentMatchers.any());

        Mockito.verify(batalhaRepository, Mockito.times(1)).findByIdWithExceptionIfNotFound(ArgumentMatchers.any());
        Assertions.assertEquals("Jogador 1", batalhaResponse.nomeJogadorAtacante());
        Assertions.assertEquals("Monstro 1", batalhaResponse.nomeMonstroDefensor());
        Assertions.assertTrue(batalhaResponse.jogadorAtacanteVenceuIniciativa());
        Assertions.assertEquals(1, batalhaResponse.numeroTurnoAtual());
        Assertions.assertFalse(batalhaResponse.batalhaFinalizada());
    }
}
