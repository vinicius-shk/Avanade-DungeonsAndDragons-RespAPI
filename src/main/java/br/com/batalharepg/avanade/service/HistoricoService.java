package br.com.batalharepg.avanade.service;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResumoResponse;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoricoService {
    private final BatalhaRepository batalhaRepository;

    @Transactional
    public BatalhaDetalhesResponse buscarHistoricoDetalhadoDeBatalha(UUID uuid) {
        return batalhaRepository.findByIdWithExceptionIfNotFound(uuid).getDetalhesResponseDto();
    }

    @Transactional
    public BatalhaResumoResponse listarTodosTurnosExistentesPorBatalha(UUID uuid) {
        return batalhaRepository.findByIdWithExceptionIfNotFound(uuid).getResumoResponseDto();
    }
}
