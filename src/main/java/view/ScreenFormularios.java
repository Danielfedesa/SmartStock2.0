package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.UsuarioSesion;
import model.Categoria;
import model.HistorialInventario;
import model.HistorialInventario.TipoMovimiento;
import model.Producto;
import model.Usuario;

public class ScreenFormularios {

	// Formularios editar y añadir PRODUCTOS:

	public void abrirFormularioEdicionProd(Producto productoEditar, Runnable onUpdateCallback) {
		JFrame formularioEdicion = new JFrame("Editar Producto");
		formularioEdicion.setSize(400, 600);
		formularioEdicion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		formularioEdicion.setLocationRelativeTo(null);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Configuración de tamaño estándar para los campos.
		Dimension campoTamanio = new Dimension(200, 25);

		// Campo "Nombre".
		JLabel nombreLabel = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(nombreLabel, gbc);

		JTextField nombreField = new JTextField(productoEditar.getNombreProducto());
		nombreField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(nombreField, gbc);

		// Campo "Descripción".
		JLabel descripcionLabel = new JLabel("Descripción:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(descripcionLabel, gbc);

		JTextArea descripcionField = new JTextArea(productoEditar.getDescripcion());
		descripcionField.setLineWrap(true);
		descripcionField.setWrapStyleWord(true);
		JScrollPane scrollDescripcion = new JScrollPane(descripcionField);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		panelFormulario.add(scrollDescripcion, gbc);

		// Restablecer gridheight y fill.
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Campo "Precio".
		JLabel precioLabel = new JLabel("Precio:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(precioLabel, gbc);

		JTextField precioField = new JTextField(String.valueOf(productoEditar.getPrecio()));
		precioField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(precioField, gbc);

		// Campo "Stock".
		JLabel stockLabel = new JLabel("Stock:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		panelFormulario.add(stockLabel, gbc);

		JTextField stockField = new JTextField(String.valueOf(productoEditar.getStock()));
		stockField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 4;
		panelFormulario.add(stockField, gbc);

		// Campo "Stock mínimo".
		JLabel stockMinLabel = new JLabel("Stock mínimo:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		panelFormulario.add(stockMinLabel, gbc);

		JTextField stockMinField = new JTextField(String.valueOf(productoEditar.getStockMinimo()));
		stockMinField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 5;
		panelFormulario.add(stockMinField, gbc);

		// Campo "ID Categoría".
		JLabel categoriaLabel = new JLabel("ID Categoría:");
		gbc.gridx = 0;
		gbc.gridy = 6;
		panelFormulario.add(categoriaLabel, gbc);

		JTextField categoriaField = new JTextField(String.valueOf(productoEditar.getCategoria().getIdCategoria()));
		categoriaField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 6;
		panelFormulario.add(categoriaField, gbc);

		JButton aplicarCambios = new JButton("Aplicar Cambios");
		aplicarCambios.addActionListener(e -> {
			try {
				int idCategoria = Integer.parseInt(categoriaField.getText());
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(idCategoria); // Asumimos que la categoría existe

				productoEditar.setNombreProducto(nombreField.getText());
				productoEditar.setDescripcion(descripcionField.getText());
				productoEditar.setPrecio(Double.parseDouble(precioField.getText()));
				productoEditar.setStock(Integer.parseInt(stockField.getText()));
				productoEditar.setStockMinimo(Integer.parseInt(stockMinField.getText()));
				productoEditar.setCategoria(categoria); // Asignamos la categoría al producto

				productoEditar.actualizarProducto();
				JOptionPane.showMessageDialog(formularioEdicion, "Producto actualizado correctamente.");
				formularioEdicion.dispose();

				if (onUpdateCallback != null) {
					onUpdateCallback.run();
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(formularioEdicion, "Error al actualizar el producto: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panelFormulario.add(aplicarCambios, gbc);

		formularioEdicion.add(panelFormulario);
		formularioEdicion.setVisible(true);
	}

	public void abrirFormularioAddProd(Runnable callback) {
		JFrame formularioInsertar = new JFrame("Añadir Producto");
		formularioInsertar.setSize(400, 600);
		formularioInsertar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		formularioInsertar.setLocationRelativeTo(null);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Dimension campoTamanio = new Dimension(200, 25); // Tamaño para los campos de texto del formulario.

		// Campos del formulario
		JLabel nombreLabel = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(nombreLabel, gbc);

		JTextField nombreField = new JTextField();
		nombreField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(nombreField, gbc);

		JLabel descripcionLabel = new JLabel("Descripción:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(descripcionLabel, gbc);

		JTextField descripcionField = new JTextField();
		descripcionField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(descripcionField, gbc);

		JLabel precioLabel = new JLabel("Precio:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelFormulario.add(precioLabel, gbc);

		JTextField precioField = new JTextField();
		precioField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 2;
		panelFormulario.add(precioField, gbc);

		JLabel stockLabel = new JLabel("Stock:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(stockLabel, gbc);

		JTextField stockField = new JTextField();
		stockField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(stockField, gbc);

		JLabel stockMinLabel = new JLabel("Stock mínimo:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		panelFormulario.add(stockMinLabel, gbc);

		JTextField stockMinField = new JTextField();
		stockMinField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 4;
		panelFormulario.add(stockMinField, gbc);

		JLabel categoriaLabel = new JLabel("ID Categoría:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		panelFormulario.add(categoriaLabel, gbc);

		JTextField categoriaField = new JTextField();
		categoriaField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 5;
		panelFormulario.add(categoriaField, gbc);

		// Botón "Añadir Producto"
		JButton aplicarCambios = new JButton("Añadir Producto");
		aplicarCambios.addActionListener(e -> {
			try {
				// Verificamos que el idCategoria sea válido
				int idCategoria = Integer.parseInt(categoriaField.getText());
				// Buscar la categoría en la base de datos por el ID (esto es necesario para
				// establecer la relación)
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(idCategoria); // Asumimos que la categoría ya existe.

				// Crear el nuevo producto con la categoría seleccionada
				Producto nuevoProducto = new Producto();
				nuevoProducto.setNombreProducto(nombreField.getText());
				nuevoProducto.setDescripcion(descripcionField.getText());
				nuevoProducto.setPrecio(Double.parseDouble(precioField.getText()));
				nuevoProducto.setStock(Integer.parseInt(stockField.getText()));
				nuevoProducto.setStockMinimo(Integer.parseInt(stockMinField.getText()));
				nuevoProducto.setCategoria(categoria); // Asignamos la categoría

				// Llamamos al método para crear el producto en la base de datos
				nuevoProducto.crearProducto();
				JOptionPane.showMessageDialog(formularioInsertar, "Producto añadido correctamente.");
				formularioInsertar.dispose();

				if (callback != null) {
					callback.run(); // Ejecuta el callback para recargar la tabla
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(formularioInsertar,
						"Error en los campos: Asegúrese de ingresar un ID de categoría válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(formularioInsertar, "Error al añadir el producto: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		panelFormulario.add(aplicarCambios, gbc);

		formularioInsertar.add(panelFormulario);
		formularioInsertar.setVisible(true);
	}

	// Formularios editar y añadir USUARIOS:

	public void abrirFormularioEdicionUsu(Usuario usuarioEditar, Runnable onUpdateCallback) {
		try {
			JFrame formularioEdicion = new JFrame("Editar Usuario");
			formularioEdicion.setSize(400, 600);
			formularioEdicion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			formularioEdicion.setLocationRelativeTo(null);

			JPanel panelFormulario = new JPanel(new GridBagLayout());
			panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(10, 10, 10, 10);
			gbc.fill = GridBagConstraints.HORIZONTAL;

			// Configuración de tamaño estándar para los campos.
			Dimension campoTamanio = new Dimension(200, 25);

			// Campos del formulario para editar los datos.
			JLabel nombreLabel = new JLabel("Nombre:");
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelFormulario.add(nombreLabel, gbc);

			JTextField nombreField = new JTextField(usuarioEditar.getNombreUsuario());
			nombreField.setPreferredSize(campoTamanio);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelFormulario.add(nombreField, gbc);

			JLabel apellido1Label = new JLabel("Apellido 1:");
			gbc.gridx = 0;
			gbc.gridy = 1;
			panelFormulario.add(apellido1Label, gbc);

			JTextField apellido1Field = new JTextField(usuarioEditar.getApellido1());
			apellido1Field.setPreferredSize(campoTamanio);
			gbc.gridx = 1;
			gbc.gridy = 1;
			panelFormulario.add(apellido1Field, gbc);

			JLabel apellido2Label = new JLabel("Apellido 2:");
			gbc.gridx = 0;
			gbc.gridy = 2;
			panelFormulario.add(apellido2Label, gbc);

			JTextField apellido2Field = new JTextField(usuarioEditar.getApellido2());
			apellido2Field.setPreferredSize(campoTamanio);
			gbc.gridx = 1;
			gbc.gridy = 2;
			panelFormulario.add(apellido2Field, gbc);

			JLabel telefonoLabel = new JLabel("Teléfono:");
			gbc.gridx = 0;
			gbc.gridy = 3;
			panelFormulario.add(telefonoLabel, gbc);

			JTextField telefonoField = new JTextField(String.valueOf(usuarioEditar.getTelefono()));
			telefonoField.setPreferredSize(campoTamanio);
			gbc.gridx = 1;
			gbc.gridy = 3;
			panelFormulario.add(telefonoField, gbc);

			JLabel emailLabel = new JLabel("Email:");
			gbc.gridx = 0;
			gbc.gridy = 4;
			panelFormulario.add(emailLabel, gbc);

			JTextField emailField = new JTextField(usuarioEditar.getEmail());
			emailField.setPreferredSize(campoTamanio);
			gbc.gridx = 1;
			gbc.gridy = 4;
			panelFormulario.add(emailField, gbc);

			JLabel rolLabel = new JLabel("Rol:");
			gbc.gridx = 0;
			gbc.gridy = 5;
			panelFormulario.add(rolLabel, gbc);

			JTextField rolField = new JTextField();
			rolField.setPreferredSize(campoTamanio);
			String[] opcionesRol = { "", "empleado", "admin" };
			JComboBox<String> rolCombo = new JComboBox<>(opcionesRol);
			gbc.gridx = 1;
			gbc.gridy = 5;
			panelFormulario.add(rolCombo, gbc);

			// Botón "Aplicar Cambios".
			JButton aplicarCambios = new JButton("Aplicar Cambios");
			aplicarCambios.addActionListener(e -> {
				try {
					usuarioEditar.setNombreUsuario(nombreField.getText());
					usuarioEditar.setApellido1(apellido1Field.getText());
					usuarioEditar.setApellido2(apellido2Field.getText());
					usuarioEditar.setTelefono(Integer.parseInt(telefonoField.getText()));
					usuarioEditar.setEmail(emailField.getText());
					usuarioEditar.setRol((String) rolCombo.getSelectedItem());

					usuarioEditar.actualizarUsuario(); // Actualiza en la base de datos.
					JOptionPane.showMessageDialog(formularioEdicion, "Usuario actualizado correctamente.");
					formularioEdicion.dispose();

					// Llamada al callback para actualizar la tabla.
					if (onUpdateCallback != null) {
						onUpdateCallback.run();
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formularioEdicion,
							"Error al actualizar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			});

			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth = 2;
			panelFormulario.add(aplicarCambios, gbc);

			formularioEdicion.add(panelFormulario);
			formularioEdicion.setVisible(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cargar los datos del usuario: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void abrirFormularioAddUsu(Runnable callback) {
		JFrame formularioInsertar = new JFrame("Añadir Usuario");
		formularioInsertar.setSize(400, 600);
		formularioInsertar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		formularioInsertar.setLocationRelativeTo(null);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Dimension campoTamanio = new Dimension(200, 25); // Tamaño para los campos de texto del formulario.

		// Campos del formulario
		JLabel nombreLabel = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(nombreLabel, gbc);

		JTextField nombreField = new JTextField();
		nombreField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(nombreField, gbc);

		JLabel apellido1Label = new JLabel("Apellido 1:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(apellido1Label, gbc);

		JTextField apellido1Field = new JTextField();
		apellido1Field.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(apellido1Field, gbc);

		JLabel apellido2Label = new JLabel("Apellido 2:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelFormulario.add(apellido2Label, gbc);

		JTextField apellido2Field = new JTextField();
		apellido2Field.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 2;
		panelFormulario.add(apellido2Field, gbc);

		JLabel telefonoLabel = new JLabel("Teléfono:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(telefonoLabel, gbc);

		JTextField telefonoField = new JTextField();
		telefonoField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(telefonoField, gbc);

		JLabel emailLabel = new JLabel("Email:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		panelFormulario.add(emailLabel, gbc);

		JTextField emailField = new JTextField();
		emailField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 4;
		panelFormulario.add(emailField, gbc);

		JLabel contrasenaLabel = new JLabel("Contraseña:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		panelFormulario.add(contrasenaLabel, gbc);

		JTextField contrasenaField = new JTextField();
		contrasenaField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 5;
		panelFormulario.add(contrasenaField, gbc);

		JLabel rolLabel = new JLabel("Rol:");
		gbc.gridx = 0;
		gbc.gridy = 6;
		panelFormulario.add(rolLabel, gbc);

		JTextField rolField = new JTextField();
		rolField.setPreferredSize(campoTamanio);
		String[] opcionesRol = { "", "empleado", "admin" };
		JComboBox<String> rolCombo = new JComboBox<>(opcionesRol);
		gbc.gridx = 1;
		gbc.gridy = 6;
		panelFormulario.add(rolCombo, gbc);

		// Botón aplicar cambios
		JButton aplicarCambios = new JButton("Añadir usuario");
		aplicarCambios.addActionListener(e -> {
			try {
				Usuario nuevoUsuario = new Usuario();
				nuevoUsuario.setNombreUsuario(nombreField.getText());
				nuevoUsuario.setApellido1(apellido1Field.getText());
				nuevoUsuario.setApellido2(apellido2Field.getText());
				nuevoUsuario.setTelefono(Integer.parseInt(telefonoField.getText()));
				nuevoUsuario.setEmail(emailField.getText());
				nuevoUsuario.setContrasena(contrasenaField.getText());
				nuevoUsuario.setRol((String) rolCombo.getSelectedItem());

				nuevoUsuario.crearUsuario(); // Llama al método para crear el usuario en la base de datos.
				JOptionPane.showMessageDialog(formularioInsertar, "Usuario creado correctamente.");
				formularioInsertar.dispose();

				if (callback != null) {
					callback.run(); // Ejecuta el callback para recargar la tabla
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(formularioInsertar, "Error al añadir el usuario: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		panelFormulario.add(aplicarCambios, gbc);

		formularioInsertar.add(panelFormulario);
		formularioInsertar.setVisible(true);
	}

	// Formularios editar y añadir CATEGORIAS:

	public void abrirFormularioEdicionCat(Categoria categoriaEditar, Runnable onUpdateCallback) {
		try {
			JFrame formularioEdicion = new JFrame("Editar Categoría");
			formularioEdicion.setSize(400, 600);
			formularioEdicion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			formularioEdicion.setLocationRelativeTo(null);

			JPanel panelFormulario = new JPanel(new GridBagLayout());
			panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(10, 10, 10, 10);
			gbc.fill = GridBagConstraints.HORIZONTAL;

			// Configuración de tamaño estándar para los campos.
			Dimension campoTamanio = new Dimension(200, 25);

			// Campos del formulario para editar los datos.
			JLabel nombreLabel = new JLabel("Nombre:");
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelFormulario.add(nombreLabel, gbc);

			JTextField nombreField = new JTextField(categoriaEditar.getNombreCategoria());
			nombreField.setPreferredSize(campoTamanio);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelFormulario.add(nombreField, gbc);

			JLabel descripcionLabel = new JLabel("Descripción:");
			gbc.gridx = 0; // Columna 0
			gbc.gridy = 1; // Fila 1
			gbc.gridheight = 1; // Ocupa solo una fila
			panelFormulario.add(descripcionLabel, gbc);

			JTextArea descripcionField = new JTextArea(categoriaEditar.getDescripcion()); // Cambiamos a JTextArea para
																							// soportar múltiples líneas
			descripcionField.setLineWrap(true); // Envolver texto automáticamente
			descripcionField.setWrapStyleWord(true); // Envolver por palabras completas
			gbc.gridx = 1; // Columna 1
			gbc.gridy = 1; // Fila 1
			gbc.gridheight = 3; // Ocupa 2 filas
			gbc.fill = GridBagConstraints.BOTH; // Expandir tanto en ancho como en alto
			panelFormulario.add(new JScrollPane(descripcionField), gbc);

			// Botón "Aplicar Cambios".
			JButton aplicarCambios = new JButton("Aplicar Cambios");
			aplicarCambios.addActionListener(e -> {
				try {
					categoriaEditar.setNombreCategoria(nombreField.getText());
					categoriaEditar.setDescripcion(descripcionField.getText());

					categoriaEditar.actualizarCategoria(); // Actualiza en la base de datos.
					JOptionPane.showMessageDialog(formularioEdicion, "Categoría actualizada correctamente.");
					formularioEdicion.dispose();

					// Llamada al callback para actualizar la tabla.
					if (onUpdateCallback != null) {
						onUpdateCallback.run();
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(formularioEdicion,
							"Error al actualizar la categoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			});

			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth = 2;
			panelFormulario.add(aplicarCambios, gbc);

			formularioEdicion.add(panelFormulario);
			formularioEdicion.setVisible(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cargar los datos del usuario: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void abrirFormularioAddCat(Runnable callback) {
		JFrame formularioInsertar = new JFrame("Añadir Usuario");
		formularioInsertar.setSize(400, 600);
		formularioInsertar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		formularioInsertar.setLocationRelativeTo(null);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Dimension campoTamanio = new Dimension(200, 25); // Tamaño para los campos de texto del formulario.

		// Campos del formulario
		JLabel nombreLabel = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(nombreLabel, gbc);

		JTextField nombreField = new JTextField();
		nombreField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(nombreField, gbc);

		JLabel descripcionLabel = new JLabel("Descripcion:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(descripcionLabel, gbc);

		JTextField descripcionField = new JTextField();
		descripcionField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelFormulario.add(descripcionField, gbc);

		// Botón aplicar cambios
		JButton aplicarCambios = new JButton("Añadir categoría");
		aplicarCambios.addActionListener(e -> {
			try {
				Categoria nuevaCategoria = new Categoria();
				nuevaCategoria.setNombreCategoria(nombreField.getText());
				nuevaCategoria.setDescripcion(descripcionField.getText());

				nuevaCategoria.crearCategoria(); // Llama al método para crear la categoria en la base de datos.
				JOptionPane.showMessageDialog(formularioInsertar, "Categoria creada correctamente.");
				formularioInsertar.dispose();

				if (callback != null) {
					callback.run(); // Ejecuta el callback para recargar la tabla
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(formularioInsertar, "Error al añadir la categoría: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		panelFormulario.add(aplicarCambios, gbc);

		formularioInsertar.add(panelFormulario);
		formularioInsertar.setVisible(true);
	}

	public void abrirFormularioMovimiento(Producto productoEditar, Runnable onUpdateCallback) {
		JFrame formularioEdicion = new JFrame("Movimiento de Producto");
		formularioEdicion.setSize(400, 600);
		formularioEdicion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		formularioEdicion.setLocationRelativeTo(null);

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Dimension campoTamanio = new Dimension(200, 25);

		// Campo "Nombre".
		JLabel nombreLabel = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(nombreLabel, gbc);

		JTextField nombreField = new JTextField(productoEditar.getNombreProducto());
		nombreField.setPreferredSize(campoTamanio);
		nombreField.setEditable(false); // Campo no editable
		nombreField.setBorder(BorderFactory.createLineBorder(Color.black));
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelFormulario.add(nombreField, gbc);

		// Campo "Descripción".
		JLabel descripcionLabel = new JLabel("Descripción:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelFormulario.add(descripcionLabel, gbc);

		JTextArea descripcionField = new JTextArea(productoEditar.getDescripcion());
		descripcionField.setLineWrap(true);
		descripcionField.setWrapStyleWord(true);
		descripcionField.setEditable(false); // Campo no editable
		descripcionField.setBackground(panelFormulario.getBackground());
		JScrollPane scrollDescripcion = new JScrollPane(descripcionField);
		scrollDescripcion.setBorder(BorderFactory.createLineBorder(Color.black)); // Borde opcional
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		panelFormulario.add(scrollDescripcion, gbc);

		// Restablecer gridheight y fill.
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Campo "Stock".
		JLabel stockLabel = new JLabel("Stock:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelFormulario.add(stockLabel, gbc);

		JTextField stockField = new JTextField(String.valueOf(productoEditar.getStock()));
		stockField.setPreferredSize(campoTamanio);
		stockField.setEditable(false); // Campo no editable
		stockField.setBorder(BorderFactory.createLineBorder(Color.black));
		gbc.gridx = 1;
		gbc.gridy = 3;
		panelFormulario.add(stockField, gbc);

		// Campo "Tipo de Movimiento".
		JLabel tipoMovimientoLabel = new JLabel("Tipo:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		panelFormulario.add(tipoMovimientoLabel, gbc);

		String[] opcionesMovimiento = { "", "Entrada", "Salida" };
		JComboBox<String> tipoMovimientoCombo = new JComboBox<>(opcionesMovimiento);
		gbc.gridx = 1;
		gbc.gridy = 4;
		panelFormulario.add(tipoMovimientoCombo, gbc);

		// Campo "Cantidad".
		JLabel cantidadLabel = new JLabel("Cantidad:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		panelFormulario.add(cantidadLabel, gbc);

		JTextField cantidadField = new JTextField();
		cantidadField.setPreferredSize(campoTamanio);
		gbc.gridx = 1;
		gbc.gridy = 5;
		panelFormulario.add(cantidadField, gbc);

		// Botón "Aplicar Movimiento".
		JButton aplicarMovimiento = new JButton("Aplicar Movimiento");
		aplicarMovimiento.addActionListener(e -> {
			try {
				// Validar si se seleccionó un tipo de movimiento válido.
				String tipoMovimientoStr = (String) tipoMovimientoCombo.getSelectedItem();
				if (tipoMovimientoStr == null || tipoMovimientoStr.isBlank()) {
					JOptionPane.showMessageDialog(formularioEdicion, "Debes indicar un tipo de movimiento válido.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Crear el objeto TipoMovimiento correspondiente
				TipoMovimiento tipoMovimiento = TipoMovimiento.valueOf(tipoMovimientoStr.toUpperCase());

				// Validar si la cantidad es un número válido.
				int cantidad = Integer.parseInt(cantidadField.getText());

				// Ajustar el stock del producto según el movimiento.
				if (tipoMovimiento.equals(TipoMovimiento.salida) && cantidad > productoEditar.getStock()) {
					JOptionPane.showMessageDialog(formularioEdicion, "No hay suficiente stock para esta salida.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Ajustar el stock del producto según el tipo de movimiento.
				if (tipoMovimiento.equals(TipoMovimiento.entrada)) {
					productoEditar.setStock(productoEditar.getStock() + cantidad);
				} else if (tipoMovimiento.equals(TipoMovimiento.salida)) {
					productoEditar.setStock(productoEditar.getStock() - cantidad);
				}

				// Actualizar el producto en la base de datos.
				productoEditar.actualizarProducto();

				// Registrar el movimiento en la tabla historialinventario.
				HistorialInventario historial = new HistorialInventario();
				historial.setProducto(productoEditar); // Establecer el producto correctamente
				historial.setUsuario(UsuarioSesion.getUsuarioActual()); // Obtener el usuario actual
				historial.setCantidad(cantidad);
				historial.setTipoMovimiento(tipoMovimiento); // Establecer el tipo de movimiento correctamente

				// Insertar el movimiento en la base de datos
				historial.crearMovimiento();

				// Mostrar mensaje de éxito y cerrar el formulario.
				JOptionPane.showMessageDialog(formularioEdicion, "Movimiento aplicado correctamente.");
				formularioEdicion.dispose();

				// Ejecutar el callback para actualizar la tabla.
				if (onUpdateCallback != null) {
					onUpdateCallback.run();
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(formularioEdicion, "Error al aplicar el movimiento: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panelFormulario.add(aplicarMovimiento, gbc);

		formularioEdicion.add(panelFormulario);
		formularioEdicion.setVisible(true);
	}

} // Class
