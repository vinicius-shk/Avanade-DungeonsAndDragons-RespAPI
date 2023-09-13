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
}
