package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Login;
import controller.UsuarioSesion;
import model.Usuario;

public class ScreenLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	public ScreenLogin(Login loginControlador) {
		setTitle("SmartStock - Inicio de sesión");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(800, 600));

		// Estilos
		Color fondoColor = new Color(240, 240, 240);
		Color botonColor = new Color(70, 130, 180);
		Color textoBotonColor = Color.white;
		Font fuenteTitulo = new Font("Arial", Font.BOLD, 24);
		Font fuenteSubtitulo = new Font("Arial", Font.BOLD, 16);
		Font fuenteTextos = new Font("Arial", Font.PLAIN, 16);

		// Configuración del fondo
		getContentPane().setBackground(fondoColor);

		// Panel principal
		JPanel contenedor = new JPanel(new GridBagLayout());
		contenedor.setBackground(fondoColor);

		// Panel del formulario con tamaño fijo
		JPanel panelFormulario = new JPanel();
		panelFormulario.setPreferredSize(new Dimension(400, 600)); // Tamaño fijo
		panelFormulario.setBackground(fondoColor);
		panelFormulario.setLayout(new GridBagLayout());
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Componentes
		JLabel tituloLabel = new JLabel("Bienvenido a SmartStock", SwingConstants.CENTER);
		tituloLabel.setFont(fuenteTitulo);
		tituloLabel.setForeground(Color.DARK_GRAY);

		JLabel subtituloLabel = new JLabel("Introduzca sus credenciales para acceder al sistema.",
				SwingConstants.CENTER);
		subtituloLabel.setFont(fuenteSubtitulo);
		subtituloLabel.setForeground(Color.DARK_GRAY);

		JTextField usuarioField = new JTextField();
		usuarioField.setBorder(BorderFactory.createTitledBorder("Correo electrónico"));
		usuarioField.setFont(fuenteTextos);

		JPasswordField contrasenaField = new JPasswordField();
		contrasenaField.setBorder(BorderFactory.createTitledBorder("Contraseña"));
		contrasenaField.setFont(fuenteTextos);

		JButton enviar = new JButton("Iniciar sesión");
		enviar.setFont(fuenteTextos);
		enviar.setBackground(botonColor);
		enviar.setForeground(textoBotonColor);
		enviar.setFocusPainted(false);
		enviar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(botonColor.darker(), 1),
				BorderFactory.createEmptyBorder(5, 15, 5, 15)));

		// Hover para el botón
		enviar.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				enviar.setBackground(botonColor.brighter());
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				enviar.setBackground(botonColor);
			}
		});

		// Acción del botón "Iniciar sesión"
		enviar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String email = usuarioField.getText();
		        String pass = new String(contrasenaField.getPassword());

		        if (email.isEmpty() || pass.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        try {
		            // Verificar si el usuario existe y la contraseña es correcta
		            System.out.println("Intentando iniciar sesión con email: " + email);
		            Usuario usuarioLogueado = loginControlador.iniciarSesion(email, pass);

		            if (usuarioLogueado != null) {
		                System.out.println("Inicio de sesión exitoso.");
		                UsuarioSesion.setUsuarioActual(usuarioLogueado); // Guardar el usuario en sesión.
		                JOptionPane.showMessageDialog(null, "Inicio de sesión correcto.\nBienvenido.");
		                dispose(); // Cierra la ventana de inicio de sesión.

		                // Redirige según el rol del usuario.
		                if ("admin".equals(usuarioLogueado.getRol())) {
		                    new ScreenDashboardAdmin().setVisible(true);
		                } else if ("empleado".equals(usuarioLogueado.getRol())) {
		                    new ScreenDashboard().setVisible(true);
		                }
		            } else {
		                System.out.println("Error: Credenciales incorrectas");
		                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.", "Error",
		                        JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Error al intentar iniciar sesión: " + ex.getMessage(), "Error",
		                    JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
		    }
		});


		// Añadir componentes al panel del formulario
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;

		gbc.gridy = 0;
		panelFormulario.add(tituloLabel, gbc);

		gbc.gridy++;
		panelFormulario.add(subtituloLabel, gbc);

		gbc.gridy++;
		panelFormulario.add(usuarioField, gbc);

		gbc.gridy++;
		panelFormulario.add(contrasenaField, gbc);

		gbc.gridy++;
		panelFormulario.add(enviar, gbc);

		// Añadir el formulario al contenedor principal (centrado)
		contenedor.add(panelFormulario);

		// Añadir el contenedor a la ventana
		add(contenedor);
	}
}
