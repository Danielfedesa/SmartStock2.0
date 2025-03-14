package service;

import java.util.List;

import model.HistorialInventario;
import repository.HistorialInventarioRepository;

public class HistorialInventarioService {
	
    private final HistorialInventarioRepository historialInventarioRepository;

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
