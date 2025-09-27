package escrita;

import exceptions.ArquivoFractalException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CriarPPM {

    public static void criar(String saida) throws ArquivoFractalException {

        FileWriter arq;
        try {
            arq = new FileWriter("src/fractal.ppm");
        } catch (IOException e) {
            throw new ArquivoFractalException();
        }

        int ordem = saida.indexOf('\n');

        try {
            arq.write("P1\n");
            arq.write("%d %d\n".formatted(ordem, ordem));
            arq.write(saida);

            arq.close();

        } catch (IOException e) {
            throw new ArquivoFractalException();
        }
    }

}
