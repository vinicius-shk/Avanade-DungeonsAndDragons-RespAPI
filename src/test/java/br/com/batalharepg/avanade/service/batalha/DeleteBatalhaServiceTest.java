package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.repository.BatalhaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeleteBatalhaServiceTest {
    @Mock
    private BatalhaRepository batalhaRepository;
    @InjectMocks
    private DeleteBatalhaService deleteBatalhaService;

    @Test
    void deveriaDeletarBatalhaComSucesso() {
        Mockito.doNothing().when(batalhaRepository).deleteByIdWithExceptionIfNotFound(ArgumentMatchers.any());
        deleteBatalhaService.deletarBatalha(ArgumentMatchers.any());
        Mockito.verify(batalhaRepository, Mockito.times(1)).deleteByIdWithExceptionIfNotFound(ArgumentMatchers.any());
    }
}
