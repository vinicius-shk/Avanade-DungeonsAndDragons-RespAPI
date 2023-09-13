package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadBatalhaService {
    private final BatalhaRepository batalhaRepository;

    public List<BatalhaResponse> buscarTodasBatalhas() {
        return batalhaRepository.findAll().stream().map(Batalha::getResponseDto).toList();
    }

    @Transactional
    public BatalhaDetalhesResponse buscarBatalhaPorUuid(UUID uuid) {
        return batalhaRepository.findByIdWithExceptionIfNotFound(uuid)
            .getDetalhesResponseDto();
    }
}
