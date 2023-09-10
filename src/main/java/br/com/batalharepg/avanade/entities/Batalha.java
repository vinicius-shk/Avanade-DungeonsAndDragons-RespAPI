package br.com.batalharepg.avanade.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToOne
    @JoinColumn(name = "atacante_uuid")
    private Personagem atacante;
    @OneToOne
    @JoinColumn(name = "defensor_uuid")
    private Personagem defensor;
    private Boolean atacanteVenceuIniciativa;
    @Column(columnDefinition = "integer default 1")
    private Integer numeroTurnoAtual;
    @Column(columnDefinition = "boolean default false")
    private Boolean batalhaFinalizada;
    @Column(columnDefinition = "varchar(50) default 'Batalha em andamento'")
    private String vencedor;
    @OneToMany(mappedBy = "batalha")
    private List<DadosTurno> dadosTurnosList;
}
