package br.com.alura.tarefas;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadosFutureWSAndFutureBanco implements Callable<Void> {
    private Future<String> futureWS;
    private Future<String> furureBanco;
    private PrintStream saidaCliente;

    public JuntaResultadosFutureWSAndFutureBanco(Future<String> futureWS, Future<String> furureBanco, PrintStream saidaCliente) {
        this.futureWS = futureWS;
        this.furureBanco = furureBanco;
        this.saidaCliente = saidaCliente;
    }

    @Override
    public Void call() throws Exception {
        System.out.println("Aguardando resultado do future WS e Banco");

        try {
            String numeroMagico = this.futureWS.get(15, TimeUnit.SECONDS);
            String numeroMagico2 = this.furureBanco.get(15, TimeUnit.SECONDS);
            this.saidaCliente.println("Resultado comando c2 : " + numeroMagico + " , " + numeroMagico2);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Timeout cancelando excecucao do comando c2");
            this.saidaCliente.println("Timeout na execucao do comando c2");
            this.futureWS.cancel(true);
            this.furureBanco.cancel(true);
        }

        System.out.println("Finalizou JuntaResultadosFutureWSAndFutureBanco");
        return null;

    }
}
