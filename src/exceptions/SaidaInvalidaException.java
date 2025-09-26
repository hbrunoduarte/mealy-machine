package exceptions;

public class SaidaInvalidaException extends AutomatoException {

    public SaidaInvalidaException(String saida) {
        super("[A saída '%s' é inválida para o alfabeto (delta) dado]".formatted(saida));
    }
}
