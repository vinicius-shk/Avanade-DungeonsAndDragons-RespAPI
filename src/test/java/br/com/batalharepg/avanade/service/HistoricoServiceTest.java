package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResumoResponse;
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
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class HistoricoServiceTest {
    @Mock
    private BatalhaRepository batalhaRepository;
    @InjectMocks
    private HistoricoService historicoService;

    @Test
    void deveriaBuscarHistoricoDetalhadoDeBatalhaComSucesso() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Atacante");
        Personagem defensor = new OrcFactory().criarPersonagem("Defensor");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any(UUID.class)))
            .thenReturn(batalha);

        BatalhaDetalhesResponse batalhaDetalhesResponse = historicoService.buscarHistoricoDetalhadoDeBatalha(UUID.randomUUID());

        Assertions.assertNotNull(batalhaDetalhesResponse);
        Assertions.assertEquals(batalha.getAtacante().getNome(), batalhaDetalhesResponse.nomeJogadorAtacante());
        Assertions.assertEquals(batalha.getDefensor().getNome(), batalhaDetalhesResponse.nomeMonstroDefensor());
        Assertions.assertEquals(batalha.getAtacanteVenceuIniciativa(), batalhaDetalhesResponse.jogadorAtacanteVenceuIniciativa());
    }

    @Test
    void deveriaLancarExcecaoAoBuscarHistoricoDetalhadoDeBatalhaComIdInexistente() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(batalhaRepository.findById(ArgumentMatchers.any(UUID.class)))
            .thenReturn(Optional.empty());

        // Caso default para o método findByIdWithExceptionIfNotFound do JpaRepository
        Assertions.assertThrows(NullPointerException.class, () -> historicoService.buscarHistoricoDetalhadoDeBatalha(uuid));
    }

    @Test
    void deveriaBuscarHistoricoResumidoComSucesso() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Atacante");
        Personagem defensor = new OrcFactory().criarPersonagem("Defensor");
        Batalha batalha = new Batalha(atacante, defensor, true);
        batalha.setBatalhaFinalizada(true);
        batalha.setNomeVencedor("Atacante");

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any(UUID.class)))
            .thenReturn(batalha);

        BatalhaResumoResponse batalhaDetalhesResponse = historicoService.buscarHistoricoResumidoDeBatalha(UUID.randomUUID());

        Assertions.assertNotNull(batalhaDetalhesResponse);
        Assertions.assertEquals(batalha.getAtacante().getNome(), batalhaDetalhesResponse.nomeAtacante());
        Assertions.assertEquals(batalha.getDefensor().getNome(), batalhaDetalhesResponse.nomeDefensor());
        Assertions.assertEquals(batalha.getNomeVencedor(), batalhaDetalhesResponse.nomeVencedor());
        Assertions.assertTrue(batalhaDetalhesResponse.batalhaFinalizada());
    }
}
