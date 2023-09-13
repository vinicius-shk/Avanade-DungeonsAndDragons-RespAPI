package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.configuration.PersonagemFactoryConfiguration;
import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NomeExistenteException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.BarbaroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class CreatePersonagemServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;
    @Mock
    private PersonagemFactoryConfiguration factoryConfiguration;
    @InjectMocks
    private CreatePersonagemService createPersonagemService;

    @BeforeEach
    public void setUp() {
        Mockito.when(factoryConfiguration.getFactory(ArgumentMatchers.any(TipoClassePersonagem.class)))
            .thenAnswer((Answer<PersonagemFactory>) invocation -> {
                TipoClassePersonagem argument = invocation.getArgument(0);

                if (argument == TipoClassePersonagem.BARBARO) {
                    return new BarbaroFactory();
                } else if (argument == TipoClassePersonagem.ORC) {
                    return new OrcFactory();
                }
                return null;
            });
    }

    @Test
    void deveriaCriarPersonagemComSucesso() {
        PersonagemRequest request1 = new PersonagemRequest("Barbaro", TipoClassePersonagem.BARBARO);
        PersonagemRequest request2 = new PersonagemRequest("Orc", TipoClassePersonagem.ORC);
        Personagem personagem1 = new BarbaroFactory().criarPersonagem(request1.nome());
        Personagem personagem2 = new OrcFactory().criarPersonagem(request2.nome());

        Mockito.when(personagemRepository.findByNome(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.empty());

        Mockito.when(personagemRepository.save(ArgumentMatchers.any(Personagem.class)))
            .thenReturn(personagem1)
            .thenReturn(personagem2);

        PersonagemResponse response1 = createPersonagemService.criarPersonagem(request1);
        PersonagemResponse response2 = createPersonagemService.criarPersonagem(request2);

        Assertions.assertDoesNotThrow(() -> createPersonagemService.criarPersonagem(request1));
        Assertions.assertDoesNotThrow(() -> createPersonagemService.criarPersonagem(request2));
        Assertions.assertEquals(personagem1.getNome(), response1.nome());
        Assertions.assertEquals(personagem2.getNome(), response2.nome());
        Assertions.assertNotEquals(response1, response2);
    }

    @Test
    void deveriaLancarExcecaoCasoExistaPersonagemComMesmoNome() {
        PersonagemRequest request = new PersonagemRequest("Barbaro", TipoClassePersonagem.BARBARO);
        Personagem personagem = new BarbaroFactory().criarPersonagem(request.nome());

        Mockito.when(personagemRepository.findByNome(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(personagem));

        Assertions.assertThrows(NomeExistenteException.class, () -> createPersonagemService.criarPersonagem(request));
    }

    @Test
    void deveriaLancarExcecaoCasoNaoExistaFactoryParaClasseRequisitada() {
        PersonagemRequest request = new PersonagemRequest("Barbaro", TipoClassePersonagem.BARBARO);

        Mockito.when(personagemRepository.findByNome(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        Mockito.when(factoryConfiguration.getFactory(ArgumentMatchers.any(TipoClassePersonagem.class)))
            .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> createPersonagemService.criarPersonagem(request));
    }
}
