package controller;

import model.Chat;
import service.ChatService;

import java.util.List;

/**
 * Controlador para gestionar los mensajes del chat dentro del sistema.
 * 
 * Esta clase actua como intermediario entre la vista y la capa de servicio, 
 * delegando las operaciones CRUD a {@link ChatService}.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class ChatController {

    private final ChatService chatService;

    public ChatController() {
        this.chatService = new ChatService();
    }

    /**
     * Envia un mensaje cifrado a la base de datos.
     *
     * @param contenido Mensaje en texto plano.
     * @param usuario   Usuario que envia el mensaje.
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
