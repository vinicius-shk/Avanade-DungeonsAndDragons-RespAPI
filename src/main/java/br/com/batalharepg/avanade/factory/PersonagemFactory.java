package br.com.batalharepg.avanade.factory;

import br.com.batalharepg.avanade.entities.Personagem;

public abstract class PersonagemFactory {
    public abstract Personagem criarPersonagem(String nome);
    public abstract Integer getVida();
    public abstract Integer getForca();
    public abstract Integer getDefesa();
    public abstract Integer getAgilidade();
    public abstract Integer getQuantidadeDadosAtaque();
    public abstract Integer getQuantidadeFacesDadosAtaque();

    public Personagem completaCriacaoPersonagem(Personagem personagem) {
        personagem.setVida(this.getVida());
        personagem.setForca(this.getForca());
        personagem.setDefesa(this.getDefesa());
        personagem.setAgilidade(this.getAgilidade());
        personagem.setQuantidadeDadosAtaque(this.getQuantidadeDadosAtaque());
        personagem.setQuantidadeFacesDadosAtaque(this.getQuantidadeFacesDadosAtaque());
        return personagem;
    }

    public abstract TipoPersonagem getTipoPersonagem();
}
