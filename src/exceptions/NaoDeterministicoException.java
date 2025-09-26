package exceptions;

import dados.Estado;

public class NaoDeterministicoException extends AutomatoException {
    public NaoDeterministicoException(Estado origem, Estado destino, Character entrada) {
        super("[O estado %s já possui transição para %s lendo '%c']".formatted(origem.getNome(), destino.getNome(), entrada));
    }

    public NaoDeterministicoException() {
        super("[Mais de um estado inicial identificado]");
    }
}
