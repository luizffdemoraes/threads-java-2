package br.com.alura.cliente;

import java.io.IOException;
import java.net.Socket;

public class ClienteTarefas {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 12345);

        System.out.println("conexao estabelecida");

        socket.close();
    }
}
