package exceptions;

public class AutomatoException extends Exception {

    private final String mensagem;

    public AutomatoException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return mensagem;
    }
}
