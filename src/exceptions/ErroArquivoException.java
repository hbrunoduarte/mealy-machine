package exceptions;

public class ErroArquivoException extends Exception {

    public ErroArquivoException() {
        super();
    }

    @Override
    public String getMessage() {
        return "[Erro ao realizar a leitura do arquivo da máquina de Mealy]";
    }
}
