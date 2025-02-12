package chatservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import DAO.DaoChat;
import model.Chat;

public class ClientHandler implements Runnable {


	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

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
            
         // Enviar mensaje de bienvenida
            broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + " ha entrado al chat");

            // Enviar mensajes previos a este cliente
            sendPreviousMessages();
            
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
            	saveMessage(mensajeCifrado);
                broadcastMessage(mensajeCifrado);
            } catch (IOException e) {
                closeAll(socket, entrada, salida);
                break;
            }
		}

	}
	
	private void sendPreviousMessages() {
	    DaoChat daoMensaje = new DaoChat();
	    List<Chat> mensajes = daoMensaje.listar();
	    for (Chat mensaje : mensajes) {
	        try {
	            String mensajeFormateado = mensaje.getUsuario() + ": " + mensaje.getContenido();  // Corregido
	            salida.write(mensajeFormateado);
	            salida.newLine();
	            salida.flush();
	        } catch (IOException e) {
	            closeAll(socket, entrada, salida);
	        }
	    }
	}
	
	public void broadcastMessage(String mensajeParaEnviar) {
	    for (ClientHandler clientHandler : clientHandlers) {
	        try {
	            if (!clientHandler.nombreUsuarioCliente.equals(nombreUsuarioCliente)) {
	                // Aquí no es necesario cifrar de nuevo el mensaje si ya lo has cifrado previamente
	                clientHandler.salida.write(mensajeParaEnviar); // Enviar el mensaje tal cual
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
	
	// Método para guardar los mensajes en la base de datos
    private void saveMessage(String mensaje) {
        DaoChat daoMensaje = new DaoChat();
        Chat nuevoMensaje = new Chat(mensaje, nombreUsuarioCliente, java.time.LocalDateTime.now());
        daoMensaje.insertar(nuevoMensaje);
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