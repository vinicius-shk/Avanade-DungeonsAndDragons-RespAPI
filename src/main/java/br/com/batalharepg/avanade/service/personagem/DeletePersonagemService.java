package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePersonagemService {
    private final PersonagemRepository personagemRepository;

    public void deletaPersonagem(UUID uuid) throws NotFoundException {
        Personagem personagem = personagemRepository.findByIdWithExceptionIfNotFound(uuid);
        personagemRepository.delete(personagem);
    }
}
