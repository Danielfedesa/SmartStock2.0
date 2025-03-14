package view;

import controller.UsuarioSesion;
import model.Usuario;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Ventana de chat en la aplicacion SmartStock.
 * 
 * <p>
 * Esta clase proporciona una interfaz grafica para la comunicacion en tiempo real
 * entre los usuarios a traves de un servidor de chat. Se conecta a un servidor
 * en el puerto 5000 y permite enviar y recibir mensajes en tiempo real.
 * </p>
 * 
 * <p>
 * Se utiliza un {@code JTextPane} para mostrar los mensajes con formato, donde los
 * nombres de usuario aparecen en negrita. Los mensajes se envian mediante un
 * {@code JTextField} y un boton de "Enviar".
 * </p>
 * 
 * <p>
 * Se permite la desconexion manual del usuario mediante un boton, y tambien se
 * maneja el cierre de la ventana con una confirmacion de desconexion.
 * </p>
 * 
 * @see UsuarioSesion
 * @see model.Usuario
 * @author Daniel Fernandez Sanchez
 * @version 1.0 02/2025
 */
public class ScreenChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextPane chatArea;
	private StyledDocument doc;
	private JTextField messageField;
	private JButton sendButton, disconnectButton;
	private Socket socket;
	private BufferedReader input;
	private BufferedWriter output;
	private String username;

	/**
     * Constructor de la ventana de chat.
     * <p>
     * Obtiene el usuario en sesion y establece la conexion con el servidor de chat.
     * Configura la interfaz grafica y los eventos de usuario.
     * </p>
     */
	public ScreenChat() {
		// Obtener el usuario en sesión
		Usuario usuario = UsuarioSesion.getUsuarioActual();

		if (usuario == null) {
			JOptionPane.showMessageDialog(this, "Error: No hay un usuario en sesión.", "Error",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}

		// Extraer el nombre de usuario del email (antes del '@')
		this.username = usuario.getEmail().split("@")[0];

		// Configuración de la ventana
		setTitle("SmartStock - Chat");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(600, 500));

		// Interceptar el evento de cierre de la ventana
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				confirmarSalida();
			}
		});

		// Colores y fuentes
		Color fondoColor = new Color(240, 240, 240);
		Font fuenteTitulo = new Font("Arial", Font.BOLD, 20);
		Font fuenteTexto = new Font("Arial", Font.PLAIN, 14);

		// Panel principal
		JPanel contenedor = new JPanel(new GridBagLayout());
		contenedor.setBackground(fondoColor);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// Título
		JLabel tituloLabel = new JLabel("Chat de SmartStock", SwingConstants.CENTER);
		tituloLabel.setFont(fuenteTitulo);
		tituloLabel.setForeground(Color.DARK_GRAY);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		contenedor.add(tituloLabel, gbc);

		// Área de mensajes con JTextPane para formatear texto
		chatArea = new JTextPane();
		chatArea.setEditable(false);
		doc = chatArea.getStyledDocument();

		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setPreferredSize(new Dimension(500, 300));

		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		contenedor.add(scrollPane, gbc);

		// Campo de mensaje
		messageField = new JTextField();
		messageField.setFont(fuenteTexto);
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		contenedor.add(messageField, gbc);

		// Botón de enviar
		sendButton = new JButton("Enviar");
		sendButton.setFont(fuenteTexto);
		sendButton.setBackground(new Color(70, 130, 180));
		sendButton.setForeground(Color.white);
		sendButton.setFocusPainted(false);
		sendButton.addActionListener(e -> enviarMensaje());
		gbc.gridx = 1;
		gbc.weightx = 0;
		contenedor.add(sendButton, gbc);

		// Botón de desconexión
		disconnectButton = new JButton("Desconectar");
		disconnectButton.setFont(fuenteTexto);
		disconnectButton.setBackground(new Color(220, 53, 69));
		disconnectButton.setForeground(Color.white);
		disconnectButton.setFocusPainted(false);
		disconnectButton.addActionListener(e -> desconectar());
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.NONE;
		contenedor.add(disconnectButton, gbc);

		// Agregar el contenedor a la ventana
		add(contenedor);

		// Conectar al servidor
		connectToServer();

		// Iniciar el hilo de escucha de mensajes
		listenForMessages();
	}

	/**
     * Conecta el cliente al servidor de chat.
     */
	private void connectToServer() {
		try {
			socket = new Socket("127.0.0.1", 5000);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			output.write(username);
			output.newLine();
			output.flush();

			agregarMensaje("Conectado como: " + username + "\n", true);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error al conectar con el servidor.", "Error",
					JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	/**
     * Escucha mensajes entrantes del servidor en un hilo separado.
     */
	private void listenForMessages() {
		new Thread(() -> {
			String mensaje;
			while (socket.isConnected()) {
				try {
					mensaje = input.readLine();
					if (mensaje != null) {
						System.out.println("Mensaje recibido: " + mensaje);
						mostrarMensajeFormateado(mensaje);
					}
				} catch (IOException e) {
					desconectar();
					break;
				}
			}
		}).start();
	}

	/**
     * Envia un mensaje al servidor de chat.
     */
	private void enviarMensaje() {
		String mensaje = messageField.getText().trim();
		if (!mensaje.isEmpty()) {
			try {
				String mensajeCompleto = username + ": " + mensaje;
				output.write(mensajeCompleto);
				output.newLine();
				output.flush();

				mostrarMensajeFormateado(mensajeCompleto);
				messageField.setText("");

			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error al enviar el mensaje.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
     * Formatea los mensajes para mostrar el nombre en negrita.
     * 
     * @param mensaje Mensaje recibido o enviado.
     */
	private void mostrarMensajeFormateado(String mensaje) {
		int separatorIndex = mensaje.indexOf(": ");
		if (separatorIndex != -1) {
			String nombre = mensaje.substring(0, separatorIndex + 2); // "Usuario: "
			String texto = mensaje.substring(separatorIndex + 2); // Mensaje

			agregarMensaje(nombre, true); // Nombre en negrita
			agregarMensaje(texto + "\n", false); // Texto normal
		} else {
			agregarMensaje(mensaje + "\n", false);
		}
	}

	/**
     * Agrega un mensaje al area de chat con formato y estilo especificado.
     */
	private void agregarMensaje(String mensaje, boolean bold) {
		try {
			Style style = chatArea.addStyle("Estilo", null);
			if (bold) {
				StyleConstants.setBold(style, true);
			} else {
				StyleConstants.setBold(style, false);
			}
			doc.insertString(doc.getLength(), mensaje, style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
     * Solicita confirmacion al usuario antes de cerrar la ventana.
     */
	private void confirmarSalida() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas desconectarte?", "Confirmar desconexión",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (opcion == JOptionPane.YES_OPTION) {
			desconectar();
		}
	}

	/**
    * Desconecta al usuario del chat y cierra la ventana.
    */
	private void desconectar() {
		try {
			if (socket != null && !socket.isClosed()) {
				if (output != null) {
					String mensajeSalida = "SERVIDOR: " + username + " ha salido del chat";
					output.write(mensajeSalida);
					output.newLine();
					output.flush();
				}
				socket.close();
			}
		} catch (IOException e) {
			System.err.println("Error al desconectar: " + e.getMessage());
		} finally {
			dispose();
		}
	}
}