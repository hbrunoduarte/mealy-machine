package exceptions;

import dados.Estado;

public class TransicaoIndefinidaException extends AutomatoException {

    public TransicaoIndefinidaException(Estado e, Character entrada) {
        super("[A transição do estado %s não está definida para a entrada '%c']".formatted(e.getNome(), entrada));
    }
}
