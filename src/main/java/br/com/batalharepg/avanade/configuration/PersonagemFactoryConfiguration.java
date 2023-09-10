package br.com.batalharepg.avanade.configuration;

import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;

@Configuration
public class PersonagemFactoryConfiguration {
    private final EnumMap<TipoPersonagem, PersonagemFactory> factoryMap = new EnumMap<>(TipoPersonagem.class);

    @Autowired
    public void characterFactoryConfig(List<PersonagemFactory> factories) {
        for (PersonagemFactory factory : factories) {
            factoryMap.put(factory.getTipoPersonagem(), factory);
        }
    }

    public PersonagemFactory getFactory(TipoPersonagem tipoPersonagem) {
        return factoryMap.get(tipoPersonagem);
    }
}
