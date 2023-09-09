package br.com.batalharepg.avanade.factory;

import br.com.batalharepg.avanade.entities.Personagem;

public interface PersonagemFactory {
    Personagem criarPersonagem(String nome);

    TipoPersonagem getTipoPersonagem();
}
