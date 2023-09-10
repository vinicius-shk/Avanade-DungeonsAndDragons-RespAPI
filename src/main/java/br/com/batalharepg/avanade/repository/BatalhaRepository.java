package br.com.batalharepg.avanade.repository;

import br.com.batalharepg.avanade.entities.Batalha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BatalhaRepository extends JpaRepository<Batalha, UUID> {
}
