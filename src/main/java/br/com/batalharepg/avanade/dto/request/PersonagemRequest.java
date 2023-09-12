package br.com.batalharepg.avanade.dto.request;

import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonagemRequest(@NotNull(message = "Nome deve ser informado")
                                @Size(min = 2, max = 20, message = "Nome deve ter entre 2 e 20 caracteres")
                                String nome,
                                @NotNull(message = "Tipo de classe deve ser informado")
                                TipoClassePersonagem tipoClassePersonagem) {
}
