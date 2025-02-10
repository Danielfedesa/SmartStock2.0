package process;

import model.Producto;

/**
 * Clase que implementa un proceso para supervisar el stock de productos y
 * lanzar alertas si el stock es bajo.
 * 
 * Esta clase implementa la interfaz 'Runnable' y ejecuta un hilo que verifica
 * periodicamente el stock de los productos.
 * 
 * @see Producto
 * @author Daniel Fernandez Sanchez.
 * @version 2.0 02/2025
 */
public class SupervisorStock implements Runnable {

	/**
	 * Metodo que ejecuta el proceso de supervision de stock en un hilo. Verifica el
	 * stock de los productos a intervalos regulares y emite una alerta si el stock
	 * de algun producto está por debajo del umbral minimo establecido.
	 * 
	 * Si no se detecta ningun problema de stock, se imprime un mensaje indicando
	 * que no hay productos con stock bajo.
	 * 
	 * En caso de que ocurra un error durante la verificación, se captura la
	 * excepcion y se muestra un mensaje de error.
	 */
	@Override
	public void run() {

		while (true) {
			try {
				Producto producto = new Producto();
				String alerta = producto.verificarStockMinimo();

				if (!alerta.equals("No hay productos con stock bajo.")) {
					System.out.println("¡Alerta de stock bajo!");
					System.out.println(alerta);
				} else {
					System.out.println(alerta); // Imprime mensaje si no hay productos con stock bajo
				}

				Thread.sleep(60000);
			} catch (Exception e) {
				System.err.println("Error en la verificación del stock: " + e.getMessage());
			}
		}
	} // Fin proceso.

} // Class
