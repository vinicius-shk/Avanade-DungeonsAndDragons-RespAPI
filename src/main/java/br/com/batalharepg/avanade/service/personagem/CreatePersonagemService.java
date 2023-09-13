package br.com.batalharepg.avanade.service.personagem;

import br.com.batalharepg.avanade.configuration.PersonagemFactoryConfiguration;
import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NomeExistenteException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePersonagemService {
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
}
