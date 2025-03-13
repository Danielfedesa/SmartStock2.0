package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.HistorialInventarioController;
import controller.UsuarioSesion;
import model.HistorialInventario;

/**
 * Pantalla de gestion del historial de inventario en el sistema SmartStock.
 * Permite visualizar los movimientos de stock realizados.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 02/2025
 */
public class ScreenGHistorialInventario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final HistorialInventarioController historialController; // Controlador que maneja la lógica relacionada con productos.
	private JTable tablaProductos; // Tabla para mostrar la lista de productos.

	/**
	 * Constructor que inicializa la pantalla de gestion del historial de inventario.
	 * 
	 * @param historial Objeto que representa el historial de inventario.
	 */
	public ScreenGHistorialInventario() {
        this.historialController = new HistorialInventarioController();

		// Configuración básica de la ventana.
		setTitle("SmartStock - Historial de movimientos de stock");
		setSize(1350, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(800, 600));

		// Estilos de colores y fuentes.
		Color fondoColor = new Color(240, 240, 240);
		Color botonColor = new Color(70, 130, 180);
		Color textoBotonColor = Color.white;
		Font fuenteTitulo = new Font("Arial", Font.BOLD, 24);
		Font fuenteBotones = new Font("Arial", Font.PLAIN, 16);

		// Configuración del fondo.
		getContentPane().setBackground(fondoColor);

		// Panel principal que contiene todos los componentes.
		JPanel contenedor = new JPanel(new BorderLayout());
		contenedor.setBackground(fondoColor);

		// Panel superior con el título y el botón "Volver atrás"
		JPanel panelSuperior = new JPanel(new GridBagLayout());
		panelSuperior.setBackground(fondoColor);

		GridBagConstraints gbcSuperior = new GridBagConstraints();
		gbcSuperior.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
		gbcSuperior.fill = GridBagConstraints.HORIZONTAL; // Ajuste horizontal

		// Título de la pantalla
		JLabel tituloLabel = new JLabel("Historial movimientos de stock", JLabel.CENTER);
		tituloLabel.setFont(fuenteTitulo);
		tituloLabel.setForeground(Color.DARK_GRAY);
		gbcSuperior.gridx = 0;
		gbcSuperior.gridy = 0;
		gbcSuperior.gridwidth = 2; // Ocupa dos columnas para centrar
		panelSuperior.add(tituloLabel, gbcSuperior);

		// Botón "Volver atrás"
		JButton botonVolver = new JButton("Volver atrás");
		botonVolver.setFont(fuenteBotones);
		botonVolver.setBackground(botonColor);
		botonVolver.setForeground(textoBotonColor);
		botonVolver.setFocusPainted(false);
		botonVolver.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(botonColor.darker(), 1),
				BorderFactory.createEmptyBorder(5, 15, 5, 15)));

		botonVolver.addActionListener(e -> {
			// Lógica para volver al dashboard según el rol del usuario.
			String rol = UsuarioSesion.getRolUsuarioActual();

			if ("admin".equals(rol)) {
				new ScreenDashboardAdmin().setVisible(true);
			} else if ("empleado".equals(rol)) {
				new ScreenDashboard().setVisible(true);
			}

			this.dispose(); // Cierra la pantalla actual
		});

		gbcSuperior.gridx = 0;
		gbcSuperior.gridy = 1; // Debajo del título
		gbcSuperior.gridwidth = 2; // Ocupa solo una columna
		gbcSuperior.anchor = GridBagConstraints.CENTER; // Centrar horizontalmente
		panelSuperior.add(botonVolver, gbcSuperior);

		// Añadir el panel superior al contenedor principal
		contenedor.add(panelSuperior, BorderLayout.NORTH);

		// Modelo de la tabla con columnas específicas.
		String[] columnas = { "Id Historial", "Id Producto", "Id Usuario", "Cantidad", "Movimiento", "Fecha" };
		DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
			
			private static final long serialVersionUID = 1L;
		};

		// Inicialización de la tabla de productos.
		tablaProductos = new JTable(modeloTabla);
		tablaProductos.putClientProperty("terminateEditOnFocusLost", true);
		tablaProductos.setRowHeight(30);
		tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
		tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

		// Configuración de ancho fijo para las columnas
		tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(10); // Columna ID Historial
		tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(10); // Columna ID Producto
		tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(10); // Columna ID Usuario
		tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(20); // Columna Cantidad
		tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(20); // Columna Movimiento
		tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(50); // Columna Fecha

		// Rellenar la tabla con datos desde el backend.
		cargarDatosTabla(modeloTabla);

		// Panel con la tabla.
		JScrollPane scrollTabla = new JScrollPane(tablaProductos);
		scrollTabla.setBorder(BorderFactory.createEmptyBorder(50, 300, 20, 300));

		// Agregar paneles al contenedor principal.
		contenedor.add(panelSuperior, BorderLayout.NORTH);
		contenedor.add(scrollTabla, BorderLayout.CENTER);

		// Añadir el contenedor a la ventana.
		add(contenedor);
	}

	/**
	 * Metodo para cargar datos en la tabla desde la base de datos.
	 * 
	 * @param Modelo de la tabla donde se insertaran los datos.
	 */
	private void cargarDatosTabla(DefaultTableModel modeloTabla) {
        try {
            List<HistorialInventario> movimientos = historialController.listarMovimientos();
            modeloTabla.setRowCount(0);

            for (HistorialInventario historial : movimientos) {
                modeloTabla.addRow(new Object[]{
                    historial.getIdHistorial(),
                    historial.getProducto().getIdProducto(),
                    historial.getUsuario().getIdUsuario(),
                    historial.getCantidad(),
                    historial.getTipoMovimiento(),
                    historial.getFecha()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los movimientos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}