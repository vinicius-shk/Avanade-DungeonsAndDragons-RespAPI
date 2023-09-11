package br.com.batalharepg.avanade.util;

import java.util.Random;

public class RolagemDados {
    public static final Random RANDOM = new Random();

    private RolagemDados() {
    }

    public static Integer rolarDados(Integer numeroDados, Integer numeroFaces) {
        var resultado = 0;
        for (int i = 0; i < numeroDados; i++) {
            resultado += RANDOM.nextInt(numeroFaces) + 1;
        }
        return resultado;
    }

    public static Integer rolarD20() {
        final var NUMERO_DADOS_INICIATIVA = 1;
        final var LADOS_DADO_INICIATIVA = 20;
        return rolarDados(NUMERO_DADOS_INICIATIVA, LADOS_DADO_INICIATIVA);
    }

    public static Integer rolarD12() {
        final var NUMERO_DADOS_ATAQUE_DEFESA = 1;
        final var LADOS_DADO_ATAQUE_DEFESA = 12;
        return rolarDados(NUMERO_DADOS_ATAQUE_DEFESA, LADOS_DADO_ATAQUE_DEFESA);
    }
}
