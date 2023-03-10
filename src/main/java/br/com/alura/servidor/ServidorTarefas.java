package br.com.alura.servidor;

import br.com.alura.tarefas.DistribuirTarefas;
import br.com.alura.tarefas.TarefasConsumir;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

    private ServerSocket servidor;
    private ExecutorService threadPool;
    private AtomicBoolean estaRodando;
    private BlockingQueue<String> filaComandos;
    // private volatile boolean estaRodando

    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando servidor ---");
        this.servidor = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads()); // Executors.newFixedThreadPool(4, new FabricaDeThreads()); //Executors.newFixedThreadPool(4); // Executors.newCachedThreadPool();
        this.estaRodando = new AtomicBoolean(true);
        this.filaComandos = new ArrayBlockingQueue<>(2);
        iniciarConsumidores();
    }

    // na classe ServidorTarefas
    private void iniciarConsumidores() {
        int qtdCondumidores = 2;
        for (int i = 0; i < qtdCondumidores; i++) {
            TarefasConsumir tarefa = new TarefasConsumir(filaComandos);
            this.threadPool.execute(tarefa);
        }
    }

    public static void main(String[] args) throws Exception {

        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
        servidor.parar();
    }

    public void parar() throws IOException {
        estaRodando = new AtomicBoolean(false);
        servidor.close();
        threadPool.shutdown();
        System.exit(0);
    }

    public void rodar() throws IOException {
        while (this.estaRodando.get()) {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this, threadPool, filaComandos);
                threadPool.execute(distribuirTarefas);
            } catch (SocketException e) {
                e.printStackTrace();
                System.out.println("SocketException, Est?? rodando? " + this.estaRodando);
            }
        }
    }
}
