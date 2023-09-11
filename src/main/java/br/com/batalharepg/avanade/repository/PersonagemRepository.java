package br.com.batalharepg.avanade.repository;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, UUID> {

    default Personagem findByIdWithExceptionIfNotFound(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Personagem não encontrado"));
    }

    default Personagem findByNomeOrThrowException(String nome) {
        return findByNome(nome).orElseThrow(() -> new NotFoundException("Personagem não encontrado"));
    }
    Optional<Personagem> findByNome(String nome);

    @Query("SELECT p FROM Personagem p WHERE p.nome IN ('Aboleth', 'Balor', 'Denathor') ORDER BY RANDOM() LIMIT 1")
    Personagem sorteiaMonstroDefaultParaCombate();
}
