package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.repository.DadosTurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoService {
    private final DadosTurnoRepository dadosTurnoRepository;

    public List<DadosTurnoResponse> listarTodosTurnosExistentes() {
        return dadosTurnoRepository.findAllByOrderByBatalhaUuidAscNumeroTurnoAsc()
                .stream()
                .map(DadosTurno::getDadosTurnoDto)
                .toList();
    }
}
