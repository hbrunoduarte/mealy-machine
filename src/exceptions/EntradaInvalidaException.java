package exceptions;

public class EntradaInvalidaException extends AutomatoException {

    public EntradaInvalidaException(Character entrada) {
        super("[A entrada '%c' é inválida para o alfabeto (sigma) dado]".formatted(entrada));
    }
}
