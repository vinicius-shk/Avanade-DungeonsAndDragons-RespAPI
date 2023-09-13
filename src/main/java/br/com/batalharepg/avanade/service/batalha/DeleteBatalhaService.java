package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.repository.BatalhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBatalhaService {
    private final BatalhaRepository batalhaRepository;

    public void deletarBatalha(UUID uuid) {
        batalhaRepository.deleteByIdWithExceptionIfNotFound(uuid);
    }

}
