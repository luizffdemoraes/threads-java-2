package br.com.alura.tarefas;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {

    private final PrintStream saida;

    public ComandoC2AcessaBanco(PrintStream saida) {
        this.saida = saida;
    }

    @Override
    public String call() throws Exception {
        saida.println("Servidor recebeu comando c2 - Banco");
        saida.println("Executando comando C2 - Banco");

        Thread.sleep(15000);

        int numero = new Random().nextInt(100) + 1;

        saida.println("Servidor finalizou comando c2 - Banco");
        return Integer.toString(numero);
    }
}
