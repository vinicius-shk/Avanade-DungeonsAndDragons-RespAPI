package br.com.batalharepg.avanade.repository;

import br.com.batalharepg.avanade.entities.DadosTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DadosTurnoRepository extends JpaRepository<DadosTurno, Long> {
    List<DadosTurno> findAllByOrderByBatalhaUuidAscNumeroTurnoAsc();
    List<DadosTurno> findAllByBatalhaUuidOrderByNumeroTurnoAsc(UUID uuid);
}
