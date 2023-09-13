package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.dto.request.PersonagemUpdateRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
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
class UpdatePersonagemServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;
    @InjectMocks
    private UpdatePersonagemService updatePersonagemService;

    @Test
    void deveriaAtualizarPersonagemComSucesso() {
        Personagem personagem = new BarbaroFactory().criarPersonagem("Barbaro");
        String nomeAnterior = personagem.getNome();
        PersonagemUpdateRequest request = new PersonagemUpdateRequest("Barbaro Atualizado");

        Mockito.when(personagemRepository.findByIdWithExceptionIfNotFound(ArgumentMatchers.any()))
            .thenReturn(personagem);

        Mockito.when(personagemRepository.save(ArgumentMatchers.any()))
            .thenReturn(personagem);
        PersonagemResponse response = updatePersonagemService.atualizaPersonagem(personagem.getUuid(), request);

        Assertions.assertEquals(request.nome(), response.nome());
        Assertions.assertNotEquals(nomeAnterior, response.nome());
    }
}
