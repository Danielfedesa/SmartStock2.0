package controller;

import model.Chat;
import service.ChatService;

import java.util.List;

/**
 * Controlador que maneja la lógica de los mensajes de chat.
 */
public class ChatController {

    private final ChatService chatService;

    public ChatController() {
        this.chatService = new ChatService();
    }

    /**
     * Envía un mensaje cifrado a la base de datos.
     *
     * @param contenido Mensaje en texto plano.
     * @param usuario   Usuario que envía el mensaje.
     */
    public void enviarMensaje(String contenido, String usuario) {
        chatService.agregarMensaje(contenido, usuario);
    }

    /**
     * Obtiene los mensajes descifrados desde el servicio.
     *
     * @return Lista de mensajes descifrados.
     */
    public List<Chat> obtenerMensajes() {
        return chatService.obtenerMensajes();
    }
}
