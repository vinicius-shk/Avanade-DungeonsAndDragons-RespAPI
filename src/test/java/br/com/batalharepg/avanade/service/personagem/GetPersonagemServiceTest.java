package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.BarbaroFactory;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.OrcFactory;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
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
class GetPersonagemServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;
    @InjectMocks
    private GetPersonagemService getPersonagemService;

    @Test
    void deveriaBuscarTodosOsPersonagensSalvosNoBanco() {
        Personagem personagem1 = new BarbaroFactory().criarPersonagem("Barbaro");
        Personagem personagem2 = new OrcFactory().criarPersonagem("Orc");

        Mockito.when(personagemRepository.findAll())
            .thenReturn(List.of(personagem1, personagem2));

        Assertions.assertEquals(2, getPersonagemService.listaTodosPersonagens().size());
    }

    @Test
    void deveriaRetornarUsuarioPorNomeComSucesso() {
        Personagem personagem = new BarbaroFactory().criarPersonagem("Barbaro");

        Mockito.when(personagemRepository.findByNomeOrThrowException(ArgumentMatchers.anyString()))
            .thenReturn(personagem);

        Assertions.assertEquals("Barbaro", getPersonagemService.buscaPersonagemPorNome("Barbaro").nome());
    }

    @Test
    void deveriaLancarExcecaoCasoNaoEncontrePersonagemPorNome() {
        Mockito.when(personagemRepository.findByNome("Nome Inexistente"))
            .thenReturn(Optional.empty());

        // Verifica a exceção default lançada pelo método findByNomeOrThrowException do JpaRepositoy
        Assertions.assertThrows(NullPointerException.class, () -> getPersonagemService.buscaPersonagemPorNome("Nome Inexistente"));
    }

    @Test
    void deveriaRetornarUsuarioPorUuidComSucesso() {
        Personagem personagem = new BarbaroFactory().criarPersonagem("Barbaro");

        Mockito.when(personagemRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any()))
            .thenReturn(personagem);

        Assertions.assertEquals("Barbaro", getPersonagemService.buscaPersonagemPorUuid(personagem.getUuid()).nome());
    }

    @Test
    void deveriaLancarExcecaoCasoNaoEncontrePersonagemPorUuid() {
        UUID randomUuid = UUID.randomUUID();

        Mockito.when(personagemRepository.findById(ArgumentMatchers.any()))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(NullPointerException.class, () -> getPersonagemService.buscaPersonagemPorUuid(randomUuid));
    }
}
