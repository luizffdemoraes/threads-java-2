package br.com.alura.tarefas;

import java.util.concurrent.BlockingQueue;

public class TarefasConsumir implements Runnable {

    private BlockingQueue<String> filaComandos;

    public TarefasConsumir(BlockingQueue<String> filaComandos) {
        this.filaComandos = filaComandos;
    }

    @Override
    public void run() {

        try {
            String comando = null;

            //enquanto existe um novo comando, lembrando take() bloqueia
            while ((comando = filaComandos.take()) != null) {
                System.out.println("Consumindo comando " + comando + ", " + Thread.currentThread().getName());
                Thread.sleep(20000); //demorando 20s para consumir
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
