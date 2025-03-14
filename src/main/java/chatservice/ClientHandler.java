package chatservice;

import controller.ChatController;
import model.Chat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de clientes para el chat.
 */
public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader entrada;
    private BufferedWriter salida;
    private String nombreUsuarioCliente;
    private ChatController chatController;

    /**
     * Constructor de ClientHandler.
     *
     * @param socket El socket asociado a la conexión del cliente.
     */
    public ClientHandler(Socket socket, ChatController chatController) {
        this.chatController = chatController;
		try {
            this.socket = socket;
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.nombreUsuarioCliente = entrada.readLine();
            this.chatController = new ChatController();
            clientHandlers.add(this);

            broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + " ha entrado al chat");

            sendPreviousMessages();
        } catch (IOException e) {
            closeAll();
        }
    }

    @Override
    public void run() {
        String mensajeCifrado;
        while (socket.isConnected()) {
            try {
                mensajeCifrado = entrada.readLine();
                if (mensajeCifrado == null) {
                    removeClientHandler();
                    break;
                }

                saveMessage(mensajeCifrado);
                broadcastMessage(mensajeCifrado);
            } catch (IOException e) {
                closeAll();
                break;
            }
        }
    }

    private void sendPreviousMessages() {
        List<Chat> mensajes = chatController.obtenerMensajes();
        for (Chat mensaje : mensajes) {
            try {
                String mensajeFormateado = mensaje.getUsuario() + ": " + mensaje.getContenido();
                salida.write(mensajeFormateado);
                salida.newLine();
                salida.flush();
            } catch (Exception e) {
                closeAll();
            }
        }
    }

    public void broadcastMessage(String mensajeParaEnviar) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.nombreUsuarioCliente.equals(nombreUsuarioCliente)) {
                    clientHandler.salida.write(mensajeParaEnviar);
                    clientHandler.salida.newLine();
                    clientHandler.salida.flush();
                }
            } catch (Exception e) {
                closeAll();
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + " ha salido del chat");
    }

    private void saveMessage(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty() || mensaje.startsWith("SERVIDOR:")) {
            return;
        }
        chatController.enviarMensaje(mensaje, nombreUsuarioCliente);
    }

    public void closeAll() {
        if (clientHandlers.contains(this)) {
            removeClientHandler();
        }
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
        }
    }
}
