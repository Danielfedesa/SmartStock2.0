package service;

import java.util.List;

import model.HistorialInventario;
import repository.HistorialInventarioRepository;

/**
 * Servicio para gestionar las operaciones de la entidad HistorialInventario.
 * 
 * Esta clase actua como intermediario entre el controlador y el repositorio,
 * aplicando reglas de negocio antes de interactuar con la base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class HistorialInventarioService {
	
    private final HistorialInventarioRepository historialInventarioRepository;

	/**
	 * Constructor que inicializa el repositorio de historial de inventario.
	 */
    public HistorialInventarioService() {
        this.historialInventarioRepository = new HistorialInventarioRepository();
    }

    /**
     * Agrega un nuevo movimiento al historial de inventario.
     *
     * @param movimiento Objeto de tipo HistorialInventario a insertar.
     */
    public void agregarMovimiento(HistorialInventario movimiento) {
        if (movimiento.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        historialInventarioRepository.insertar(movimiento);
    }

    /**
     * Obtiene la lista de movimientos del inventario.
     *
     * @return Lista de movimientos del historial de inventario.
     */
    public List<HistorialInventario> listarMovimientos() {
        return historialInventarioRepository.listar();
    }
}
