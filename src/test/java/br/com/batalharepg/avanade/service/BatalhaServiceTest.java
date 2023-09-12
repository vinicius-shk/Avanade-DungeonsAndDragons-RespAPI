package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.request.CriarBatalhaRequest;
import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.TipoPersonagemDefensorIncorretoException;
import br.com.batalharepg.avanade.exceptions.TurnoNaoFinalizadoException;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.CavaleiroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class BatalhaServiceTest {
    @Mock
    private BatalhaRepository batalhaRepository;
    @Mock
    private PersonagemRepository personagemRepository;
    @Mock
    private TurnoService turnoService;
    @InjectMocks
    private BatalhaService batalhaService;

    private static Batalha criaBatalha(Personagem atacante, Personagem defensor) {
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        turnoInicial.setValorDoAtaqueAtacante(10);
        turnoInicial.setValorDoAtaqueDefensor(10);
        turnoInicial.setValorDaDefesaAtacante(10);
        turnoInicial.setValorDaDefesaDefensor(10);
        turnoInicial.setValorDoDanoAtacante(10);
        turnoInicial.setValorDoDanoDefensor(10);
        batalha.setDadosTurnosList(List.of(turnoInicial));
        return batalha;
    }

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

        BatalhaResponse batalha = batalhaService.criarBatalha(criarBatalhaRequest);

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

        BatalhaResponse batalha = batalhaService.criarBatalha(criarBatalhaRequest);

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

        Assertions.assertThrows(TipoPersonagemDefensorIncorretoException.class, () -> batalhaService.criarBatalha(criarBatalhaRequest));
    }

    @Test
    void deveriaListarTodasBatalhas() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");

        Mockito.when(batalhaRepository.findAll()).thenReturn(List.of(new Batalha(atacante, defensor, true),
            new Batalha(atacante, defensor, true)));
        List<BatalhaResponse> batalhaResponse = batalhaService.buscarTodasBatalhas();

        Mockito.verify(batalhaRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(2, batalhaResponse.size());
    }

    @Test
    void deveriaBuscarBatalhaPorIdComSucesso() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        batalha.setDadosTurnosList(List.of(turnoInicial));
        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        BatalhaDetalhesResponse batalhaResponse = batalhaService.buscarBatalhaPorUuid(ArgumentMatchers.any());

        Mockito.verify(batalhaRepository, Mockito.times(1)).findByIdWithExceptionIfNotFound(ArgumentMatchers.any());
        Assertions.assertEquals("Jogador 1", batalhaResponse.nomeJogadorAtacante());
        Assertions.assertEquals("Monstro 1", batalhaResponse.nomeMonstroDefensor());
        Assertions.assertTrue(batalhaResponse.jogadorAtacanteVenceuIniciativa());
        Assertions.assertEquals(1, batalhaResponse.numeroTurnoAtual());
        Assertions.assertFalse(batalhaResponse.batalhaFinalizada());
    }

    @Test
    void deveriaDeletarBatalhaComSucesso() {
        Mockito.doNothing().when(batalhaRepository).deleteByIdWithExceptionIfNotFound(ArgumentMatchers.any());
        batalhaService.deletarBatalha(ArgumentMatchers.any());
        Mockito.verify(batalhaRepository, Mockito.times(1)).deleteByIdWithExceptionIfNotFound(ArgumentMatchers.any());
    }

    @Test
    void deveriaCriarNovoTurnoCasoBatalhaNaoTenhaUmVencedor() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        turnoInicial.setValorDoAtaqueAtacante(10);
        turnoInicial.setValorDoAtaqueDefensor(10);
        turnoInicial.setValorDaDefesaAtacante(10);
        turnoInicial.setValorDaDefesaDefensor(10);
        turnoInicial.setValorDoDanoAtacante(0);
        turnoInicial.setValorDoDanoDefensor(0);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.when(batalhaRepository.save(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.doNothing().when(turnoService).criarTurnoAdicional(ArgumentMatchers.any(), ArgumentMatchers.any());
        BatalhaResponse response = batalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

        Mockito.verify(turnoService, Mockito.times(1)).criarTurnoAdicional(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertTrue(response.numeroTurnoAtual() > 1);
        Assertions.assertFalse(response.batalhaFinalizada());
    }

    @Test
    void deveriaLancarEventoJaRealizadoExceptionCasoBatalhaJaTenhaUmVencedor() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.setBatalhaFinalizada(true);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(EventoJaRealizadoException.class, () -> batalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Test
    void deveriaFinalizarBatalhaCasoDefensorTenhaMorrido() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.getDadosTurnosList().get(0).setVidaAtualDefensor(0);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.when(batalhaRepository.save(ArgumentMatchers.any())).thenReturn(batalha);
        BatalhaResponse response = batalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

        Assertions.assertTrue(response.batalhaFinalizada());
        Assertions.assertEquals("Jogador 1", response.nomeVencedor());
    }

    @Test
    void deveriaFinalizarBatalhaComVencedorDaIniciativaComoVencedorDaBatalhaCasoOsDoisPersonagensMorram() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.getDadosTurnosList().get(0).setVidaAtualDefensor(0);
        batalha.getDadosTurnosList().get(0).setVidaAtualAtacante(0);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.when(batalhaRepository.save(ArgumentMatchers.any())).thenReturn(batalha);
        BatalhaResponse response = batalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

        Assertions.assertTrue(response.batalhaFinalizada());
        Assertions.assertEquals("Jogador 1", response.nomeVencedor());
        Assertions.assertNotEquals("Monstro 1", response.nomeVencedor());
    }

    @Test
    void deveriaVerificarSeMonstroVenceBatalhaQuandoAtacantePerderIniciativaQuandoAmbosMorrem() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.setAtacanteVenceuIniciativa(false);
        batalha.getDadosTurnosList().get(0).setVidaAtualDefensor(0);
        batalha.getDadosTurnosList().get(0).setVidaAtualAtacante(0);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.when(batalhaRepository.save(ArgumentMatchers.any())).thenReturn(batalha);
        BatalhaResponse response = batalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

        Assertions.assertTrue(response.batalhaFinalizada());
        Assertions.assertNotEquals("Jogador 1", response.nomeVencedor());
        Assertions.assertEquals("Monstro 1", response.nomeVencedor());
    }

    @Test
    void deveriaLancarTurnoNaoFinalizadoExceptionCasoNaoTenhaSidoFeitoAcaoAtaque() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        turnoInicial.setValorDoAtaqueAtacante(null);
        turnoInicial.setValorDoAtaqueDefensor(null);
        turnoInicial.setValorDaDefesaAtacante(10);
        turnoInicial.setValorDaDefesaDefensor(10);
        turnoInicial.setValorDoDanoAtacante(10);
        turnoInicial.setValorDoDanoDefensor(10);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(TurnoNaoFinalizadoException.class, () -> batalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Test
    void deveriaLancarTurnoNaoFinalizadoExceptionCasoNaoTenhaSidoFeitoAcaoDefesa() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        turnoInicial.setValorDoAtaqueAtacante(10);
        turnoInicial.setValorDoAtaqueDefensor(10);
        turnoInicial.setValorDaDefesaAtacante(null);
        turnoInicial.setValorDaDefesaDefensor(null);
        turnoInicial.setValorDoDanoAtacante(10);
        turnoInicial.setValorDoDanoDefensor(10);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(TurnoNaoFinalizadoException.class, () -> batalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Test
    void deveriaLancarTurnoNaoFinalizadoExceptionCasoNaoTenhaSidoFeitoAcaoDano() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno  turnoInicial = new DadosTurno(batalha);
        turnoInicial.setValorDoAtaqueAtacante(10);
        turnoInicial.setValorDoAtaqueDefensor(10);
        turnoInicial.setValorDaDefesaAtacante(10);
        turnoInicial.setValorDaDefesaDefensor(10);
        turnoInicial.setValorDoDanoAtacante(null);
        turnoInicial.setValorDoDanoDefensor(null);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(TurnoNaoFinalizadoException.class, () -> batalhaService.verificarSeBatalhaAcabou(uuid));
    }
}
