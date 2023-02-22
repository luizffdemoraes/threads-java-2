package br.com.alura.tarefas;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {
    private final PrintStream saida;

    public ComandoC1(PrintStream saida) {
        this.saida = saida;
    }

    @Override
    public void run() {
        System.out.println("Executando comando C1");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        saida.println("Comando c1 executado com sucesso!");
    }
}
