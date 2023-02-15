package br.com.alura.tarefas;

import br.com.alura.servidor.ServidorTarefas;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {
    private Socket socket;
    private ServidorTarefas servidor;

    public DistribuirTarefas(Socket socket, ServidorTarefas servidor) {
        this.socket = socket;
        this.servidor = servidor;
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
                        break;
                    }
                    case "c2": {
                        saidaCliente.println("Confirmação comando c2");
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
