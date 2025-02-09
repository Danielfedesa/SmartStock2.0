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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.UsuarioSesion;
import model.Producto;

public class ScreenGInventario extends JFrame {

    private static final long serialVersionUID = 1L;
    private Producto producto; // Controlador que maneja la lógica relacionada con productos.
    private JTable tablaProductos; // Tabla para mostrar la lista de productos.


    public ScreenGInventario(Producto producto) {
        this.producto = producto;

        // Configuración básica de la ventana.
        setTitle("SmartStock - Gestión de inventario");
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
        JLabel tituloLabel = new JLabel("Gestión de inventario", JLabel.CENTER);
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
        botonVolver.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(botonColor.darker(), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
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
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock", "Stock MIN", "ID Cat", "Movimiento"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Solo las columnas de botones son editables.
                return column == 7;
            }
        };

        // Inicialización de la tabla de productos.
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.putClientProperty("terminateEditOnFocusLost", true);
        tablaProductos.setRowHeight(30);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Configuración de ancho fijo para las columnas
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(10);  // Columna ID
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(200); // Columna Nombre
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(400); // Columna Descripcion
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(20); // Columna Precio
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(20); // Columna Stock
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(20); // Columna Stock Minimo
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(10); // Columna ID Categoria
        tablaProductos.getColumnModel().getColumn(7).setPreferredWidth(50); // Botón Movimiento

        // Renderizador para la columna Stock que cambia de color si es menor que el stock mínimo
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel();
            label.setOpaque(true); // Necesario para cambiar el color de fondo

            // Obtener los valores de stock y stock mínimo desde la tabla
            int stock = Integer.parseInt(table.getValueAt(row, 4).toString());
            int stockMinimo = Integer.parseInt(table.getValueAt(row, 5).toString());

            // Si la celda está seleccionada, establecer el color de selección predeterminado
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
            } else {
                // Si el stock es menor que el stock mínimo, cambiar el color de fondo a naranja
                if (stock < stockMinimo) {
                    label.setBackground(Color.ORANGE);
                } else {
                    // Establecer el color predeterminado de fondo
                    label.setBackground(table.getBackground());
                }
            }

            label.setText(value.toString()); // Establecer el valor del stock en la celda
            return label; // Devolver la celda personalizada
        });
        
        // Rellenar la tabla con datos desde el backend.
        cargarDatosTabla(modeloTabla);

        // Renderizador y editor para el botón "Movimiento".
        tablaProductos.getColumnModel().getColumn(7).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JButton botonEditar = new JButton("Movimiento");
            botonEditar.setBackground(botonColor);
            botonEditar.setForeground(textoBotonColor);
            return botonEditar;
        });

        // Renderizador y editor para el botón "Editar".
        tablaProductos.getColumnModel().getColumn(7).setCellEditor(new javax.swing.DefaultCellEditor(new JTextField()) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			    JButton botonEditar = new JButton("Movimiento");
			    botonEditar.setBackground(botonColor); // Configuración de estilo para el botón.
			    botonEditar.setForeground(textoBotonColor);

			    botonEditar.addActionListener(e -> {
			        try {
			            int idProducto = Integer.parseInt(table.getValueAt(row, 0).toString()); // Recuperar el ID del producto.

			            // Recuperar el producto antes de llamar al formulario.
			            Producto productoEditar = producto.recuperarPro(idProducto);

			            // Crear instancia de ScreenFormularios.
			            ScreenFormularios screenFormularios = new ScreenFormularios();

			            // Llamar al método abrirFormularioEdicion pasando el Producto.
			            screenFormularios.abrirFormularioMovimiento(productoEditar, () -> {
			                // Acción adicional: recargar la tabla principal después de cerrar el formulario.
			                DefaultTableModel modeloTabla = (DefaultTableModel) table.getModel();
			                modeloTabla.setRowCount(0); // Limpiar la tabla.
			                cargarDatosTabla(modeloTabla); // Volver a cargar los datos.
			            });
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(null, "Error al recuperar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    });

			    return botonEditar; // Devolver el botón como componente del editor.
			}
        });
        
        // Panel con la tabla.
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(100, 80, 20, 80));


        // Agregar paneles al contenedor principal.
        contenedor.add(panelSuperior, BorderLayout.NORTH);
        contenedor.add(scrollTabla, BorderLayout.CENTER);

        // Añadir el contenedor a la ventana.
        add(contenedor);
    }

    // Método para cargar datos en la tabla desde la base de datos.
    private void cargarDatosTabla(DefaultTableModel modeloTabla) {
        try {
            List<Producto> productos = producto.listarProductos();
            modeloTabla.setRowCount(0); // Limpia todas las filas antes de agregar nuevas
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombreProducto(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getStockMinimo(),
                    producto.getCategoria(),
                    "Movimiento"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Producto producto = new Producto(); // Instancia de Usuario que gestiona la lógica.
            new ScreenGInventario(producto).setVisible(true);
        });
        
    }
   
}