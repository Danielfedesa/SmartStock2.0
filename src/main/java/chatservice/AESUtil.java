package chatservice;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    // La clave debe ser de 16 bytes (128 bits) para AES
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "0123456789abcdef"; // Usa una clave fija para el ejemplo

    // Cifrar el mensaje
    public static String encrypt(String mensaje) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(mensaje.getBytes());
        return Base64.getEncoder().encodeToString(encrypted); // Convierte el mensaje cifrado a base64
    }

    // Descifrar el mensaje
    public static String decrypt(String mensajeCifrado) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedMessage = Base64.getDecoder().decode(mensajeCifrado); // Decodifica de base64
        byte[] decrypted = cipher.doFinal(decodedMessage);
        return new String(decrypted); // Devuelve el mensaje original
    }
}
