package br.com.batalharepg.avanade.repository;

import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BatalhaRepository extends JpaRepository<Batalha, UUID> {
    default void deleteByIdWithExceptionIfNotFound(UUID id) {
        if (existsById(id)) {
            deleteById(id);
        } else {
            throw new NotFoundException("Batalha não encontrada");
        }
    }

    default Batalha findByIdWithExceptionIfNotFound(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Batalha não encontrada"));
    }
}
