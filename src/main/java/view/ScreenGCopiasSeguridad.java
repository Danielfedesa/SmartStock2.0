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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.CopiaSeguridad;

public class ScreenGCopiasSeguridad extends JFrame {

    private static final long serialVersionUID = 1L;
    private CopiaSeguridad copia;
    private JTable tablaCopiasSeguridad;

    public ScreenGCopiasSeguridad(CopiaSeguridad copia) {
        this.copia = copia;

        // Configuración básica de la ventana.
        setTitle("SmartStock - Gestión de Backups");
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
        JLabel tituloLabel = new JLabel("Gestión de Backups", JLabel.CENTER);
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
        String[] columnas = {"ID", "Fecha y hora", "Ruta de almacenamiento"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        // Inicialización de la tabla de usuarios.
        tablaCopiasSeguridad = new JTable(modeloTabla);
        tablaCopiasSeguridad.setRowHeight(30);
        tablaCopiasSeguridad.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaCopiasSeguridad.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Configuración de ancho fijo para las columnas
        tablaCopiasSeguridad.getColumnModel().getColumn(0).setPreferredWidth(10);  // Columna ID
        tablaCopiasSeguridad.getColumnModel().getColumn(1).setPreferredWidth(100); // Columna Fecha
        tablaCopiasSeguridad.getColumnModel().getColumn(2).setPreferredWidth(700); // Columna Ruta

        // Rellenar la tabla con datos desde el backend.
        cargarDatosTabla(modeloTabla);

        // Panel con la tabla.
        JScrollPane scrollTabla = new JScrollPane(tablaCopiasSeguridad);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

        // Añadir el panel con la tabla al contenedor principal.
        contenedor.add(scrollTabla, BorderLayout.CENTER);

        // Agregar el contenedor principal a la ventana.
        add(contenedor);
    }

    // Método para cargar datos en la tabla desde la base de datos.
    private void cargarDatosTabla(DefaultTableModel modeloTabla) {
        try {
            List<CopiaSeguridad> copias = copia.listarCopias();
            for (CopiaSeguridad copia : copias) {
                modeloTabla.addRow(new Object[]{
                    String.valueOf(copia.getIdBackup()),
                    copia.getFechaBackup(),
                    copia.getRutaArchivo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el listado de backups: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para probar la vista de la pantalla de copias de seguridad.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CopiaSeguridad copias = new CopiaSeguridad();
            new ScreenGCopiasSeguridad(copias).setVisible(true);
        });
    }
}
