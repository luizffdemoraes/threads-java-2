package br.com.alura.tarefas;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {

    private final PrintStream saida;

    public ComandoC2ChamaWS(PrintStream saida) {
        this.saida = saida;
    }

    @Override
    public String call() throws Exception {
        saida.println("Servidor recebeu comando c2 - WS");
        saida.println("Executando comando C2 - WS");

        Thread.sleep(15000);

        int numero = new Random().nextInt(100) + 1;

        saida.println("Servidor finalizou comando c2 - WS");
        return Integer.toString(numero);
    }
}
