package chatservice;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Clase AESUtil que contiene los metodos para cifrar y descifrar mensajes
 * mediante el algoritmo AES.
 * 
 *  @author Daniel Fernandez Sanchez.
 * @version 1.0 02/2025
 */
public class AESUtil {

	private static final String ALGORITHM = "AES";
	private static final String SECRET_KEY = "0123456789abcdef"; // Usa una clave fija para el ejemplo

	/**
	 * Cifra el mensaje que se pasa como parametro.
	 * 
	 * @param mensaje Mensaje a cifrar.
	 * @return Mensaje cifrado en base64.
	 * @throws Exception Si ocurre algun error al cifrar el mensaje.
	 */
	// Cifrar el mensaje
	public static String encrypt(String mensaje) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encrypted = cipher.doFinal(mensaje.getBytes());
		return Base64.getEncoder().encodeToString(encrypted); // Convierte el mensaje cifrado a base64
	}

	/**
	 * Descifra el mensaje que se pasa como parametro.
	 * 
	 * @param mensajeCifrado Mensaje cifrado en base64.
	 * @return Mensaje descifrado.
	 * @throws Exception Si ocurre algun error al descifrar el mensaje.
	 */
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
