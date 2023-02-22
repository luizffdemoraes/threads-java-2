package br.com.alura.test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorDeTeste {

    private volatile boolean estaRodando = false;
    // private AtomicBoolean estaRodando;

    public static void main(String[] args) throws InterruptedException {
        ServidorDeTeste servidor = new ServidorDeTeste();
        servidor.rodar();
        servidor.alterandoAtributo();
    }

    private void rodar() {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                System.out.println("Servidor começando, estaRodando = " + estaRodando);

                // enquanto estiver false, fica no laço
                while (!estaRodando) {
                }

                if (estaRodando) {
                    throw new RuntimeException("Deu erro na thread ....");
                }

                System.out.println("Servidor rodando, estaRodando = " + estaRodando);

                // enquanto estiver true, fica no laço
                while (estaRodando) {
                }

                System.out.println("Servidor terminando, estaRodando = " + estaRodando);
            }
        });

        thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
        thread.start();
    }

    private void alterandoAtributo() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando = true");
        estaRodando = true;

        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando = false");
        estaRodando = false;
    }
}

