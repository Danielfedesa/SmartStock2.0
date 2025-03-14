package controller;

import java.util.List;

import model.CopiaSeguridad;
import service.CopiaSeguridadService;

/**
 * Controlador para gestionar las copias de seguridad dentro del sistema.
 * 
 * Este controlador actua como intermediario entre la vista y la capa de servicio,
 * delegando las operaciones relacionadas con las copias de seguridad a {@link CopiaSeguridadService}.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class CopiaSeguridadController {
	
    private CopiaSeguridadService copiaSeguridadService;

    /**
     * Constructor que inicializa el servicio de copias de seguridad.
     */
    public CopiaSeguridadController() {
        this.copiaSeguridadService = new CopiaSeguridadService();
    }

    /**
     * Realiza una nueva copia de seguridad de la base de datos.
     * 
     * Esta operacion se encarga de generar un archivo de respaldo de la base de datos
     * y almacenarlo en la ubicacion predeterminada.
     * 
     */
    public void realizarBackup() {
        copiaSeguridadService.realizarBackup();
    }

    /**
     * Lista todas las copias de seguridad almacenadas en el sistema.
     *
     * @return Lista de objetos {@link CopiaSeguridad} representando los respaldos disponibles.
     */
    public List<CopiaSeguridad> listarCopias() {
        return copiaSeguridadService.listarCopias();
    }
}
