package service;

import java.util.List;

import DAO.DaoHistorialInventario;
import model.HistorialInventario;

public class HistorialInventarioService {
	
    private final DaoHistorialInventario daoHistorialInventario;

    public HistorialInventarioService() {
        this.daoHistorialInventario = new DaoHistorialInventario();
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
        daoHistorialInventario.insertar(movimiento);
    }

    /**
     * Obtiene la lista de movimientos del inventario.
     *
     * @return Lista de movimientos del historial de inventario.
     */
    public List<HistorialInventario> listarMovimientos() {
        return daoHistorialInventario.listar();
    }
}
