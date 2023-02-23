package br.com.alura.tarefas;

import br.com.alura.servidor.ServidorTarefas;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {
    private final ExecutorService theadPool;
    private Socket socket;
    private ServidorTarefas servidor;

    private BlockingQueue<String> filaComandos;

    public DistribuirTarefas(Socket socket, ServidorTarefas servidor, ExecutorService threadPool, BlockingQueue<String> filaComandos) {
        this.socket = socket;
        this.servidor = servidor;
        this.theadPool = threadPool;
        this.filaComandos = filaComandos;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo as tarefas para o cliente " + socket);

            Scanner entradaCliente = new Scanner(socket.getInputStream());
            PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

            while (entradaCliente.hasNextLine()) {
                String comando = entradaCliente.nextLine();
                System.out.println("Comando recebido " + comando);

                switch (comando) {

                    case "c1": {
                        saidaCliente.println("Confirmação comando c1");
                        ComandoC1 comandoC1 = new ComandoC1(saidaCliente);
                        this.theadPool.execute(comandoC1);
                        break;
                    }
                    case "c2": {
                        saidaCliente.println("Confirmação comando c2");
                        ComandoC2ChamaWS c2WS = new ComandoC2ChamaWS(saidaCliente);
                        ComandoC2AcessaBanco c2Banco = new ComandoC2AcessaBanco(saidaCliente);
                        Future<String> futureWS = this.theadPool.submit(c2WS);
                        Future<String> furureBanco = this.theadPool.submit(c2Banco);

                        this.theadPool.submit(new JuntaResultadosFutureWSAndFutureBanco(futureWS, furureBanco, saidaCliente));
                        String resultadoWS = futureWS.get();
                        break;
                    }
                    case "c3": {
                        this.filaComandos.put(comando); // bloqueia
                        saidaCliente.println("Comando c3 adicionando na fila");
                        servidor.parar();
                        break;
                    }
                    case "fim": {
                        saidaCliente.println("Desligando o servidor");
                        servidor.parar();
                        break;
                    }
                    default: {
                        saidaCliente.println("Comando não encontrado");
                        break;
                    }
                }
                System.out.println(comando);
            }

            entradaCliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
