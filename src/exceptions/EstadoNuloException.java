package exceptions;

public class EstadoNuloException extends AutomatoException {

    public EstadoNuloException() {
        super("[Erro ao adicionar estado: estado nulo]");
    }
}
