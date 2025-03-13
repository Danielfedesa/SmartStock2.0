package controller;

import java.util.List;

import model.Chat;
import service.ChatService;

public class ChatController {
    
    private final ChatService chatService;

    public ChatController() {
        this.chatService = new ChatService();
    }

    public void agregarMensaje(String usuario, String contenido) {
        chatService.agregarMensaje(usuario, contenido);
    }

    public List<Chat> listarMensajes() {
        return chatService.listarMensajes();
    }
}