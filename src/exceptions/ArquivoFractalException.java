package exceptions;

public class ArquivoFractalException extends Exception {

    @Override
    public String getMessage() {
        return "[Não foi possível criar o arquivo PPM]";
    }
}
