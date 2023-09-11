package br.com.batalharepg.avanade.database;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;
import br.com.batalharepg.avanade.service.PersonagemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final PersonagemService personagemService;

    @PostConstruct
    public void init() {
        List<PersonagemResponse> todosPersonagens = personagemService.listaTodosPersonagens();
        List<String> listaNomes = List.of("Aboleth", "Balor", "Denathor");
        List<TipoPersonagem> listaTipoMontros = List.of(TipoPersonagem.ORC, TipoPersonagem.GIGANTE, TipoPersonagem.LOBISOMEM);

        if (todosPersonagens.isEmpty()) {
            for (int i = 0; i < listaNomes.size(); i++) {
                personagemService.criarPersonagem(new PersonagemRequest(listaNomes.get(i), listaTipoMontros.get(i)));
            }
        }
    }
}
