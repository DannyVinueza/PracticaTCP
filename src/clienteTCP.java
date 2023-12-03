import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clienteTCP {
    public static void main(String[] args) {
        try {
            Socket socket_cliente = new Socket("localhost", 1234);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

            String pregunta;
            String respuesta;
            while (true) {
                // Leer la pregunta enviada por el servidor
                pregunta = entrada.readLine();
                if (pregunta.equals("Ha respondido todas las preguntas")) {
                    // Si el servidor envía este mensaje, se termina la interacción
                    System.out.println(pregunta);
                    break;
                }
                System.out.println("Servidor: " + pregunta);

                // Enviar la respuesta al servidor
                System.out.print("Respuesta: ");
                respuesta = lector.readLine();
                salida.println(respuesta);

                // Recibir y mostrar la retroalimentación del servidor
                String retroalimentacion = entrada.readLine();
                System.out.println("Servidor: " + retroalimentacion);
            }

            // Recibir y mostrar la puntuación final
            String puntuacionFinal = entrada.readLine();
            System.out.println(puntuacionFinal);

            // Cerrar la conexión con el servidor
            socket_cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}