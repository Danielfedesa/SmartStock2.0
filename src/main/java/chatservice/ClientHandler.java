package chatservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {


	public static List<ClientHandler> clientHandlers = new ArrayList<>();

	private Socket socket;

	private BufferedReader entrada;

	private BufferedWriter salida;

	private String nombreUsuarioCliente;

	public ClientHandler(Socket socket) {
        try {
        	this.socket = socket;
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.nombreUsuarioCliente = entrada.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + "ha entrado al chat");
            } catch (IOException e) {
            	closeAll(socket, entrada, salida);
            }
	}


	@Override
	public void run() {
		String mensajeCifrado;
		while (socket.isConnected()) {
            try {
            	mensajeCifrado = entrada.readLine();
                broadcastMessage(mensajeCifrado);
            } catch (IOException e) {
                closeAll(socket, entrada, salida);
                break;
            }
		}

	}
	
	public void broadcastMessage(String mensajeParaEnviar) {
		for (ClientHandler clientHandler : clientHandlers) {
			try {
				if (!clientHandler.nombreUsuarioCliente.equals(nombreUsuarioCliente)) {
                    String mensajeDescifrado = AESUtil.decrypt(mensajeParaEnviar); // Descifrado del mensaje antes de enviarlo
                    String mensajeCifrado = AESUtil.encrypt(mensajeDescifrado); // Cifrado del mensaje antes de enviarlo
                    clientHandler.salida.write(mensajeCifrado);
                    clientHandler.salida.newLine();
                    clientHandler.salida.flush();
                }
            } catch (Exception e) {
                closeAll(clientHandler.socket, clientHandler.entrada, clientHandler.salida);
            }
        }
    }
	
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + " ha salido del chat");
	}
	
	public void closeAll(Socket socket, BufferedReader entrada, BufferedWriter salida) {
		removeClientHandler();
		try {
			if (entrada != null) {
				entrada.close();
			}
			if (salida != null) {
				salida.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
		}
	}
	

} // Class