package chatservice;

import controller.ChatController;
import model.Chat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de clientes para el chat.
 *
 * Esta clase gestiona la conexion de los clientes, la recepcion y envio de mensajes
 * y la gestion de la base de datos a traves del {@link ChatController}.
 * 
 * Cada cliente conectado tiene su propia instancia de esta clase ejecutandose en un hilo separado.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 03/2025
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
     * Inicializa la conexion del cliente, lee su nombre de usuario, lo agrega a la lista de clientes
     * y envia los mensajes previos almacenados en la base de datos.
     *
     * @param socket         El socket asociado a la conexion del cliente.
     * @param chatController Controlador que gestiona los mensajes del chat.
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

    /**
     * Metodo que se ejecuta en un hilo separado cuando un cliente se conecta.
     * 
     * Escucha continuamente mensajes entrantes del cliente y los retransmite a los demas clientes conectados.
     * Si el cliente se desconecta, se elimina de la lista de clientes activos.
     * 
     */
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

    /**
     * Recupera los mensajes previos almacenados en la base de datos y los envia al cliente actual.
     */
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

    /**
     * Envia un mensaje a todos los clientes conectados, excepto al cliente que lo envio originalmente.
     *
     * @param mensajeParaEnviar Mensaje que sera enviado a los clientes conectados.
     */
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

    /**
     * Elimina al cliente actual de la lista de clientes conectados
     * y notifica a los demas clientes que ha salido del chat.
     */
    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + " ha salido del chat");
    }

    /**
     * Guarda un mensaje en la base de datos si no esta vacio ni es un mensaje del sistema.
     *
     * @param mensaje Mensaje a guardar en la base de datos.
     */
    private void saveMessage(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty() || mensaje.startsWith("SERVIDOR:")) {
            return;
        }
        chatController.enviarMensaje(mensaje, nombreUsuarioCliente);
    }

    /**
     * Cierra todos los recursos asociados al cliente, incluyendo flujos de entrada y salida,
     * asi como el socket de conexion.
     */
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
