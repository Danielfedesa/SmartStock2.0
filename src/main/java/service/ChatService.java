package service;

import model.Chat;
import repository.ChatRepository;
import chatservice.AESUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones de la entidad Chat.
 * 
 * Esta clase actua como intermediario entre el controlador y el repositorio,
 * aplicando reglas de negocio antes de interactuar con la base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService() {
        this.chatRepository = new ChatRepository();
    }

    /**
     * Agrega un mensaje cifrado a la base de datos.
     *
     * @param contenido Mensaje en texto plano.
     * @param usuario   Usuario que envía el mensaje.
     */
    public void agregarMensaje(String contenido, String usuario) {
        try {
            String mensajeCifrado = AESUtil.encrypt(contenido);
            Chat mensaje = new Chat(mensajeCifrado, usuario, LocalDateTime.now());
            chatRepository.insertar(mensaje);
        } catch (Exception e) {
            System.err.println("Error al cifrar el mensaje: " + e.getMessage());
        }
    }

    /**
     * Obtiene y descifra los mensajes almacenados en la base de datos.
     *
     * @return Lista de mensajes descifrados.
     */
    public List<Chat> obtenerMensajes() {
        return chatRepository.listar().stream().map(mensaje -> {
            try {
                String mensajeDescifrado = AESUtil.decrypt(mensaje.getContenido());
                return new Chat(mensaje.getId(), mensajeDescifrado, mensaje.getUsuario(), mensaje.getFecha());
            } catch (Exception e) {
                System.err.println("Error al descifrar mensaje: " + e.getMessage());
                return mensaje; // Si falla, devuelve el mensaje cifrado
            }
        }).collect(Collectors.toList());
    }
}
