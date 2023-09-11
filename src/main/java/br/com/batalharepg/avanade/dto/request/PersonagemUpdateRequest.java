package br.com.batalharepg.avanade.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonagemUpdateRequest(@NotNull(message = "O nome do personagem é obrigatorio")
                                      @Size(min = 2, max = 20, message = "Nome deve ter entre 2 e 20 caracteres")
                                      String nome) {
}
