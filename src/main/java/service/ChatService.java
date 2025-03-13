package service;

import java.time.LocalDateTime;
import java.util.List;

import DAO.DaoChat;
import model.Chat;

public class ChatService {
	
    private DaoChat daoChat;

    public ChatService() {
        this.daoChat = new DaoChat();
    }

    public void agregarMensaje(String usuario, String contenido) {
        if (usuario == null || usuario.isBlank() || contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException("Usuario y mensaje no pueden estar vacíos.");
        }
        Chat mensaje = new Chat(usuario, contenido, LocalDateTime.now());
        daoChat.insertar(mensaje);
    }

    public List<Chat> listarMensajes() {
        return daoChat.listar();
    }
}