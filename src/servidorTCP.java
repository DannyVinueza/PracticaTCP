import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidorTCP {
    public static void main(String[] args) {
        // Crear Socket del Servidor

        try {
            ServerSocket socket = new ServerSocket(1234);
            System.out.println("Esperando Conexiones...");

            while (true){
                //Esperar y aceptar conexiones de clientes
                Socket socket_cliente = socket.accept();
                System.out.println("Cliente Conectado: " + socket_cliente.getInetAddress().getHostAddress());

                //Crear un hilo para manejar la conexion con el cliente
                hiloCliente hilo = new hiloCliente(socket_cliente);
                hilo.start();
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}