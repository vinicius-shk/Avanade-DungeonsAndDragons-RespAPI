package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.implementacoes.BarbaroFactory;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeletePersonagemServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;
    @InjectMocks
    private DeletePersonagemService deletePersonagemService;

    @Test
    void deveriaDeletarPersonagemComSucesso() {
        Personagem personagem = new BarbaroFactory().criarPersonagem("Barbaro");

        Mockito.when(personagemRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any()))
            .thenReturn(personagem);

        Assertions.assertDoesNotThrow(() -> deletePersonagemService.deletaPersonagem(personagem.getUuid()));
    }
}
