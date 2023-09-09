package br.com.batalharepg.avanade.repository;

import br.com.batalharepg.avanade.entities.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonagemRepository extends JpaRepository<Personagem, UUID> {
    public List<Personagem> findByNome(String nome);
}
