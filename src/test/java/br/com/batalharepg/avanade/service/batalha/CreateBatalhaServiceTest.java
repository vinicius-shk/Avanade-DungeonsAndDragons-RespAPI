package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.TipoPersonagemDefensorIncorretoException;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.CavaleiroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import br.com.batalharepg.avanade.service.TurnoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateBatalhaServiceTest {
    @Mock
    private BatalhaRepository batalhaRepository;
    @Mock
    private PersonagemRepository personagemRepository;
    @Mock
    private TurnoService turnoService;
    @InjectMocks
    private CreateBatalhaService createBatalhaService;

    @BeforeEach
    void setUp() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Personagem defensorDefault = new OrcFactory().criarPersonagem("Monstro default");

        Mockito.when(personagemRepository.findByNome(ArgumentMatchers.anyString()))
            .thenReturn(java.util.Optional.of(atacante))
            .thenReturn(java.util.Optional.of(defensor));
        Mockito.when(personagemRepository.sorteiaMonstroDefaultParaCombate()).thenReturn(defensorDefault);
        Mockito.when(batalhaRepository.save(Mockito.any(Batalha.class))).thenReturn(new Batalha(atacante, defensor, true));
        Mockito.doNothing().when(turnoService).criarTurnoInicial(Mockito.any(Batalha.class));
    }

    @Test
    void deveriaCriarBatalhaComSucesso() {
        CriarBatalhaRequest criarBatalhaRequest = new CriarBatalhaRequest("Jogador 1", "Monstro 1");

        BatalhaResponse batalha = createBatalhaService.criarBatalha(criarBatalhaRequest);

        Assertions.assertNotNull(batalha);
        Assertions.assertEquals("Jogador 1", batalha.nomeJogadorAtacante());
        Assertions.assertEquals("Monstro 1", batalha.nomeMonstroDefensor());
        Assertions.assertTrue(batalha.jogadorAtacanteVenceuIniciativa());
        Assertions.assertEquals(1, batalha.numeroTurnoAtual());
        Assertions.assertFalse(batalha.batalhaFinalizada());
    }

    @Test
    void deveriaCriarBatalhaComSucessoSemDefensor() {
        CriarBatalhaRequest criarBatalhaRequest = new CriarBatalhaRequest("Jogador 1", null);
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensorDefault = new OrcFactory().criarPersonagem("Monstro default");

        Mockito.when(personagemRepository.findByNome(ArgumentMatchers.anyString()))
            .thenReturn(java.util.Optional.of(atacante));
        Mockito.when(personagemRepository.sorteiaMonstroDefaultParaCombate()).thenReturn(defensorDefault);
        Mockito.when(batalhaRepository.save(Mockito.any(Batalha.class))).thenReturn(new Batalha(atacante, defensorDefault, true));
        Mockito.doNothing().when(turnoService).criarTurnoInicial(Mockito.any(Batalha.class));

        BatalhaResponse batalha = createBatalhaService.criarBatalha(criarBatalhaRequest);

        Assertions.assertNotNull(batalha);
        Assertions.assertEquals("Jogador 1", batalha.nomeJogadorAtacante());
        Assertions.assertEquals("Monstro default", batalha.nomeMonstroDefensor());
        Assertions.assertTrue(batalha.jogadorAtacanteVenceuIniciativa());
        Assertions.assertEquals(1, batalha.numeroTurnoAtual());
        Assertions.assertFalse(batalha.batalhaFinalizada());
    }

    @Test
    void deveriaLancarTipoPersonagemDefensorIncorretoExceptionCasoDefensorNaoSejaTipoMonstro() {
        CriarBatalhaRequest criarBatalhaRequest = new CriarBatalhaRequest("Jogador 1", "Jogador 2");
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensorInvalido = new CavaleiroFactory().criarPersonagem("Jogador 2");

        Mockito.when(personagemRepository.findByNome(ArgumentMatchers.anyString()))
            .thenReturn(java.util.Optional.of(atacante))
            .thenReturn(java.util.Optional.of(defensorInvalido));

        Assertions.assertThrows(TipoPersonagemDefensorIncorretoException.class,
            () -> createBatalhaService.criarBatalha(criarBatalhaRequest));
    }
}
