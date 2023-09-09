package br.com.batalharepg.avanade.factory;

public interface PersonagemFactory {
    Personagem criarPersonagem(String nome, TipoPersonagem tipoPersonagem);
}
