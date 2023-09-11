package br.com.batalharepg.avanade.configuration;

import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;

@Configuration
public class PersonagemFactoryConfiguration {
    private final EnumMap<TipoClassePersonagem, PersonagemFactory> factoryMap = new EnumMap<>(TipoClassePersonagem.class);

    @Autowired
    public void characterFactoryConfig(List<PersonagemFactory> factories) {
        for (PersonagemFactory factory : factories) {
            factoryMap.put(factory.getTipoClassePersonagem(), factory);
        }
    }

    public PersonagemFactory getFactory(TipoClassePersonagem tipoClassePersonagem) {
        return factoryMap.get(tipoClassePersonagem);
    }
}
