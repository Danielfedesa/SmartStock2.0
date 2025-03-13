package controller;

import java.util.List;

import model.HistorialInventario;
import service.HistorialInventarioService;

public class HistorialInventarioController {
    private final HistorialInventarioService historialInventarioService;

    public HistorialInventarioController() {
        this.historialInventarioService = new HistorialInventarioService();
    }

    public void agregarMovimiento(HistorialInventario movimiento) {
        historialInventarioService.agregarMovimiento(movimiento);
    }

    public List<HistorialInventario> listarMovimientos() {
        return historialInventarioService.listarMovimientos();
    }
}
