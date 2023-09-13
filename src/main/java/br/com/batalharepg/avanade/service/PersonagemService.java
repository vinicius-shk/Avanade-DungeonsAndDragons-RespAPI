package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.configuration.PersonagemFactoryConfiguration;
import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.request.PersonagemUpdateRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NomeExistenteException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonagemService {
    private final PersonagemRepository personagemRepository;
    private final PersonagemFactoryConfiguration factoryConfiguration;

    public PersonagemResponse criarPersonagem(PersonagemRequest dto) {
        personagemRepository.findByNome(dto.nome()).ifPresent(personagem -> {
            throw new NomeExistenteException("Personagem com este nome já cadastrado");
        });
        PersonagemFactory factory = factoryConfiguration.getFactory(dto.tipoClassePersonagem());
        if (factory != null) {
            Personagem personagem = factory.criarPersonagem(dto.nome());
            return personagemRepository.save(personagem).getResponseDto();
        }
        throw new NotFoundException("Tipo de personagem não encontrado");
    }

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

    public PersonagemResponse atualizaPersonagem(UUID uuid, PersonagemUpdateRequest dto) throws NotFoundException {
        Personagem personagem = personagemRepository.findByIdWithExceptionIfNotFound(uuid);
        personagem.setNome(dto.nome());
        return personagemRepository.save(personagem).getResponseDto();
    }

    public void deletaPersonagem(UUID uuid) throws NotFoundException {
        Personagem personagem = personagemRepository.findByIdWithExceptionIfNotFound(uuid);
        personagemRepository.delete(personagem);
    }
}
