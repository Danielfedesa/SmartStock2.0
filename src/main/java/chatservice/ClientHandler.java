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

    // Enviar los mensajes previos a este cliente
    private void sendPreviousMessages() {
        DaoChat daoMensaje = new DaoChat();
        List<Chat> mensajes = daoMensaje.listar();
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

    // Método para enviar el mensaje a todos los clientes conectados
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

    // Método para eliminar un cliente
    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVIDOR: " + nombreUsuarioCliente + " ha salido del chat");
    }

    // Método para guardar el mensaje en la base de datos
    private void saveMessage(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            System.out.println("No se guardó un mensaje vacío o nulo en la base de datos.");
            return;
        }

        if (!mensaje.startsWith("SERVIDOR:")) { // No guardar mensajes del sistema
            try {
                // Ciframos el mensaje
                String mensajeCifrado = AESUtil.encrypt(mensaje);

                // Guardamos el mensaje cifrado en la base de datos
                DaoChat daoMensaje = new DaoChat();
                Chat nuevoMensaje = new Chat(mensajeCifrado, nombreUsuarioCliente, java.time.LocalDateTime.now());
                daoMensaje.insertar(nuevoMensaje);
            } catch (Exception e) {
                System.err.println("Error al cifrar el mensaje: " + e.getMessage());
            }
        }
    }

    // Método para cerrar todos los flujos y el socket
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
}