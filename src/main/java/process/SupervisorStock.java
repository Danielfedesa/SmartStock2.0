package process;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Producto;
import view.ScreenGInventario;

/**
 * Clase que implementa un proceso para supervisar el stock de productos y lanza
 * alertas si el stock es bajo.
 * 
 * Esta clase implementa la interfaz 'Runnable' y ejecuta un hilo que verifica
 * periódicamente el stock de los productos en la base de datos. Si se detecta
 * que un producto tiene un stock por debajo del mínimo, se lanza una alerta
 * notificando al usuario.
 * 
 * La clase utiliza un conjunto ('Set') para llevar un control de los productos
 * que ya han generado alertas. Si un producto tiene un stock bajo y ya se ha
 * mostrado una alerta para ese producto, no se volverá a mostrar la misma
 * alerta. Sin embargo, si el stock se recompone por encima del mínimo y luego
 * vuelve a caer por debajo del mínimo, se vuelve a generar una alerta para ese
 * producto.
 * 
 * La alerta se muestra como una ventana emergente con dos opciones: -
 * "Revisar": Abre la pantalla de gestión de inventario para permitir al usuario
 * revisar los productos con stock bajo. - "Ver más tarde": Cierra la ventana
 * sin realizar ninguna acción adicional.
 * 
 * @see Producto
 * @author Daniel Fernandez Sanchez.
 * @version 2.0 02/2025
 */
public class SupervisorStock implements Runnable {

	/**
	 * Metodo ejecutado por el hilo. Realiza la verificación
	 * periodica del stock de los productos.
	 */
	@Override
	public void run() {
		// El conjunto productosAlertados almacena los IDs de los productos que ya han
		// mostrado alerta.
		// Se reinicia al principio de cada ciclo de verificación.
		while (true) {
			Set<Integer> productosAlertados = new HashSet<>(); // Reiniciar el conjunto al inicio de cada ciclo de
																// verificación

			try {
				Producto producto = new Producto(); // Creamos una instancia de Producto para verificar el stock.
				String alerta = producto.verificarStockMinimo(); // Verificamos el stock mínimo de los productos.

				// Si el mensaje de alerta indica que hay productos con stock bajo
				if (!alerta.equals("No hay productos con stock bajo.")) {
					System.out.println("¡Alerta de stock bajo!"); // Imprimimos la alerta en consola.
					System.out.println(alerta);

					// Extraemos el ID del producto con stock bajo desde el mensaje de alerta
					int productoId = obtenerIdDeProductoDesdeAlerta(alerta); // Método auxiliar para extraer el ID

					// Si aún no se ha mostrado alerta para este producto, procedemos con la
					// notificación.
					if (!productosAlertados.contains(productoId)) {
						productosAlertados.add(productoId); // Marcamos este producto como alertado

						// Mostramos una ventana emergente con las opciones "Revisar" y "Ver más tarde"
						SwingUtilities.invokeLater(() -> {
							// Configuración del diálogo con las opciones
							int respuesta = JOptionPane.showOptionDialog(null, "¡Alerta de stock bajo!\n" + alerta, // Mensaje
																													// de
																													// la
																													// alerta
									"Stock Bajo", // Título de la ventana
									JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
									new Object[] { "Revisar", "Ver más tarde" }, // Botones de opciones
									"Ver más tarde" // Opción por defecto
							);

							// Si el usuario selecciona "Revisar", abrimos la pantalla de gestión de
							// inventario.
							if (respuesta == 0) {
								SwingUtilities
										.invokeLater(() -> new ScreenGInventario(new Producto()).setVisible(true));
							}
						});
					}
				} else {
					// Si no hay productos con stock bajo, simplemente imprimimos el mensaje.
					System.out.println(alerta);
				}

				// El hilo espera 20 segundos (20000 milisegundos) antes de realizar la siguiente
				// verificación.
				Thread.sleep(20000); // 20 segundos
			} catch (Exception e) {
				// Capturamos cualquier error y lo mostramos en consola.
				System.err.println("Error en la verificación del stock: " + e.getMessage());
			}
		}
	}

	/**
	 * Metodo auxiliar para extraer el ID del producto desde el mensaje de alerta.
	 * Este método asume que el mensaje de alerta contiene el ID del producto de
	 * manera que se pueda extraer mediante el texto "idProducto=".
	 *
	 * @param alerta El mensaje de alerta que contiene información sobre el
	 *               producto.
	 * @return El ID del producto extraído del mensaje de alerta.
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
