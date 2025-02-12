package view;

import controller.UsuarioSesion;
import model.Usuario;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

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

    public ScreenChat() {
        // Obtener el usuario en sesión
        Usuario usuario = UsuarioSesion.getUsuarioActual();

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Error: No hay un usuario en sesión.", "Error", JOptionPane.ERROR_MESSAGE);
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
                confirmExit();
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
        sendButton.addActionListener(e -> sendMessage());
        gbc.gridx = 1;
        gbc.weightx = 0;
        contenedor.add(sendButton, gbc);

        // Botón de desconexión
        disconnectButton = new JButton("Desconectar");
        disconnectButton.setFont(fuenteTexto);
        disconnectButton.setBackground(new Color(220, 53, 69));
        disconnectButton.setForeground(Color.white);
        disconnectButton.setFocusPainted(false);
        disconnectButton.addActionListener(e -> disconnect());
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

    // Método para conectar con el servidor
    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            output.write(username);
            output.newLine();
            output.flush();

            appendMessage("Conectado como: " + username + "\n", true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void listenForMessages() {
        new Thread(() -> {
            String mensaje;
            while (socket.isConnected()) {
                try {
                    mensaje = input.readLine();
                    if (mensaje != null) {
                        System.out.println("Mensaje recibido: " + mensaje);
                        showFormattedMessage(mensaje);
                    }
                } catch (IOException e) {
                    disconnect();
                    break;
                }
            }
        }).start();
    }

    // Método para enviar mensajes
    private void sendMessage() {
        String mensaje = messageField.getText().trim();
        if (!mensaje.isEmpty()) {
            try {
                String mensajeCompleto = username + ": " + mensaje;
                output.write(mensajeCompleto);
                output.newLine();
                output.flush();

                showFormattedMessage(mensajeCompleto);
                messageField.setText("");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al enviar el mensaje.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para mostrar nombres en negrita en el chat
    private void showFormattedMessage(String mensaje) {
        int separatorIndex = mensaje.indexOf(": ");
        if (separatorIndex != -1) {
            String nombre = mensaje.substring(0, separatorIndex + 2); // "Usuario: "
            String texto = mensaje.substring(separatorIndex + 2); // Mensaje

            appendMessage(nombre, true); // Nombre en negrita
            appendMessage(texto + "\n", false); // Texto normal
        } else {
            appendMessage(mensaje + "\n", false);
        }
    }

    // Método para agregar mensajes con formato al JTextPane
    private void appendMessage(String mensaje, boolean bold) {
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

    // Método para mostrar diálogo de confirmación al cerrar
    private void confirmExit() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas desconectarte?",
                "Confirmar desconexión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            disconnect();
        }
    }

    // Método para desconectar del chat
    private void disconnect() {
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
