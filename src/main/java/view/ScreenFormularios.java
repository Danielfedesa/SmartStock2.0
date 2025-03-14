package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Timestamp;

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

import controller.CategoriaController;
import controller.HistorialInventarioController;
import controller.ProductoController;
import controller.UsuarioController;
import controller.UsuarioSesion;
import model.Categoria;
import model.HistorialInventario;
import model.HistorialInventario.TipoMovimiento;
import model.Producto;
import model.Usuario;

/**
 * Clase que contiene diferentes formularios para la gestion de productos, 
 * usuarios, inventario y categorias en SmartStock.
 * 
 * <p>
 * Proporciona interfaces de formulario para crear y editar productos, usuarios, categorias
 * y registrar movimientos de inventario.
 * </p>
 * 
 * @see Producto
 * @see Usuario
 * @see Categoria
 * @see HistorialInventario
 * @author Daniel Fernandez Sanchez
 * @version 3.0 03/2025
 */
public class ScreenFormularios {
	
	@SuppressWarnings("unused")
	private final ProductoController productoController;
	@SuppressWarnings("unused")
	private final UsuarioController usuarioController;
	@SuppressWarnings("unused")
	private final CategoriaController categoriaController;
	
	public ScreenFormularios() {
		this.productoController = new ProductoController();
		this.usuarioController = new UsuarioController();
		this.categoriaController = new CategoriaController();
	}

	/**
	 * Abre un formulario para editar un producto.
	 * Configura el formulario con los campos necesarios para editar
	 * @param productoEditar Producto a editar.
	 * @param onUpdateCallback Callback que se ejecuta despues de editar el producto para actualizar la tabla con los nuevos datos.
	 */
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

