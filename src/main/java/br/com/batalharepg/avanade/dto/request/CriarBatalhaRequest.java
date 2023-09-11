package br.com.batalharepg.avanade.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarBatalhaRequest(@NotNull(message = "Personagem do atacante deve ser indormado")
                                  @Size(min = 2, max = 20, message = "Nome do personagem deve ter entre 2 e 20 caracteres")
                                  String nomeJogadorAtacante,
                                  @Nullable
                                  @Size(min = 2, max = 20, message = "Nome do monstro deve ter entre 2 e 20 caracteres")
                                  String nomeMonstroDefensor) {
}
