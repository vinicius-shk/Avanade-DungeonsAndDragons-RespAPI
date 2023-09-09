package br.com.batalharepg.avanade.entities;

import br.com.batalharepg.avanade.dto.request.PersonagemRequest;
import br.com.batalharepg.avanade.dto.response.PersonagemResponse;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String nome;
    private TipoPersonagem tipoPersonagem;
    private Integer vida;
    private Integer forca;
    private Integer defesa;
    private Integer agilidade;
    private Integer quantidadeDadosAtaque;
    private Integer quantidadeFacesDadosAtaque;

    public Personagem(String nome,
                      TipoPersonagem tipoPersonagem) {
        this.nome = nome;
        this.tipoPersonagem = tipoPersonagem;
    }

    public PersonagemResponse getResponseDto() {
        return new PersonagemResponse(
            this.getUuid(),
            this.getNome(),
            this.getTipoPersonagem(),
            this.getVida(),
            this.getForca(),
            this.getDefesa(),
            this.getAgilidade(),
            this.getQuantidadeDadosAtaque(),
            this.getQuantidadeFacesDadosAtaque());
    }
}
