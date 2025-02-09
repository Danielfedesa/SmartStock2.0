package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import controller.Login;
import controller.UsuarioSesion;
import model.*;

public class ScreenDashboardAdmin extends JFrame {

	private static final long serialVersionUID = 1L;

	public ScreenDashboardAdmin() {
		// Configuración de la ventana
		setTitle("SmartStock - Dashboard ADMINISTRADOR");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(800, 600));

		// Colores y fuentes
		Color fondoColor = new Color(240, 240, 240);
		Font fuenteTitulo = new Font("Arial", Font.BOLD, 24);

		// Panel principal
		JPanel contenedor = new JPanel(new GridBagLayout());
		contenedor.setBackground(fondoColor);

		// Panel superior con el botón de "Cerrar Sesión"
		JPanel panelSuperior = new JPanel(new GridBagLayout());
		panelSuperior.setBackground(fondoColor);
		GridBagConstraints gbcSuperior = new GridBagConstraints();
		gbcSuperior.insets = new Insets(10, 10, 10, 10);
		gbcSuperior.gridx = 1;
		gbcSuperior.gridy = 0;
		gbcSuperior.weightx = 1;
		gbcSuperior.anchor = GridBagConstraints.EAST;

		// Imagen para "Cerrar Sesión"
		JPanel botonCerrarSesion = createImageButton("salida.png", "Cerrar Sesión", this::cerrarSesion, 20, 20);
		panelSuperior.add(botonCerrarSesion, gbcSuperior);

		// Agregar el panel superior al contenedor principal
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		contenedor.add(panelSuperior, gbc);

		// Panel del título centrado pero más arriba
		JPanel panelTitulo = new JPanel(new GridBagLayout());
		panelTitulo.setBackground(fondoColor);

		// Crear y configurar el JLabel centrado
		JLabel tituloLabel = new JLabel("DASHBOARD ADMINISTRADOR", SwingConstants.CENTER);
		tituloLabel.setFont(fuenteTitulo);
		tituloLabel.setForeground(Color.DARK_GRAY);

		// Configuración del GridBagConstraints para el título
		GridBagConstraints gbcTitulo = new GridBagConstraints();
		gbcTitulo.gridx = 0;
		gbcTitulo.gridy = 1;
		gbcTitulo.gridwidth = 3;
		gbcTitulo.anchor = GridBagConstraints.CENTER;
		gbcTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbcTitulo.insets = new Insets(0, 0, 5, 0);
		gbcTitulo.weighty = 0.05;

		// Agregar el título al panel de título
		panelTitulo.add(tituloLabel, gbcTitulo);

		// Agregar el panel del título al contenedor principal
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		contenedor.add(panelTitulo, gbc);

		// Panel del menú con iconos
		JPanel panelMenu = new JPanel(new GridBagLayout());
		panelMenu.setPreferredSize(new Dimension(800, 600));
		panelMenu.setBackground(fondoColor);
		panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Creación de botones con imágenes y texto con espaciado
		JPanel gUsuarios = createImageButton("usuarios.png", "Usuarios", this::abrirGestionUsuarios, 40, 40);
		JPanel gCategorias = createImageButton("categorias.png", "Categorías", this::abrirGestionCategorias, 40, 40);
		JPanel gProductos = createImageButton("productos.png", "Productos", this::abrirGestionProductos, 40, 40);
		JPanel inventario = createImageButton("inventario.png", "Inventario", this::abrirInventario, 40, 40);
		JPanel movInventario = createImageButton("movimientos.png", "Movimientos de Inventario",
				this::abrirMovInventario, 40, 40);
		JPanel copiasSeguridad = createImageButton("copias.png", "Copias de Seguridad", this::abrirCopiasSeguridad, 40,
				40);
		JPanel alertasStock = createImageButton("alertas.png", "Alertas de Stock", this::abrirAlertasStock, 40, 40);
		JPanel chat = createImageButton("chat.png", "Chat", this::abrirChat, 40, 40);

		// Asegurar que el panel de botones está en `gbc.gridy = 2` para evitar que el
		// título lo pise
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 10, 10, 10);
		contenedor.add(panelMenu, gbc);

		// Primera fila
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		panelMenu.add(gUsuarios, gbc);
		gbc.gridx = 1;
		panelMenu.add(gCategorias, gbc);
		gbc.gridx = 2;
		panelMenu.add(gProductos, gbc);
		gbc.gridx = 3;
		panelMenu.add(copiasSeguridad, gbc);

		// Segunda fila
		gbc.gridy++;
		gbc.gridx = 0;
		panelMenu.add(inventario, gbc);
		gbc.gridx = 1;
		panelMenu.add(movInventario, gbc);
		gbc.gridx = 2;
		panelMenu.add(alertasStock, gbc);
		gbc.gridx = 3;
		panelMenu.add(chat, gbc);

		// Agregar el contenedor a la ventana
		add(contenedor);
	}

	private JPanel createImageButton(String imagePath, String texto, Runnable action, int width, int height) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		File file = new File("src/main/resources/images/" + imagePath);
		if (!file.exists()) {
			System.err.println("No se encontró la imagen: " + file.getAbsolutePath());
			return new JPanel();
		}

		ImageIcon icon = new ImageIcon(file.getAbsolutePath());
		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);

		JLabel labelIcon = new JLabel(icon);
		labelIcon.setHorizontalAlignment(SwingConstants.CENTER);
		labelIcon.setVerticalAlignment(SwingConstants.CENTER);
		labelIcon.setPreferredSize(new Dimension(width + 20, height + 20));

		JLabel labelTexto = new JLabel(texto, SwingConstants.CENTER);
		labelTexto.setFont(new Font("Arial", Font.BOLD, 12));
		labelTexto.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Aumenta la separación superior

		panel.add(labelIcon, BorderLayout.CENTER);
		panel.add(labelTexto, BorderLayout.SOUTH);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				action.run();
			}
		});

		return panel;
	}

	private void cerrarSesion() {
		int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?",
				"Confirmar Cierre de Sesión", JOptionPane.YES_NO_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION) {
			UsuarioSesion.cerrarSesion();
			dispose();
			new ScreenLogin(new Login()).setVisible(true);
		}
	}

	private void abrirGestionUsuarios() {
		new ScreenGUsuarios(new Usuario()).setVisible(true);
		this.dispose();
	}

	private void abrirGestionCategorias() {
		new ScreenGCategorias(new Categoria()).setVisible(true);
		this.dispose();
	}

	private void abrirGestionProductos() {
		new ScreenGProductos(new Producto()).setVisible(true);
		this.dispose();
	}

	private void abrirInventario() {
		new ScreenGInventario(new Producto()).setVisible(true);
		this.dispose();
	}

	private void abrirCopiasSeguridad() {
		new ScreenGCopiasSeguridad(new CopiaSeguridad()).setVisible(true);
		this.dispose();
	}

	private void abrirMovInventario() {
		new ScreenGHistorialInventario(new HistorialInventario()).setVisible(true);
		this.dispose();
	}

	private void abrirAlertasStock() {
		new ScreenAlertasStock(new Producto()).setVisible(true);
		this.dispose();
	}

	private void abrirChat() {
		new ScreenAlertasStock(new Producto()).setVisible(true);
		this.dispose();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ScreenDashboardAdmin().setVisible(true));
	}
}