	    // Botón para aplicar cambios
	    JButton aplicarCambios = new JButton("Aplicar Cambios");
	    aplicarCambios.addActionListener(e -> {
	        try {
	            ProductoController productoController = new ProductoController();
	            CategoriaController categoriaController = new CategoriaController();

	            int idCategoria = Integer.parseInt(categoriaField.getText());
	            Categoria categoria = categoriaController.obtenerCategoriaPorId(idCategoria);

	            if (categoria == null) {
	                JOptionPane.showMessageDialog(formularioEdicion, "La categoría no existe.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            productoEditar.setNombreProducto(nombreField.getText());
	            productoEditar.setDescripcion(descripcionField.getText());
	            productoEditar.setPrecio(Double.parseDouble(precioField.getText()));
	            productoEditar.setStock(Integer.parseInt(stockField.getText()));
	            productoEditar.setStockMinimo(Integer.parseInt(stockMinField.getText()));
	            productoEditar.setCategoria(categoria);

	            boolean actualizado = productoController.actualizarProducto(productoEditar);

	            if (actualizado) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Producto actualizado correctamente.");
	                formularioEdicion.dispose();
	                if (onUpdateCallback != null) {
	                    onUpdateCallback.run();
	                }
	            } else {
	                JOptionPane.showMessageDialog(formularioEdicion, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
	            }

	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(formularioEdicion, "Error en los valores ingresados. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(formularioEdicion, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

	/**
	 * Abre un formulario para agregar un nuevo producto. Configura el formulario
	 * con los campos necesarios para agregar el producto.
	 * 
	 * @param Callback que se ejecuta despues de agregar el producto para actualizar la tabla con los nuevos datos.
	 */
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
	            ProductoController productoController = new ProductoController();
	            CategoriaController categoriaController = new CategoriaController();

	            // Verificamos que el ID de la categoría sea válido
	            int idCategoria = Integer.parseInt(categoriaField.getText());
	            Categoria categoria = categoriaController.obtenerCategoriaPorId(idCategoria);

	            if (categoria == null) {
	                JOptionPane.showMessageDialog(formularioInsertar, "La categoría no existe.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Crear el nuevo producto con la categoría seleccionada
	            Producto nuevoProducto = new Producto();
	            nuevoProducto.setNombreProducto(nombreField.getText());
	            nuevoProducto.setDescripcion(descripcionField.getText());
	            nuevoProducto.setPrecio(Double.parseDouble(precioField.getText()));
	            nuevoProducto.setStock(Integer.parseInt(stockField.getText()));
	            nuevoProducto.setStockMinimo(Integer.parseInt(stockMinField.getText()));
	            nuevoProducto.setCategoria(categoria);

	            // Llamamos al controlador para agregar el producto
	            productoController.agregarProducto(nuevoProducto);

	            JOptionPane.showMessageDialog(formularioInsertar, "Producto añadido correctamente.");
	            formularioInsertar.dispose();

	            if (callback != null) {
	                callback.run(); // Ejecuta el callback para recargar la tabla
	            }

	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(formularioInsertar, "Error en los campos: Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(formularioInsertar, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });

	    gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridwidth = 2;
	    panelFormulario.add(aplicarCambios, gbc);

	    formularioInsertar.add(panelFormulario);
	    formularioInsertar.setVisible(true);
	}


	/**
	 * Abre un formulario para editar un usuario.
	 * Configura el formulario con los campos necesarios para editar el usuario.
	 * @param usuarioEditar Usuario a editar.
	 * @param onUpdateCallback Callback que se ejecuta despues de editar el usuario para actualizar la tabla con los nuevos datos.
	 */
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

	        // Instancia del controlador
	        UsuarioController usuarioController = new UsuarioController();

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

	        String[] opcionesRol = { "empleado", "admin" };
	        JComboBox<String> rolCombo = new JComboBox<>(opcionesRol);
	        rolCombo.setSelectedItem(usuarioEditar.getRol()); // Selecciona el rol actual del usuario
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

	                // Llamada al controlador para actualizar el usuario
	                usuarioController.actualizarUsuario(usuarioEditar);

	                JOptionPane.showMessageDialog(formularioEdicion, "Usuario actualizado correctamente.");
	                formularioEdicion.dispose();

	                // Llamada al callback para actualizar la tabla.
	                if (onUpdateCallback != null) {
	                    onUpdateCallback.run();
	                }

	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Error: Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Error al actualizar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        gbc.gridx = 0;
	        gbc.gridy = 6;
	        gbc.gridwidth = 2;
	        panelFormulario.add(aplicarCambios, gbc);

	        formularioEdicion.add(panelFormulario);
	        formularioEdicion.setVisible(true);

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar los datos del usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


	/**
	 * Abre un formulario para agregar un nuevo usuario.
	 * Configura el formulario con los campos necesarios para agregar el usuario.
	 * @param Callback que se ejecuta después de agregar la categoria para actualizar la tabla con los nuevos datos.
	 */
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

	    // Instancia del controlador
	    UsuarioController usuarioController = new UsuarioController();

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

	    String[] opcionesRol = { "empleado", "admin" };
	    JComboBox<String> rolCombo = new JComboBox<>(opcionesRol);
	    gbc.gridx = 1;
	    gbc.gridy = 6;
	    panelFormulario.add(rolCombo, gbc);

	    // Botón aplicar cambios
	    JButton aplicarCambios = new JButton("Añadir usuario");
	    aplicarCambios.addActionListener(e -> {
	        try {
	            // Validaciones
	            if (nombreField.getText().trim().isEmpty() || 
	                apellido1Field.getText().trim().isEmpty() || 
	                telefonoField.getText().trim().isEmpty() || 
	                emailField.getText().trim().isEmpty() || 
	                contrasenaField.getText().trim().isEmpty() || 
	                rolCombo.getSelectedItem() == null) {
	                JOptionPane.showMessageDialog(formularioInsertar, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Creación del usuario con datos ingresados
	            Usuario nuevoUsuario = new Usuario(
	                nombreField.getText(),
	                apellido1Field.getText(),
	                apellido2Field.getText(),
	                Integer.parseInt(telefonoField.getText()),
	                emailField.getText(),
	                contrasenaField.getText(),
	                (String) rolCombo.getSelectedItem()
	            );

	            // Llamada al controlador para agregar el usuario
	            usuarioController.crearUsuario(nuevoUsuario);

	            JOptionPane.showMessageDialog(formularioInsertar, "Usuario creado correctamente.");
	            formularioInsertar.dispose();

	            if (callback != null) {
	                callback.run(); // Ejecuta el callback para recargar la tabla
	            }

	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(formularioInsertar, "Error: Ingrese un número de teléfono válido.", "Error", JOptionPane.ERROR_MESSAGE);
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(formularioInsertar, "Error al añadir el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });

	    gbc.gridx = 0;
	    gbc.gridy = 7;
	    gbc.gridwidth = 2;
	    panelFormulario.add(aplicarCambios, gbc);

	    formularioInsertar.add(panelFormulario);
	    formularioInsertar.setVisible(true);
	}


	/**
	 * Abre un formulario para editar las categorias.
	 * Configura el formulario con los campos necesarios para editar la categoria.
	 * @param categoriaEditar Categoria a editar.
	 * @param Callback que se ejecuta después de agregar la categoria para actualizar la tabla con los nuevos datos.
	 */
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

	        // Instancia del controlador
	        CategoriaController categoriaController = new CategoriaController();

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
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        panelFormulario.add(descripcionLabel, gbc);

	        JTextArea descripcionField = new JTextArea(categoriaEditar.getDescripcion());
	        descripcionField.setLineWrap(true);
	        descripcionField.setWrapStyleWord(true);
	        JScrollPane scrollDescripcion = new JScrollPane(descripcionField);
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        gbc.gridheight = 3;
	        gbc.fill = GridBagConstraints.BOTH;
	        panelFormulario.add(scrollDescripcion, gbc);

	        // Restablecer gridheight y fill.
	        gbc.gridheight = 1;
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        // Botón "Aplicar Cambios".
	        JButton aplicarCambios = new JButton("Aplicar Cambios");
	        aplicarCambios.addActionListener(e -> {
	            try {
	                // Validaciones
	                if (nombreField.getText().trim().isEmpty() || descripcionField.getText().trim().isEmpty()) {
	                    JOptionPane.showMessageDialog(formularioEdicion, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                // Se actualizan los datos de la categoría
	                categoriaEditar.setNombreCategoria(nombreField.getText());
	                categoriaEditar.setDescripcion(descripcionField.getText());

	                // Se usa el controlador para actualizar en la base de datos
	                categoriaController.actualizarCategoria(categoriaEditar);

	                JOptionPane.showMessageDialog(formularioEdicion, "Categoría actualizada correctamente.");
	                formularioEdicion.dispose();

	                // Llamada al callback para actualizar la tabla.
	                if (onUpdateCallback != null) {
	                    onUpdateCallback.run();
	                }

	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Error al actualizar la categoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        gbc.gridx = 0;
	        gbc.gridy = 6;
	        gbc.gridwidth = 2;
	        panelFormulario.add(aplicarCambios, gbc);

	        formularioEdicion.add(panelFormulario);
	        formularioEdicion.setVisible(true);

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar los datos de la categoría: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	      }
	    }


	/**
	 * Abre un formulario para agregar una nueva categoria.
	 * Configura el formulario con los campos necesarios para agregar la categoria.
	 * @param Callback que se ejecuta después de agregar la categoria para actualizar la tabla con los nuevos datos.
	 */
	public void abrirFormularioAddCat(Runnable callback) {
	    JFrame formularioInsertar = new JFrame("Añadir Categoría");
	    formularioInsertar.setSize(400, 600);
	    formularioInsertar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    formularioInsertar.setLocationRelativeTo(null);

	    JPanel panelFormulario = new JPanel(new GridBagLayout());
	    panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    Dimension campoTamanio = new Dimension(200, 25); // Tamaño para los campos de texto del formulario.

	    // Instancia del controlador
	    CategoriaController categoriaController = new CategoriaController();

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

	    // Botón "Añadir categoría"
	    JButton aplicarCambios = new JButton("Añadir categoría");
	    aplicarCambios.addActionListener(e -> {
	        try {
	            // Validaciones
	            if (nombreField.getText().trim().isEmpty() || descripcionField.getText().trim().isEmpty()) {
	                JOptionPane.showMessageDialog(formularioInsertar, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Crear nueva categoría
	            Categoria nuevaCategoria = new Categoria();
	            nuevaCategoria.setNombreCategoria(nombreField.getText());
	            nuevaCategoria.setDescripcion(descripcionField.getText());

	            // Guardar la categoría en la base de datos mediante el controlador
	            categoriaController.agregarCategoria(nuevaCategoria);

	            JOptionPane.showMessageDialog(formularioInsertar, "Categoría creada correctamente.");
	            formularioInsertar.dispose();

	            // Ejecutar el callback para recargar la tabla
	            if (callback != null) {
	                callback.run();
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


	/**
     * Abre un formulario para realizar un movimiento de un producto (entrada o salida).
     * Cada movimiento se registra en la tabla historialinventario.
     * Configura el formulario con los campos necesarios para realizar el movimiento.
     * 
     * @param productoEditar Producto a editar (stock).
     * @param Callback que se ejecuta despues de agregar el producto para actualizar la tabla con los nuevos datos.
     */
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

	    // Instancias de los controladores
	    ProductoController productoController = new ProductoController();
	    HistorialInventarioController historialController = new HistorialInventarioController();

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
	            if (tipoMovimientoStr == null || tipoMovimientoStr.trim().isEmpty()) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Debes indicar un tipo de movimiento válido.",
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Verificar qué se está pasando realmente como tipo de movimiento
	            System.out.println("Tipo de movimiento seleccionado: " + tipoMovimientoStr);

	            // Convertir el tipo de movimiento a Enum asegurando que coincida con el formato del Enum
	            TipoMovimiento tipoMovimiento;
	            try {
	                tipoMovimiento = TipoMovimiento.valueOf(tipoMovimientoStr.trim().toUpperCase());
	            } catch (IllegalArgumentException ex) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Error: Tipo de movimiento no válido.",
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Validar si la cantidad es un número válido.
	            int cantidad = Integer.parseInt(cantidadField.getText().trim());
	            if (cantidad <= 0) {
	                JOptionPane.showMessageDialog(formularioEdicion, "La cantidad debe ser mayor a 0.",
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Ajustar el stock del producto según el movimiento.
	            int nuevoStock;
	            if (tipoMovimiento == TipoMovimiento.ENTRADA) {
	                nuevoStock = productoEditar.getStock() + cantidad;
	            } else { // TipoMovimiento.SALIDA
	                if (cantidad > productoEditar.getStock()) {
	                    JOptionPane.showMessageDialog(formularioEdicion, "No hay suficiente stock para esta salida.",
	                            "Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                nuevoStock = productoEditar.getStock() - cantidad;
	            }

	            // Actualizar el stock del producto usando el controlador
	            boolean actualizado = productoController.actualizarStock(productoEditar.getIdProducto(), nuevoStock);
	            if (!actualizado) {
	                JOptionPane.showMessageDialog(formularioEdicion, "Error al actualizar el stock en la base de datos.",
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Registrar el movimiento en el historial
	            HistorialInventario nuevoMovimiento = new HistorialInventario(
	                    productoEditar,
	                    UsuarioSesion.getUsuarioActual(),
	                    cantidad,
	                    tipoMovimiento,
	                    new Timestamp(System.currentTimeMillis()) // Fecha actual
	            );
	            historialController.agregarMovimiento(nuevoMovimiento);

	            // Mostrar mensaje de éxito y cerrar el formulario.
	            JOptionPane.showMessageDialog(formularioEdicion, "Movimiento aplicado correctamente.");
	            formularioEdicion.dispose();

	            // Ejecutar el callback para actualizar la tabla.
	            if (onUpdateCallback != null) {
	                onUpdateCallback.run();
	            }

	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(formularioEdicion, "Error: La cantidad debe ser un número válido.",
	                    "Error", JOptionPane.ERROR_MESSAGE);
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
