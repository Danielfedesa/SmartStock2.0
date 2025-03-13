package chatservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import controller.ChatController;
import model.Chat;

/**
 * Clase ClientHandler que representa el hilo de un cliente conectado al chat.
 * En este hilo gestionara la recepcion y envio de mensajes, asi como la
 * gestion de la base de datos.
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 2.0 03/2025
 */
public class ClientHandler implements Runnable {
	
    // Lista de clientes conectados al chat
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader entrada;
    private BufferedWriter salida;
    private String nombreUsuarioCliente;
    private final ChatController chatController;

    /**
     * Manejador de clientes para una conexion de socket. 
     * Este constructor inicializa la conexión con el cliente, lee su nombre de usuario, 
     * lo agrega a la lista de clientes y envia mensajes de bienvenida.
     *
     * @param socket El socket asociado a la conexion del cliente.
     */
    public ClientHandler(Socket socket) {
    	
    	this.chatController = new ChatController();
    	
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
            
        } catch (IOException e) {
            closeAll(socket, entrada, salida);
        }
    }

    /**
     * Metodo que se ejecuta cuando el hilo del cliente inicia.
     * Se encarga de recibir mensajes cifrados, almacenarlos y retransmitirlos a otros clientes.
     * Si el cliente se desconecta, se elimina de la lista de manejadores.
     */
    @Override
    public void run() {
        String mensajeCifrado;
        while (socket.isConnected()) {
            try {
                mensajeCifrado = entrada.readLine();
                if (mensajeCifrado == null) { // Si es null, el cliente se ha desconectado
                    removeClientHandler();
                    break;
                }

                saveMessage(mensajeCifrado); // Guardamos el mensaje cifrado
                broadcastMessage(mensajeCifrado); // Enviamos el mensaje a otros clientes
                
            } catch (IOException e) {
                closeAll(socket, entrada, salida);
                break;
            }
        }
    }

    /**
     * Envia al cliente los mensajes previos almacenados en la base de datos.
     * Recupera los mensajes, los descifra y los formatea antes de enviarlos a la salida del cliente.
     */
    private void sendPreviousMessages() {
        List<Chat> mensajes = chatController.listarMensajes(); // Obtener mensajes desde el controlador.
        
        for (Chat mensaje : mensajes) {
            try {
                // Decodificamos y desciframos el mensaje antes de enviarlo
                String mensajeDescifrado = AESUtil.decrypt(mensaje.getContenido());
                String mensajeFormateado = mensaje.getUsuario() + ": " + mensajeDescifrado;
                salida.write(mensajeFormateado);
                salida.newLine();
                salida.flush();
            } catch (Exception e) {
                closeAll(socket, entrada, salida);
            }
        }
    }

    /**
     * Envia un mensaje a todos los clientes conectados, excepto al que lo envio originalmente.
     * El mensaje no se cifra nuevamente antes de enviarlo.
     *
     * @param mensajeParaEnviar Mensaje que sera enviado a los clientes conectados.
     */
    public void broadcastMessage(String mensajeParaEnviar) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // Enviamos el mensaje tal cual, no lo ciframos otra vez
                if (!clientHandler.nombreUsuarioCliente.equals(nombreUsuarioCliente)) {
                    clientHandler.salida.write(mensajeParaEnviar); // Enviar el mensaje
                    clientHandler.salida.newLine();
                    clientHandler.salida.flush();
                }
            } catch (Exception e) {
                closeAll(clientHandler.socket, clientHandler.entrada, clientHandler.salida);
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
     * Guarda un mensaje en la base de datos despues de cifrarlo.
     * No guarda mensajes vacios, nulos ni mensajes del sistema (que empiezan por "SERVIDOR:")
     * para evitar guardar mensajes de entrada y salida del chat.
     *
     * @param mensaje Mensaje a guardar en la base de datos.
     */
    private void saveMessage(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            System.out.println("No se guardó un mensaje vacío o nulo en la base de datos.");
            return;
        }

        if (!mensaje.startsWith("SERVIDOR:")) { // No guardar mensajes del sistema
            try {
                String mensajeCifrado = AESUtil.encrypt(mensaje);
                chatController.agregarMensaje(nombreUsuarioCliente, mensajeCifrado); // Se delega al controlador.
            } catch (Exception e) {
                System.err.println("Error al cifrar el mensaje: " + e.getMessage());
            }
        }
    }

    /**
     * Cierra todos los flujos de entrada, salida y el socket del cliente.
     * Ademas, si el cliente aun esta en la lista de clientes conectados, lo elimina.
     *
     * @param socket  Socket asociado a la conexion del cliente.
     * @param entrada BufferedReader utilizado para la entrada de datos.
     * @param salida  BufferedWriter utilizado para la salida de datos.
     */
    public void closeAll(Socket socket, BufferedReader entrada, BufferedWriter salida) {
        if (clientHandlers.contains(this)) {
            removeClientHandler();
        }
        try {
            if (entrada != null) {
                entrada.close();
            }
            if (salida != null) {
                salida.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
        }
    }
    
} // Class