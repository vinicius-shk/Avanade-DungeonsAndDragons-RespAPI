package br.com.batalharepg.avanade.configuration;

import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;

@Configuration
public class AcaoFactoryConfiguration {
    private final EnumMap<TipoAcao, AcaoFactory> factoryMap = new EnumMap<>(TipoAcao.class);

    @Autowired
    public void acaoFactoryConfig(List<AcaoFactory> factories) {
        for (AcaoFactory factory : factories) {
            factoryMap.put(factory.getTipoAcao(), factory);
        }
    }

    public AcaoFactory getFactory(TipoAcao tipoAcao) {
        return factoryMap.get(tipoAcao);
    }
}
