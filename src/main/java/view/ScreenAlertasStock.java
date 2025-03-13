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

import controller.ProductoController;
import model.Producto;

/**
 * Ventana que muestra los productos con stock bajo en la base de datos.
 * 
 * <p>
 * Esta vista permite a los administradores visualizar los productos que han alcanzado
 * un nivel de stock critico. Se presentan en una tabla con sus respectivas
 * descripciones, precios y cantidades minimas requeridas.
 * </p>
 * 
 * <p>
 * La vista tambien permite regresar al dashboard del administrador.
 * </p>
 * 
 * @see Producto
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class ScreenAlertasStock extends JFrame {

	private static final long serialVersionUID = 1L;
	private final ProductoController productoController; // Controlador que maneja la lógica relacionada con productos.
	private JTable tablaProductos; // Tabla para mostrar la lista de productos.

	/**
     * Constructor de la ventana de alertas de stock.
     * 
     * @param productoController Instancia de Producto para obtener la lista de productos con stock bajo.
     */
	public ScreenAlertasStock() {
        this.productoController = new ProductoController();

		// Configuración básica de la ventana.
		setTitle("SmartStock - Listado de productos con stock bajo");
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
		JLabel tituloLabel = new JLabel("Listado de alertas", JLabel.CENTER);
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
			// Lógica para volver al dashboard
			new ScreenDashboardAdmin().setVisible(true); // Abre la pantalla del dashboard
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
		String[] columnas = { "ID", "Nombre", "Descripción", "Precio", "Stock", "Stock MIN", "ID Cat" };
		DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

		// Inicialización de la tabla de productos.
		tablaProductos = new JTable(modeloTabla);
		tablaProductos.putClientProperty("terminateEditOnFocusLost", true);
		tablaProductos.setRowHeight(30);
		tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
		tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

		// Configuración de ancho fijo para las columnas
		tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(10); // Columna ID
		tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(200); // Columna Nombre
		tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(400); // Columna Descripcion
		tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(20); // Columna Precio
		tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(20); // Columna Stock
		tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(20); // Columna Stock Minimo
		tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(10); // Columna ID Categoria

		// Rellenar la tabla con datos desde el backend.
		cargarAlertasStock(modeloTabla);

		// Panel con la tabla.
		JScrollPane scrollTabla = new JScrollPane(tablaProductos);
		scrollTabla.setBorder(BorderFactory.createEmptyBorder(100, 80, 20, 80));

		// Agregar paneles al contenedor principal.
		contenedor.add(panelSuperior, BorderLayout.NORTH);
		contenedor.add(scrollTabla, BorderLayout.CENTER);

		// Añadir el contenedor a la ventana.
		add(contenedor);
	}

	/**
     * Carga la lista de productos con stock bajo en la tabla.
     * 
     * <p>
     * Este metodo obtiene la lista de productos desde la base de datos y la inserta
     * en la tabla de la interfaz. Si ocurre un error al cargar los datos, se muestra
     * un mensaje de error en la pantalla.
     * </p>
     * 
     * @param modeloTabla Modelo de la tabla donde se insertarán los datos.
     */
	private void cargarAlertasStock(DefaultTableModel modeloTabla) {
        try {
            List<Producto> productos = productoController.listarStockBajo();
            modeloTabla.setRowCount(0); // Limpia todas las filas antes de agregar nuevas

            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[] {
                        producto.getIdProducto(),
                        producto.getNombreProducto(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getStockMinimo(),
                        producto.getCategoria().getIdCategoria(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}