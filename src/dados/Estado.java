package dados;

import exceptions.NaoDeterministicoException;
import exceptions.TransicaoIndefinidaException;

import java.util.HashMap;
import java.util.Map;

public class Estado {

    private final String nome;
    private final boolean isInicial;
    private final boolean isFinal;
    private Map<Character, Estado> transicoes = new HashMap<>();
    private Map<Character, String> saidas = new HashMap<>();

    public Estado(String nome, boolean isInicial, boolean isFinal) {
        this.nome = nome;
        this.isInicial = isInicial;
        this.isFinal = isFinal;
    }

    public String getNome() {
        return nome;
    }

    public boolean isInicial() {
        return isInicial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void addTransicao(Character entrada, Estado e, String escrita) throws NaoDeterministicoException {

        if (transicoes.containsKey(entrada))
            throw new NaoDeterministicoException(this, e, entrada);

        transicoes.put(entrada, e);
        saidas.put(entrada, escrita);
    }

    public Estado computarEstado(Character entrada) throws TransicaoIndefinidaException {

        if (!transicoes.containsKey(entrada))
            throw new TransicaoIndefinidaException(this, entrada);

        return transicoes.get(entrada);
    }

    public String computarSaida(Character entrada) {
        return saidas.get(entrada);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Estado e)
            return e.getNome().equals(this.nome);
        return false;
    }
}
