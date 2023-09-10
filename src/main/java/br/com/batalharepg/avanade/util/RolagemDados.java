package br.com.batalharepg.avanade.util;

import java.util.Random;

public class RolagemDados {
    public static final Random RANDOM = new Random();
    public static final Integer LADOS_DADO_INICIATIVA = 20;
    public static final Integer NUMERO_DADOS_INICIATIVA = 1;

    private RolagemDados() {
    }

    public static Integer rolarDados(Integer numeroDados, Integer numeroFaces) {
        Integer resultado = 0;
        for (int i = 0; i < numeroDados; i++) {
            resultado += RANDOM.nextInt(numeroFaces) + 1;
        }
        return resultado;
    }

    public static Integer rolarDadoIniciativa() {
        return rolarDados(NUMERO_DADOS_INICIATIVA, LADOS_DADO_INICIATIVA);
    }
}
