package br.com.batalharepg.avanade.entities;

import br.com.batalharepg.avanade.dto.response.BatalhaDetalhesResponse;
import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "batalhas")
public class Batalha {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "atacante_uuid")
    private Personagem atacante;
    @ManyToOne
    @JoinColumn(name = "defensor_uuid")
    private Personagem defensor;
    private Boolean atacanteVenceuIniciativa;
    private Integer numeroTurnoAtual = 1;
    private Boolean turnoAtualFinalizado = false;
    private Boolean batalhaFinalizada = false;
    private String nomeVencedor = "Batalha em andamento";
    @OneToMany(mappedBy = "batalha")
    @ToString.Exclude
    private List<DadosTurno> dadosTurnosList;

    public Batalha(Personagem atacante, Personagem defensor, Boolean atacanteVenceuIniciativa) {
        this.atacante = atacante;
        this.defensor = defensor;
        this.atacanteVenceuIniciativa = atacanteVenceuIniciativa;
    }



    public BatalhaResponse getResponseDto() {
        return new BatalhaResponse(
            this.getUuid(),
            this.getAtacante().getNome(),
            this.getDefensor().getNome(),
            this.getAtacanteVenceuIniciativa(),
            this.getNumeroTurnoAtual(),
            this.getBatalhaFinalizada(),
            this.getNomeVencedor());
    }

    public BatalhaDetalhesResponse getDetalhesResponseDto() {
        return new BatalhaDetalhesResponse(
            this.getUuid(),
            this.getAtacante().getNome(),
            this.getDefensor().getNome(),
            this.getAtacanteVenceuIniciativa(),
            this.getNumeroTurnoAtual(),
            this.getBatalhaFinalizada(),
            this.getNomeVencedor(),
            this.getDadosTurnosList().stream().map(DadosTurno::getDadosTurnoDto).toList());
    }
}
