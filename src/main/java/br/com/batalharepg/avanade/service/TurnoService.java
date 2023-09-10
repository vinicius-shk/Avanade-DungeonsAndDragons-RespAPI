package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.repository.DadosTurnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TurnoService {
    private final DadosTurnoRepository turnoRepository;

    public void criarTurno(Batalha batalha) {
        turnoRepository.save(new DadosTurno(batalha));
    }
}
