package br.com.batalharepg.avanade.database;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import br.com.batalharepg.avanade.service.personagem.CreatePersonagemService;
import br.com.batalharepg.avanade.service.personagem.GetPersonagemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final CreatePersonagemService createPersonagemService;
    private final GetPersonagemService getPersonagemService;

    @PostConstruct
    public void init() {
        List<PersonagemResponse> todosPersonagens = getPersonagemService.listaTodosPersonagens();
        List<String> listaNomes = List.of("Aboleth", "Balor", "Denathor");
        List<TipoClassePersonagem> listaTipoClasseMontros = List.of(TipoClassePersonagem.ORC,
            TipoClassePersonagem.GIGANTE, TipoClassePersonagem.LOBISOMEM);

        if (todosPersonagens.isEmpty()) {
            for (int i = 0; i < listaNomes.size(); i++) {
                createPersonagemService.criarPersonagem(
                    new PersonagemRequest(listaNomes.get(i), listaTipoClasseMontros.get(i)));
            }
        }
    }
}
