package br.com.batalharepg.avanade.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dados_turnos")
public class DadosTurno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer valorDoAtaque;
    private Integer valorDaDefesa;
    private Integer valorDoDano;
    private Integer vidaAtualAtacante;
    private Integer vidaAtualDefensor;
    @ManyToOne
    @JoinColumn(name = "batalha_id")
    private Batalha batalha;

}
