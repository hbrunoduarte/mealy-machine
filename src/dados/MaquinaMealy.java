package dados;

import exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class MaquinaMealy {

    private final ArrayList<String> sigma;
    private final ArrayList<String> delta;

    private final List<Estado> estados;
    private final int qtdEstados;

    private Estado inicial = null;

    private String ultimaSaida = null;

    public MaquinaMealy(int qtdEstados, ArrayList<String> sigma, ArrayList<String> delta) {
        this.qtdEstados = qtdEstados;
        this.sigma = sigma;
        this.delta = delta;
        estados = new ArrayList<>(qtdEstados);
    }

    public void addEstado(Estado e) throws AutomatoException {
        if (e == null) throw new EstadoNuloException();
        if (estados.size() == qtdEstados) throw new QuantidadeMaximaEstadosException();
        estados.add(e);

        if (e.isInicial()) {
            if (inicial == null)
                inicial = e;
            else
                throw new NaoDeterministicoException();
        }
    }

    public boolean computarPalavra(String entrada){

        StringBuilder s = new StringBuilder();
        Estado estadoAtual = inicial;
        for (int i = 0; i < entrada.length(); i++) {
            s.append(estadoAtual.computarSaida(entrada.charAt(i)));

            try {
                estadoAtual = estadoAtual.computarEstado(entrada.charAt(i));
            } catch (TransicaoIndefinidaException e) {
                ultimaSaida = null;
                System.out.println(e.getMessage());
                return false;
            }
        }

        if (estadoAtual.isFinal()) {
            ultimaSaida = s.toString();
            return true;
        }

        ultimaSaida = null;
        return false;
    }

    public String getSaida() {
        return ultimaSaida;
    }
}
