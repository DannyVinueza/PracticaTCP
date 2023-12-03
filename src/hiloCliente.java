import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class hiloCliente extends Thread {
    private Socket socket_cliente;
    private List<String[]> preguntas;
    private int puntuacion;
    public hiloCliente(Socket socket_cliente) {
        this.socket_cliente = socket_cliente;
        this.preguntas = new ArrayList<>();
        this.puntuacion = 0;
        preguntas.add(new String[]{"¿Cuanto es 1+1?", "2", "10"});
        preguntas.add(new String[]{"¿Cuanto es 1+2?", "3", "15"});
        preguntas.add(new String[]{"¿Cuanto es 1+3?", "4", "12"});
        preguntas.add(new String[]{"¿Cuanto es 1+4?", "5", "11"});
        preguntas.add(new String[]{"¿Cuanto es 1+5?", "6", "13"});
        preguntas.add(new String[]{"¿Cuanto es 1+6?", "7", "10"});
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);
            BufferedReader lectorServidor = new BufferedReader(new InputStreamReader(System.in));

            for (String[] prg : preguntas) {
                String pregunta = prg[0];
                String respuestaCorrecta = prg[1];

                // Enviar pregunta al cliente
                salida.println(pregunta);

                // Recibir respuesta del cliente
                String respuestaCliente = entrada.readLine();
                System.out.println("Respuesta de " + socket_cliente.getInetAddress().getHostAddress() + ": " + respuestaCliente);

                // Verificar respuesta y enviar retroalimentación
                if (respuestaCliente.equalsIgnoreCase(respuestaCorrecta)) {
                    salida.println("Respuesta correcta");
                    puntuacion += Integer.parseInt(prg[2]); // Incrementar puntuación si es correcta
                } else {
                    salida.println("Respuesta incorrecta. La respuesta correcta es: " + respuestaCorrecta);
                }
            }

            // Enviar mensaje de finalización y puntuación al cliente
            salida.println("Ha respondido todas las preguntas");
            salida.println("Su puntuación final es: " + puntuacion);

            // Cerrar la conexión con el cliente
            socket_cliente.close();
        } catch (IOException e) {
            if (e instanceof SocketException) {
                // Si es una excepción de Socket, el cliente se desconectó inesperadamente
                System.out.println("Cliente desconectado");
            } else {
                e.printStackTrace();
            }
        }finally {
            try {
                // Cerrar la conexión con el cliente
                socket_cliente.close();
                System.out.println("Conexión cerrada desde el cliente");
                System.out.println("---------------------------------");
                System.out.println("Esperando Conexiones...");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}