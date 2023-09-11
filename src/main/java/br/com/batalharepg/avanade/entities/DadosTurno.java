package br.com.batalharepg.avanade.entities;

import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dados_turnos")
public class DadosTurno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroTurno;
    private Integer valorDoAtaqueAtacante = null;
    private Integer valorDoAtaqueDefensor = null;
    private Integer valorDaDefesaAtacante = null;
    private Integer valorDaDefesaDefensor = null;
    private Integer valorDoDanoAtacante = null;
    private Integer valorDoDanoDefensor = null;
    private Integer vidaAtualAtacante;
    private Integer vidaAtualDefensor;
    @ManyToOne
    @JoinColumn(name = "batalha_id")
    @ToString.Exclude
    private Batalha batalha;

    public DadosTurno(Batalha batalha) {
        this.batalha = batalha;
        this.numeroTurno = batalha.getNumeroTurnoAtual();
        this.vidaAtualAtacante = batalha.getAtacante().getVida();
        this.vidaAtualDefensor = batalha.getDefensor().getVida();
    }

    public DadosTurno (Batalha batalha, DadosTurno turnoAnterior) {
        this.batalha = batalha;
        this.numeroTurno = batalha.getNumeroTurnoAtual();
        this.vidaAtualAtacante = turnoAnterior.getVidaAtualAtacante();
        this.vidaAtualDefensor = turnoAnterior.getVidaAtualDefensor();
    }

    public DadosTurnoResponse getDadosTurnoDto() {
        return new DadosTurnoResponse(
            this.getId(),
            this.getNumeroTurno(),
            this.getValorDoAtaqueAtacante(),
            this.getValorDoAtaqueDefensor(),
            this.getValorDaDefesaAtacante(),
            this.getValorDaDefesaDefensor(),
            this.getValorDoDanoAtacante(),
            this.getValorDoDanoDefensor(),
            this.getVidaAtualAtacante(),
            this.getVidaAtualDefensor());
    }

}
