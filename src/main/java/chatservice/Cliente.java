package chatservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


public class Cliente {

	private Socket socket;
	private BufferedReader entrada;
	private BufferedWriter salida;
	private String nombreUsuario;

	public Cliente(Socket socket, String nombreUsuario) {
		try {
			this.socket = socket;
			this.nombreUsuario = nombreUsuario;
			this.entrada = entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.salida = salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (Exception e) {
			closeAll(socket, entrada, salida);
		}
	}

	public void sendMessage() throws Exception {
		try {
			salida.write(nombreUsuario);
			salida.newLine();
			salida.flush();

			Scanner scanner = new Scanner(System.in);
			while (socket.isConnected()) {
				String mensajeEnviar = scanner.nextLine();
				String mensajeCifrado = AESUtil.encrypt(mensajeEnviar); // Cifrado del mensaje
                salida.write(nombreUsuario + ": " + mensajeCifrado);
				salida.flush();
			}
		} catch (IOException e) {
			closeAll(socket, entrada, salida);
		}
	}

	// Método para recibir mensajes
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

	// Método para cerrar todos los flujos y el socket
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
	
	// Método main para probar el cliente
	public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        
        Socket socket = new Socket("127.0.0.1", 5000);
        Cliente cliente = new Cliente(socket, nombreUsuario);
        cliente.listenForMessages();
        cliente.sendMessage();
	}
	
	
} // Class
