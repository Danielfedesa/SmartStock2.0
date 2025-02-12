package chatservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serverSocket;
	
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		try {
			while (!serverSocket.isClosed()) {
                // Aceptar conexiones de clientes
				// El método accept() bloquea la ejecución hasta que se conecta un cliente
				Socket socket = serverSocket.accept();
				System.out.println("Nuevo cliente conectado " + socket.getInetAddress());
				ClientHandler clientHandler = new ClientHandler(socket);
				
				// Inicia un hilo para manejar la conexión con el cliente
				Thread thread = new Thread(clientHandler);
				thread.start();
            }
		} catch (IOException e) {
			System.err.println("Error dentro del servidor de chat: " + e.getMessage());
			closeServerSocket();
		}
	}
	
	public void closeServerSocket() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			System.err.println("Error al cerrar el socket del servidor: " + e.getMessage());
		}
	}
	
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
