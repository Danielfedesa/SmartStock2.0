package process;

import org.mindrot.jbcrypt.BCrypt;

public class TestHash {
    public static void main(String[] args) {
        String contrasena = "1234"; // La contraseña introducida
        String hash = "$2a$10$gYFcUxfD8/pPQW5fV3ARGuiMp/TmAHlK0PRnrH6CBzHXP9ObS9ERS"; // El hash de la base de datos

        boolean contrasenaValida = BCrypt.checkpw(contrasena, hash);
        System.out.println("¿Las contraseñas coinciden? " + contrasenaValida);  // Esto debe devolver "true"
    }
}
