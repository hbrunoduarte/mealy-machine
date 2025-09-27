package leitura;

import dados.Estado;
import dados.MaquinaMealy;
import exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static leitura.ComponentesTransicao.*;

public class CriarMaquina {

    private final String nomeArquivo;

    private static final char SEPARADOR = ' ';
    private static final char PALAVRA_VAZIA = 'e';
    private static final char ESCAPE = '\\';

    public CriarMaquina(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    private String caractereEscape(String c) {
        if (c.equals("\\n"))
            return "\n";
        else if (c.equals("\\t"))
            return "\t";
        else
            return null;
    }

    private String lerCampo(String linha) {

        StringBuilder s = new StringBuilder();
        int i = 0;
        while (i < linha.length() && linha.charAt(i) != SEPARADOR) {
            s.append(linha.charAt(i));
            i++;
        }

        if (i == 0) return null;

        return s.toString();
    }

    private ArrayList<String> getDados(String linha) {
        ArrayList<String> campos = new ArrayList<>();

        int i = 0;
        while (i < linha.length()) {

            String campo = lerCampo(linha.substring(i));

            if (campo == null) {
                campos.add(" ");
                i++;
            } else {
                campos.add(campo);
                i += campo.length();
            }

            i++;
        }

        return campos;
    }

    private ArrayList<String> getTransicao(String linha) throws ErroArquivoException {
        int i = 0;
        ArrayList<String> campos = new ArrayList<>();

        String origem = lerCampo(linha);
        if (origem == null) throw new ErroArquivoException();
        i += origem.length() + 1;

        String entrada = lerCampo(linha.substring(i));
        if (entrada == null) {
            entrada = " ";
        }
        i += 2;

        String destino = lerCampo(linha.substring(i));
        if (destino == null) throw new ErroArquivoException();
        i += destino.length() + 1;

        String saida;
        StringBuilder s = new StringBuilder();
        while (i < linha.length()) {
            s.append(linha.charAt(i));
            i++;
        }
        saida = s.toString();

        if (saida.equals(String.valueOf(PALAVRA_VAZIA)))
            saida = "";

        campos.add(origem);
        campos.add(entrada);
        campos.add(destino);
        campos.add(saida);

        return campos;
    }

    private ArrayList<String> getCampos(String linha, boolean isTransicao) throws ErroArquivoException {
        if (!isTransicao)
            return getDados(linha);
        else
            return getTransicao(linha);
    }

    private Estado getEstado(ArrayList<Estado> estados, String nome) {
        return estados.get(estados.indexOf(new Estado(nome, false, false)));
    }

    public MaquinaMealy criarMealy() throws FileNotFoundException, AutomatoException, ErroArquivoException {

        Scanner arq = new Scanner(new File(nomeArquivo));

        ArrayList<String> nomesEstados = getCampos(arq.nextLine(), false);
        String nomeEstadoInicial = arq.nextLine();
        ArrayList<String> nomesEstadosFinais = getCampos(arq.nextLine(), false);

        ArrayList<Estado> estados = new ArrayList<>(nomesEstados.size());

        for (int i = 0; i < nomesEstados.size(); i++) {

            boolean inicial = false, terminal = false;
            if (nomesEstados.get(i).equals(nomeEstadoInicial))
                inicial = true;
            if (nomesEstadosFinais.contains(nomesEstados.get(i)))
                terminal = true;

            estados.add(new Estado(nomesEstados.get(i), inicial, terminal));
        }

        ArrayList<String> sigma = getCampos(arq.nextLine(), false);
        ArrayList<String> delta = getCampos(arq.nextLine(), false);

        while (arq.hasNextLine()) {

            ArrayList<String> componentesTransicao = getCampos(arq.nextLine(), true);

            Estado origem = getEstado(estados, componentesTransicao.get(ORIGEM.ordinal()));

            Character entrada = componentesTransicao.get(ENTRADA.ordinal()).charAt(0);
            if (entrada == PALAVRA_VAZIA || !sigma.contains(String.valueOf(entrada)))
                throw new EntradaInvalidaException(entrada);

            Estado destino = getEstado(estados, componentesTransicao.get(DESTINO.ordinal()));

            String saida = componentesTransicao.get(SAIDA.ordinal());
            if (!saida.contains(String.valueOf(ESCAPE))) {
                for (int i = 0; i < saida.length(); i++)
                    if (saida.charAt(i) != PALAVRA_VAZIA && !delta.contains(String.valueOf(saida.charAt(i))))
                        throw new SaidaInvalidaException(saida);
            } else {
                saida = caractereEscape(saida);
            }

            origem.addTransicao(entrada, destino, saida);
        }

        arq.close();

        MaquinaMealy mealy = new MaquinaMealy(nomesEstados.size(), sigma, delta);
        for (Estado e : estados) {
            mealy.addEstado(e);
        }

        return mealy;
    }
}

//public MaquinaMealy criarMealy() throws FileNotFoundException, AutomatoException {
//
//    Scanner arq = new Scanner(new File(nomeArquivo));
//
//    String[] nomesEstados = arq.nextLine().split("\\S| +");
//    String nomeEstadoInicial = arq.nextLine();
//    String[] nomesEstadosFinais = arq.nextLine().split("\\S| +");
//
//    ArrayList<Estado> estados = new ArrayList<>(nomesEstados.length);
//
//    for (int i = 0; i < nomesEstados.length; i++) {
//
//        boolean inicial = false, terminal = false;
//        if (nomesEstados[i].equals(nomeEstadoInicial))
//            inicial = true;
//        else if (isEstadoFinal(nomesEstados[i], nomesEstadosFinais))
//            terminal = true;
//
//        estados.add(new Estado(nomesEstados[i], inicial, terminal));
//    }
//
//    ArrayList<String> sigma = new ArrayList<>(List.of(arq.nextLine().split("\\S| +")));
//    ArrayList<String> delta = new ArrayList<>(List.of(arq.nextLine().split("\\S| +")));
//
//    while (arq.hasNextLine()) {
//
//        String[] componentesTransicao = arq.nextLine().split(" ");
//
//        Estado origem = getEstado(estados, componentesTransicao[ORIGEM.ordinal()]);
//
//        Character entrada = componentesTransicao[ENTRADA.ordinal()].charAt(0);
//        if (!sigma.contains(String.valueOf(entrada))) throw new EntradaInvalidaException(entrada);
//
//        Estado destino = getEstado(estados, componentesTransicao[DESTINO.ordinal()]);
//
//        String saida;
//        if (componentesTransicao.length == 4) {
//            saida = componentesTransicao[SAIDA.ordinal()];
//            for (int i = 0; i < saida.length(); i++)
//                if (!delta.contains(String.valueOf(saida.charAt(i)))) throw new SaidaInvalidaException(saida);
//
//        } else {
//            saida = "";
//        }
//
//        origem.addTransicao(entrada, destino, saida);
//    }
//
//    arq.close();
//
//    MaquinaMealy mealy = new MaquinaMealy(nomesEstados.length, sigma, delta);
//    for (Estado e : estados) {
//        mealy.addEstado(e);
//    }
//
//    return mealy;
//}
