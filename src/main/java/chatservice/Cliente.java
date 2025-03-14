package chatservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Clase Cliente que se conecta al servidor para enviar y recibir mensajes
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 1.0, 02/2025
 */
public class Cliente {

	private Socket socket;
	private BufferedReader entrada;
	private BufferedWriter salida;
	private String nombreUsuario;

	/**
	 * Constructor de la clase Cliente
	 * 
	 * @param socket        Socket por el que se conecta el cliente
	 * @param nombreUsuario Nombre de usuario del cliente
	 */
	public Cliente(Socket socket, String nombreUsuario) {
		try {
			this.socket = socket;
			this.nombreUsuario = nombreUsuario;
			this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (Exception e) {
			closeAll(socket, entrada, salida);
		}
	}

	/**
	 * Metodo para enviar mensajes. El mensaje se cifra antes de enviarlo. El
	 * mensaje se envia con el nombre del usuario que lo envia.
	 * 
	 * @throws Exception
	 */
	public void sendMessage() throws Exception {
		try {
			salida.write(nombreUsuario);
			salida.newLine();
			salida.flush();

			try (Scanner scanner = new Scanner(System.in)) {
				while (socket.isConnected()) {
					String mensajeEnviar = scanner.nextLine();
					String mensajeCifrado = AESUtil.encrypt(mensajeEnviar); // Cifrado del mensaje
					salida.write(nombreUsuario + ": " + mensajeCifrado);
					salida.flush();
				}
			}
		} catch (IOException e) {
			closeAll(socket, entrada, salida);
		}
	}

	/**
	 * Metodo para escuchar mensajes. El mensaje recibido se descifra antes de
	 * mostrarlo.
	 * 
	 * @throws Exception Si se produce un error.
	 */
	public void listenForMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String mensajeCifrado;
				while (socket.isConnected()) {
					try {
						mensajeCifrado = entrada.readLine();
						String mensajeDescifrado = AESUtil.decrypt(mensajeCifrado); // Descifrado del mensaje
						System.out.println(mensajeDescifrado);
					} catch (IOException e) {
						closeAll(socket, entrada, salida);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * Metodo para cerrar el socket, el buffer de entrada y el buffer de salida.
	 * 
	 * @param socket  Socket a cerrar.
	 * @param entrada Buffer de entrada a cerrar.
	 * @param salida  Buffer de salida a cerrar.
	 */
	public void closeAll(Socket socket, BufferedReader entrada, BufferedWriter salida) {
		try {
			if (entrada != null)
				entrada.close();
			if (salida != null)
				salida.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo principal de la clase Cliente.
	 * 
	 * @param args Argumentos de la linea de comandos.
	 * @throws Exception Si se produce un error.
	 */
	public static void main(String[] args) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Ingrese su nombre de usuario: ");
			String nombreUsuario = scanner.nextLine();

			Socket socket = new Socket("127.0.0.1", 5000);
			Cliente cliente = new Cliente(socket, nombreUsuario);
			cliente.listenForMessages();
			cliente.sendMessage();
		}
	}

} // Class
