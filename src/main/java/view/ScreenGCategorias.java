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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Categoria;

/**
 * Pantalla de gestion de categorias en el sistema SmartStock. Permite listar,
 * agregar, editar y eliminar categorias.
 *
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class ScreenGCategorias extends JFrame {

	private static final long serialVersionUID = 1L;
	private Categoria categoria; // Controlador que maneja la lógica relacionada con usuarios.
	private JTable tablaCategorias; // Tabla para mostrar la lista de usuarios.
	private ScreenFormularios screenFormularios; // Declarar ScreenFormularios

	/**
	 * Constructor que inicializa la pantalla de gestion de categorias.
	 * 
	 * @param categoria Objeto que representa la categoria para realizar
	 *                  operaciones.
	 */
	public ScreenGCategorias(Categoria categoria) {
		this.categoria = categoria;

		// Inicializar ScreenFormularios
		this.screenFormularios = new ScreenFormularios();

		// Configuración básica de la ventana.
		setTitle("SmartStock - Gestión de Categorias");
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
		JLabel tituloLabel = new JLabel("Gestión de Categorias", JLabel.CENTER);
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
		String[] columnas = { "ID", "Nombre", "Descripción", "Editar", "Eliminar" };
		DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Solo las columnas de botones son editables.
				return column == 3 || column == 4;
			}
		};

		// Inicialización de la tabla de usuarios.
		tablaCategorias = new JTable(modeloTabla);
		tablaCategorias.setRowHeight(30);
		tablaCategorias.setFont(new Font("Arial", Font.PLAIN, 12));
		tablaCategorias.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

		// Configuración de ancho fijo para las columnas
		tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(10); // Columna ID
		tablaCategorias.getColumnModel().getColumn(1).setPreferredWidth(100); // Columna Nombre
		tablaCategorias.getColumnModel().getColumn(2).setPreferredWidth(700); // Columna Descripcion
		tablaCategorias.getColumnModel().getColumn(3).setPreferredWidth(20); // Botón Editar
		tablaCategorias.getColumnModel().getColumn(4).setPreferredWidth(20); // Botón Eliminar

		// Rellenar la tabla con datos desde el backend.
		cargarDatosTabla(modeloTabla);

		// Renderizador y editor para el botón "Editar".
		tablaCategorias.getColumnModel().getColumn(3)
				.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
					JButton botonEditar = new JButton("Editar");
					botonEditar.setBackground(botonColor);
					botonEditar.setForeground(textoBotonColor);
					return botonEditar;
				});

		// Renderizador y editor para el botón "Editar".
		tablaCategorias.getColumnModel().getColumn(3)
				.setCellEditor(new javax.swing.DefaultCellEditor(new JTextField()) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public java.awt.Component getTableCellEditorComponent(JTable table, Object value,
							boolean isSelected, int row, int column) {
						JButton botonEditar = new JButton("Editar"); // Crear el botón "Editar".
						botonEditar.setBackground(botonColor); // Configuración de estilo para el botón.
						botonEditar.setForeground(textoBotonColor);

						botonEditar.addActionListener(e -> {
							try {
								int idCategoria = Integer.parseInt(table.getValueAt(row, 0).toString()); // Recuperar el ID del usuario.

								// Recuperar el producto antes de llamar al formulario.
								Categoria categoriaEditar = categoria.recuperarCat(idCategoria);

								// Crear instancia de ScreenFormularios.
								ScreenFormularios screenFormularios = new ScreenFormularios();

								// Llamar al método abrirFormularioEdicion pasando el Producto.
								screenFormularios.abrirFormularioEdicionCat(categoriaEditar, () -> {
									// Acción adicional: recargar la tabla principal después de cerrar el
									// formulario.
									DefaultTableModel modeloTabla = (DefaultTableModel) table.getModel();
									modeloTabla.setRowCount(0); // Limpiar la tabla.
									cargarDatosTabla(modeloTabla); // Volver a cargar los datos.
								});
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null,
										"Error al recuperar la categoría: " + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						});

						return botonEditar; // Devolver el botón como componente del editor.
					}
				});

		// Configuración del renderizador para la columna "Eliminar"
		tablaCategorias.getColumnModel().getColumn(4)
				.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
					JButton botonEliminar = new JButton("Eliminar");
					botonEliminar.setBackground(Color.RED);
					botonEliminar.setForeground(Color.WHITE);
					botonEliminar.setFocusPainted(false); // Elimina el borde de selección
					return botonEliminar;
				});

		// Configuración del editor para la columna "Eliminar"
		tablaCategorias.getColumnModel().getColumn(4)
				.setCellEditor(new javax.swing.DefaultCellEditor(new JTextField()) {
					private static final long serialVersionUID = 1L;

					@Override
					public java.awt.Component getTableCellEditorComponent(JTable table, Object value,
							boolean isSelected, int row, int column) {
						JButton botonEliminar = new JButton("Eliminar");
						botonEliminar.setBackground(Color.RED);
						botonEliminar.setForeground(Color.WHITE);

						// Acción al hacer clic en el botón
						botonEliminar.addActionListener(e -> {
							try {
								// Detener la edición activa antes de modificar el modelo de la tabla
								if (tablaCategorias.isEditing()) {
									tablaCategorias.getCellEditor().stopCellEditing();
								}

								// Verificar que la fila aún existe
								if (row >= 0 && row < table.getRowCount()) {
									int idCategoria = Integer.parseInt(table.getValueAt(row, 0).toString()); // ID del
																												// producto
									int confirmacion = JOptionPane.showConfirmDialog(null,
											"¿Estás seguro de que deseas eliminar esta categoría?",
											"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

									if (confirmacion == JOptionPane.YES_OPTION) {
										// Eliminar el producto del backend
										categoria.eliminarCategoria(idCategoria);
										JOptionPane.showMessageDialog(null, "Categoría eliminada correctamente.");

										// Recargar la tabla con datos actualizados
										DefaultTableModel modeloTabla = (DefaultTableModel) table.getModel();
										modeloTabla.setRowCount(0); // Limpia las filas actuales
										cargarDatosTabla(modeloTabla); // Recarga las filas desde el backend
									}
								} else {
									JOptionPane.showMessageDialog(null, "Fila no válida para eliminar.", "Advertencia",
											JOptionPane.WARNING_MESSAGE);
								}
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null,
										"Error al eliminar la categoría: " + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						});

						// Devolver el botón como componente del editor
						return botonEliminar;
					}
				});

		// Panel con la tabla.
		JScrollPane scrollTabla = new JScrollPane(tablaCategorias);
		scrollTabla.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

		// Panel inferior con el botón para añadir usuarios.
		JPanel panelInferior = new JPanel(new GridBagLayout());
		panelInferior.setBackground(fondoColor);

		JButton botonAñadir = new JButton("Añadir Categoria");
		botonAñadir.setFont(fuenteBotones);
		botonAñadir.setBackground(botonColor);
		botonAñadir.setForeground(textoBotonColor);
		botonAñadir.setFocusPainted(false);
		botonAñadir.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(botonColor.darker(), 1),
				BorderFactory.createEmptyBorder(5, 15, 5, 15)));

		// Instanciar ScreenFormularios y llamar al método abrirFormularioAddUsu
		botonAñadir.addActionListener(e -> {
			screenFormularios.abrirFormularioAddCat(() -> {
				DefaultTableModel modeloTablaActual = (DefaultTableModel) tablaCategorias.getModel();
				modeloTablaActual.setRowCount(0); // Limpia las filas existentes
				cargarDatosTabla(modeloTablaActual); // Carga los datos actualizados
			});
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 100, 10);
		panelInferior.add(botonAñadir, gbc);

		// Agregar paneles al contenedor principal.
		contenedor.add(panelSuperior, BorderLayout.NORTH);
		contenedor.add(scrollTabla, BorderLayout.CENTER);
		contenedor.add(panelInferior, BorderLayout.SOUTH);

		// Añadir el contenedor a la ventana.
		add(contenedor);
	}

	/**
     * Metodo para cargar los datos en la tabla desde la base de datos.
     * Recupera las categorias y las muestra en la tabla.
     * 
     * @param Modelo de la tabla al cual se agregaran las filas.
     */
	private void cargarDatosTabla(DefaultTableModel modeloTabla) {
		try {
			List<Categoria> categorias = categoria.listarCategorias(); // Obtener la lista de categorias.
			modeloTabla.setRowCount(0); // Limpia todas las filas antes de agregar nuevas
			for (Categoria categoria : categorias) {
				modeloTabla.addRow(new Object[] { String.valueOf(categoria.getIdCategoria()), // Convertir el ID a
																								// String.
						categoria.getNombreCategoria(), categoria.getDescripcion(), "Editar", "Eliminar" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar las categorias: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

} // Class