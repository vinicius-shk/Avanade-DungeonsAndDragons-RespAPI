package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPersonagemService {
    private final PersonagemRepository personagemRepository;

    public List<PersonagemResponse> listaTodosPersonagens() {
        return personagemRepository.findAll().stream().map(Personagem::getResponseDto).toList();
    }

    public PersonagemResponse buscaPersonagemPorNome(String nome) {
        return personagemRepository.findByNomeOrThrowException(nome)
            .getResponseDto();
    }

    public PersonagemResponse buscaPersonagemPorUuid(UUID uuid) throws NotFoundException {
        return personagemRepository.findByIdWithExceptionIfNotFound(uuid)
            .getResponseDto();
    }

}
