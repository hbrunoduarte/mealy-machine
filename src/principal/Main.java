package principal;

import dados.MaquinaMealy;
import exceptions.AutomatoException;
import exceptions.ErroArquivoException;
import leitura.CriarMaquina;

import java.io.FileNotFoundException;

import static principal.ParametrosEntrada.MAQUINA_MEALY;
import static principal.ParametrosEntrada.PALAVRA_ENTRADA;

public class Main {

    public static void main(String[] args) {

        CriarMaquina criarMaquina = new CriarMaquina("src/MM");//args[MAQUINA_MEALY.ordinal()]);

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

        boolean aceitou = mealy.computarPalavra("   xxxx.    xxxxxx.x     xxxxx x  x.");//args[PALAVRA_ENTRADA.ordinal()]);

        if (aceitou)
            System.out.println("\nA palavra foi aceita!\nSaída: %s\n\n".formatted(mealy.getSaida()));
        else
            System.out.println("\nA palavra foi rejeitada!\n\n");

    }
}
