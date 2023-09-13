package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBatalhaService {
    private final BatalhaRepository batalhaRepository;

    public List<BatalhaResponse> buscarTodasBatalhas() {
        return batalhaRepository.findAll().stream().map(Batalha::getResponseDto).toList();
    }
}
