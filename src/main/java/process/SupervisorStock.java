package process;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Producto;

/**
 * Clase que supervisa el stock de los productos en la base de datos.
 * 
 * <p>
 * Esta clase implementa la interfaz {@code Runnable} y ejecuta un hilo que
 * verifica periódicamente el stock de los productos. Si se detecta que un
 * producto tiene un stock por debajo del mínimo, se lanza una alerta
 * notificando al usuario.
 * </p>
 * 
 * <p>
 * Se utiliza un conjunto {@code Set<Integer>} para llevar un control de los
 * productos que ya han generado alertas. Si un producto ya generó una alerta,
 * esta no se repetirá hasta que el stock se recupere por encima del mínimo y
 * vuelva a caer.
 * </p>
 * 
 * <p>
 * La alerta se muestra en una ventana emergente mediante {@code JOptionPane}.
 * </p>
 * 
 * @see Producto
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class SupervisorStock implements Runnable {

	// El conjunto productosAlertados almacena los IDs de los productos que ya han
	// mostrado alerta.
	// Este conjunto persiste a lo largo de la ejecución del hilo, evitando
	// que las alertas se repitan.
	/**
	 * Conjunto que almacena los IDs de los productos que ya han generado una
	 * alerta.
	 */
	private Set<Integer> productosAlertados = new HashSet<>();

	/**
	 * Metodo ejecutado por el hilo. Realiza la verificación periodica del stock de
	 * los productos.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Producto producto = new Producto(); // Instancia para acceder a los métodos de stock.
				String alerta = producto.verificarStockMinimo(); // Verificamos el stock mínimo.

				// Si hay productos con stock bajo
				if (!alerta.equals("No hay productos con stock bajo.")) {
					System.out.println("¡Alerta de stock bajo!");
					System.out.println(alerta);

					// Extraer el ID del producto con stock bajo
					int productoId = obtenerIdDeProductoDesdeAlerta(alerta);

					// Si el producto aún no ha sido alertado, mostramos la notificación
					if (productoId != -1 && !productosAlertados.contains(productoId)) {
						productosAlertados.add(productoId); // Marcar producto como alertado

						// Mostrar alerta en una ventana emergente
						SwingUtilities.invokeLater(() -> mostrarAlerta(alerta));
					}
				} else {
					// Si no hay productos con stock bajo, imprimimos el mensaje en consola.
					System.out.println(alerta);
				}

				// Esperar 20 segundos antes de la siguiente verificación
				Thread.sleep(20000);
			} catch (Exception e) {
				System.err.println("Error en la verificación del stock: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Muestra una alerta en una ventana emergente.
	 * 
	 * @param mensaje Mensaje de alerta a mostrar.
	 */
	private void mostrarAlerta(String mensaje) {
		JOptionPane.showOptionDialog(null, "¡Alerta de stock bajo!\n" + mensaje, // Mensaje
				"Stock Bajo", // Título de la ventana
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] { "Aceptar" }, // Botón de la ventana
				"Aceptar");
	}

	/**
	 * Extrae el ID del producto desde el mensaje de alerta.
	 * 
	 * <p>
	 * Se asume que el mensaje de alerta contiene el ID del producto con la
	 * estructura "idProducto=XX", donde XX es el identificador del producto.
	 * </p>
	 * 
	 * @param alerta Mensaje de alerta que contiene la información del producto.
	 * @return ID del producto extraído o -1 si no se encuentra un ID válido.
	 */
	private int obtenerIdDeProductoDesdeAlerta(String alerta) {
		// Inicializamos la variable para almacenar el ID del producto.
		int idProducto = 0;

		// Dividimos el mensaje de alerta para encontrar el ID del producto.
		String[] partes = alerta.split("idProducto=");
		if (partes.length > 1) {
			// Extraemos el ID y lo convertimos a entero
			String idStr = partes[1].split(",")[0].trim();
			idProducto = Integer.parseInt(idStr);
		}

		// Devolvemos el ID extraído.
		return idProducto;
	}
}
