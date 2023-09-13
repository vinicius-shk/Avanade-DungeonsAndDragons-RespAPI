package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.dto.request.PersonagemUpdateRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePersonagemService {
    private final PersonagemRepository personagemRepository;

    public PersonagemResponse atualizaPersonagem(UUID uuid, PersonagemUpdateRequest dto) throws NotFoundException {
        Personagem personagem = personagemRepository.findByIdWithExceptionIfNotFound(uuid);
        personagem.setNome(dto.nome());
        return personagemRepository.save(personagem).getResponseDto();
    }
}
