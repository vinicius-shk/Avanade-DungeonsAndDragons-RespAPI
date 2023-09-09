package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.configuration.PersonagemFactoryConfiguration;
import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonagemService {
    private final PersonagemRepository personagemRepository;
    private final PersonagemFactoryConfiguration factoryConfiguration;

    public PersonagemResponse criarPersonagem(PersonagemRequest dto) {
        PersonagemFactory factory = factoryConfiguration.getFactory(dto.tipoPersonagem());
        if (factory != null) {
            Personagem personagem = factory.criarPersonagem(dto.nome());
            return personagemRepository.save(personagem).getResponseDto();
        }
        return null;
    }

    public List<PersonagemResponse> listaTodosPersonagens() {
        return personagemRepository.findAll().stream().map(Personagem::getResponseDto).toList();
    }

    public List<PersonagemResponse> listaPersonagemPorNome(String nome) {
        return personagemRepository.findByNome(nome).stream().map(Personagem::getResponseDto).toList();
    }

    public PersonagemResponse buscaPersonagemPorUuid(UUID uuid) throws NotFoundException {
        return personagemRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException("Personagem não encontrado"))
            .getResponseDto();
    }
}
