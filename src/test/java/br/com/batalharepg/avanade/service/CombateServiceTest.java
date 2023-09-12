package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.configuration.AcaoFactoryConfiguration;
import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.factory.combate.implementacoes.AtaqueFactory;
import br.com.batalharepg.avanade.factory.combate.implementacoes.DanoFactory;
import br.com.batalharepg.avanade.factory.combate.implementacoes.DefesaFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.CavaleiroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.repository.DadosTurnoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class CombateServiceTest {
    @Mock
    private DadosTurnoRepository turnoRepository;
    @Mock
    private BatalhaRepository batalhaRepository;
    @Mock
    private AcaoFactoryConfiguration factoryConfiguration;
    @InjectMocks
    private CombateService combateService;

    @BeforeEach
    public void setUp() {
        Mockito.when(factoryConfiguration.getFactory(ArgumentMatchers.any(TipoAcao.class)))
            .thenAnswer((Answer<AcaoFactory>) invocation -> {
                TipoAcao argument = invocation.getArgument(0);
                if (argument == TipoAcao.ATAQUE) {
                    return new AtaqueFactory();
                } else if (argument == TipoAcao.DEFESA) {
                    return new DefesaFactory();
                } else if (argument == TipoAcao.DANO) {
                    return new DanoFactory();
                }
                return null;
            });
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Cavaleiro");
        Personagem defensor = new OrcFactory().criarPersonagem("Orc");
        Batalha batalha = new Batalha(atacante, defensor, true);
        batalha.setBatalhaFinalizada(false);
        DadosTurno turnoInicial = new DadosTurno(batalha);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findById(ArgumentMatchers.any(UUID.class)))
            .thenReturn(java.util.Optional.of(batalha));
        Mockito.when(turnoRepository.save(ArgumentMatchers.any(DadosTurno.class)))
            .thenAnswer((Answer<DadosTurno>) invocation -> invocation.getArgument(0));
        Mockito.when(combateService.obterBatalhaPorid(ArgumentMatchers.any(UUID.class)))
            .thenReturn(batalha);
    }

    @Test
    void deveriaCalcularValoresDeAtaqueCorretamente() {
        UUID batalhaUuid = UUID.randomUUID();

        DadosTurnoResponse turnoAtualComAtaque = combateService.calcularValorTotalAcaoPersonagens(batalhaUuid,
            TipoAcao.ATAQUE);

        Assertions.assertNotNull(turnoAtualComAtaque.valorDoAtaqueDefensor());
        Assertions.assertNotNull(turnoAtualComAtaque.valorDoAtaqueAtacante());
    }

    @Test
    void deveriaCalcularValoresDeDefesaCorretamente() {
        UUID batalhaUuid = UUID.randomUUID();

        DadosTurnoResponse turnoAtualComDefesa = combateService.calcularValorTotalAcaoPersonagens(batalhaUuid,
            TipoAcao.DEFESA);

        Assertions.assertNotNull(turnoAtualComDefesa.valorDaDefesaDefensor());
        Assertions.assertNotNull(turnoAtualComDefesa.valorDaDefesaAtacante());
    }

    @Test
    void deveriaCalcularDanoCorretamente() {
        UUID batalhaUuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Cavaleiro");
        Personagem defensor = new OrcFactory().criarPersonagem("Orc");
        Batalha batalha = new Batalha(atacante, defensor, true);
        batalha.setBatalhaFinalizada(false);
        DadosTurno turnoInicial = new DadosTurno(batalha);
        turnoInicial.setValorDoAtaqueAtacante(10);
        turnoInicial.setValorDoAtaqueDefensor(10);
        turnoInicial.setValorDaDefesaAtacante(5);
        turnoInicial.setValorDaDefesaDefensor(5);
        batalha.setDadosTurnosList(List.of(turnoInicial));

        Mockito.when(batalhaRepository.findById(ArgumentMatchers.any(UUID.class)))
            .thenReturn(java.util.Optional.of(batalha));
        Mockito.when(turnoRepository.save(ArgumentMatchers.any(DadosTurno.class)))
            .thenAnswer((Answer<DadosTurno>) invocation -> invocation.getArgument(0));
        Mockito.when(combateService.obterBatalhaPorid(ArgumentMatchers.any(UUID.class)))
            .thenReturn(batalha);

        DadosTurnoResponse turnoAtualComDano = combateService.calcularValorTotalAcaoPersonagens(batalhaUuid,
            TipoAcao.DANO);

        Assertions.assertNotNull(turnoAtualComDano.valorDoDanoDefensor());
        Assertions.assertNotNull(turnoAtualComDano.valorDoDanoAtacante());
        Assertions.assertTrue(turnoAtualComDano.valorDoDanoDefensor() > 0);
        Assertions.assertTrue(turnoAtualComDano.valorDoDanoAtacante() > 0);
        Assertions.assertTrue(turnoInicial.getVidaAtualAtacante() < atacante.getVida());
        Assertions.assertTrue(turnoInicial.getVidaAtualDefensor() < defensor.getVida());
    }

    @Test
    void deveriaLancarEventoJaRealizadoExceptionSeBatalhaFoiFinalizada() {
        UUID batalhaUuid = UUID.randomUUID();
        Personagem atacante = new CavaleiroFactory().criarPersonagem("Cavaleiro");
        Personagem defensor = new OrcFactory().criarPersonagem("Orc");
        Batalha batalha = new Batalha(atacante, defensor, true);
        batalha.setBatalhaFinalizada(true);

        Mockito.when(batalhaRepository.findById(ArgumentMatchers.any(UUID.class)))
            .thenReturn(java.util.Optional.of(batalha));
        Mockito.when(turnoRepository.save(ArgumentMatchers.any(DadosTurno.class)))
            .thenAnswer((Answer<DadosTurno>) invocation -> invocation.getArgument(0));
        Mockito.when(combateService.obterBatalhaPorid(ArgumentMatchers.any(UUID.class)))
            .thenReturn(batalha);

        Assertions.assertThrows(EventoJaRealizadoException.class,
            () -> combateService.calcularValorTotalAcaoPersonagens(batalhaUuid, TipoAcao.ATAQUE));
    }

    @Test
    void deveriaLancarNotFoundExceptionSeTipoAcaoForInvalido() {
        UUID batalhaUuid = UUID.randomUUID();

        Assertions.assertThrows(NotFoundException.class,
            () -> combateService.calcularValorTotalAcaoPersonagens(batalhaUuid, null));
    }
}
