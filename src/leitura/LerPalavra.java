package leitura;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LerPalavra {

    public static String lerPalavra(String caminho) throws FileNotFoundException {
        Scanner arq = new Scanner(new File(caminho));
        StringBuilder s = new StringBuilder();

        while (arq.hasNextLine())
            s.append(arq.nextLine());

        return s.toString();
    }

}
