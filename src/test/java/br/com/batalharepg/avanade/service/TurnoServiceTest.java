package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.CavaleiroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.DadosTurnoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TurnoServiceTest {
    @Mock
    private DadosTurnoRepository turnoRepository;
    @InjectMocks
    private TurnoService turnoService;

    @Test
    void deveriaCriarTurnoInicialComSucesso() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Atacante");
        Personagem defensor = new OrcFactory().criarPersonagem("Defensor");
        Batalha batalha = new Batalha(atacante, defensor, true);

        Mockito.when(turnoRepository.save(Mockito.any(DadosTurno.class)))
            .thenReturn(new DadosTurno(batalha));

        Assertions.assertDoesNotThrow(() -> turnoService.criarTurnoInicial(batalha));
        Mockito.verify(turnoRepository, Mockito.times(1)).save(Mockito.any(DadosTurno.class));
    }

    @Test
    void deveriaCriarTurnoAdicionalComSucesso() {
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Atacante");
        Personagem defensor = new OrcFactory().criarPersonagem("Defensor");
        Batalha batalha = new Batalha(atacante, defensor, true);
        DadosTurno turnoAnterior = new DadosTurno(batalha);

        Mockito.when(turnoRepository.save(Mockito.any(DadosTurno.class)))
            .thenReturn(new DadosTurno(batalha, turnoAnterior));

        Assertions.assertDoesNotThrow(() -> turnoService.criarTurnoAdicional(batalha, turnoAnterior));
        Mockito.verify(turnoRepository, Mockito.times(1)).save(Mockito.any(DadosTurno.class));
    }
}
