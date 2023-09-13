package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.TurnoNaoFinalizadoException;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.CavaleiroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.service.TurnoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class UpdateBatalhaServiceTest {
    @Mock
    private BatalhaRepository batalhaRepository;
    @Mock
    private TurnoService turnoService;
    @InjectMocks
    private UpdateBatalhaService updateBatalhaService;

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

    @Test
    void deveriaCriarNovoTurnoCasoBatalhaNaoTenhaUmVencedor() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.when(batalhaRepository.save(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.doNothing().when(turnoService).criarTurnoAdicional(ArgumentMatchers.any(), ArgumentMatchers.any());
        BatalhaResponse response = updateBatalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

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

        Assertions.assertThrows(EventoJaRealizadoException.class, () -> updateBatalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Test
    void deveriaFinalizarBatalhaCasoDefensorTenhaMorrido() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.getDadosTurnosList().get(0).setVidaAtualDefensor(0);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);
        Mockito.when(batalhaRepository.save(ArgumentMatchers.any())).thenReturn(batalha);
        BatalhaResponse response = updateBatalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

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
        BatalhaResponse response = updateBatalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

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
        BatalhaResponse response = updateBatalhaService.verificarSeBatalhaAcabou(ArgumentMatchers.any());

        Assertions.assertTrue(response.batalhaFinalizada());
        Assertions.assertNotEquals("Jogador 1", response.nomeVencedor());
        Assertions.assertEquals("Monstro 1", response.nomeVencedor());
    }

    @Test
    void deveriaLancarTurnoNaoFinalizadoExceptionCasoNaoTenhaSidoFeitoAcaoAtaque() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.getDadosTurnosList().get(0).setValorDoAtaqueAtacante(null);
        batalha.getDadosTurnosList().get(0).setValorDoAtaqueDefensor(null);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(TurnoNaoFinalizadoException.class, () -> updateBatalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Test
    void deveriaLancarTurnoNaoFinalizadoExceptionCasoNaoTenhaSidoFeitoAcaoDefesa() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.getDadosTurnosList().get(0).setValorDaDefesaAtacante(null);
        batalha.getDadosTurnosList().get(0).setValorDaDefesaDefensor(null);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(TurnoNaoFinalizadoException.class, () -> updateBatalhaService.verificarSeBatalhaAcabou(uuid));
    }

    @Test
    void deveriaLancarTurnoNaoFinalizadoExceptionCasoNaoTenhaSidoFeitoAcaoDano() {
        UUID uuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Jogador 1");
        Personagem defensor = new OrcFactory().criarPersonagem("Monstro 1");
        Batalha batalha = criaBatalha(atacante, defensor);
        batalha.getDadosTurnosList().get(0).setValorDoDanoAtacante(null);
        batalha.getDadosTurnosList().get(0).setValorDoDanoDefensor(null);

        Mockito.when(batalhaRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any())).thenReturn(batalha);

        Assertions.assertThrows(TurnoNaoFinalizadoException.class, () -> updateBatalhaService.verificarSeBatalhaAcabou(uuid));
    }
}
