package chatservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase que representa el servidor del chat. Se encarga de aceptar conexiones
 * de clientes y manejarlas mediante hilos.
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 1.0 02/2025
 */
public class Server {

	private ServerSocket serverSocket;

	/**
	 * Constructor de la clase Server.
	 *
	 * @param serverSocket Socket del servidor que escucha las conexiones entrantes.
	 */
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * Inicia el servidor y acepta conexiones de clientes. Cada cliente que se
	 * conecta es manejado en un hilo separado.
	 * 
	 * @throws IOException Si ocurre un error al aceptar conexiones de clientes.
	 */
	public void startServer() {
		try {
			while (!serverSocket.isClosed()) {
				// Aceptar conexiones de clientes
				// El método accept() bloquea la ejecución hasta que se conecta un cliente
				Socket socket = serverSocket.accept();
				System.out.println("Nuevo cliente conectado " + socket.getInetAddress());
				ClientHandler clientHandler = new ClientHandler(socket, null);

				// Inicia un hilo para manejar la conexión con el cliente
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			System.err.println("Error dentro del servidor de chat: " + e.getMessage());
			closeServerSocket();
		}
	}

	/**
	 * Cierra el socket del servidor, deteniendo la recepcion de nuevas conexiones.
	 */
	public void closeServerSocket() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			System.err.println("Error al cerrar el socket del servidor: " + e.getMessage());
		}
	}

	/**
	 * Metodo principal que inicia el servidor en el puerto 5000. Se encarga de
	 * aceptar conexiones de clientes y manejarlas mediante hilos.
	 * 
	 * Se instancia desde la clase App para iniciar el servidor.
	 *
	 * @param args Argumentos de linea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			System.out.println("Servidor de chat iniciado en puerto " + serverSocket.getLocalPort());
			Server server = new Server(serverSocket);
			server.startServer();
		} catch (IOException e) {
			System.err.println("Error al iniciar el servidor de chat: " + e.getMessage());
		}
	}

} // Class
