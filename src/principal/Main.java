package principal;

import dados.MaquinaMealy;
import exceptions.AutomatoException;
import exceptions.ErroArquivoException;
import leitura.CriarMaquina;
import leitura.LerPalavra;

import java.io.FileNotFoundException;

import static principal.ParametrosEntrada.MAQUINA_MEALY;
import static principal.ParametrosEntrada.PALAVRA_ENTRADA;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("[2 parâmetros de entrada são esperados]");
            throw new RuntimeException();
        }

        CriarMaquina criarMaquina = new CriarMaquina(args[MAQUINA_MEALY.ordinal()]);

        MaquinaMealy mealy;
        try {
            mealy = criarMaquina.criarMealy();

        } catch (FileNotFoundException e1) {
            System.err.println("[Arquivo de entrada da máquina de Mealy não encontrado]");
            throw new RuntimeException();

        } catch (AutomatoException e2) {
            System.err.println(e2.getMessage());
            throw new RuntimeException();

        } catch (ErroArquivoException e3) {
            System.err.println(e3.getMessage());
            throw new RuntimeException();
        }

        String entrada;
        try {
            entrada = LerPalavra.lerPalavra(args[PALAVRA_ENTRADA.ordinal()]);
        } catch (FileNotFoundException e) {
            System.err.println("[Arquivo de entrada da palavra não encontrado]");
            throw new RuntimeException(e);
        }

        boolean aceitou = mealy.computarPalavra(entrada);

        if (aceitou)
            System.out.printf("\nA palavra foi aceita!\nSaída:\n%s\n\n".formatted(mealy.getSaida()));
        else
            System.out.println("\nA palavra foi rejeitada!\n\n");

    }
}
