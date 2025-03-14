package controller;

import java.util.List;

import model.HistorialInventario;
import service.HistorialInventarioService;

/**
 * Controlador para gestionar los movimientos del historial de inventario.
 * 
 * Actua como intermediario entre la vista y la capa de servicio,
 * delegando las operaciones relacionadas con el historial de inventario
 * a {@link HistorialInventarioService}.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class HistorialInventarioController {
    private final HistorialInventarioService historialInventarioService;

    /**
     * Constructor que inicializa el servicio de historial de inventario.
     */
    public HistorialInventarioController() {
        this.historialInventarioService = new HistorialInventarioService();
    }

    /**
     * Agrega un nuevo movimiento al historial de inventario.
     * 
     * Este metodo registra un nuevo movimiento en la base de datos,
     * ya sea una entrada o salida de stock.
     *
     * @param movimiento Objeto {@link HistorialInventario} que representa el movimiento a registrar.
     */
    public void agregarMovimiento(HistorialInventario movimiento) {
        historialInventarioService.agregarMovimiento(movimiento);
    }
    
    /**
     * Obtiene la lista completa de movimientos registrados en el historial de inventario.
     *
     * @return Lista de objetos {@link HistorialInventario} con todos los movimientos almacenados.
     */
    public List<HistorialInventario> listarMovimientos() {
        return historialInventarioService.listarMovimientos();
    }
}
