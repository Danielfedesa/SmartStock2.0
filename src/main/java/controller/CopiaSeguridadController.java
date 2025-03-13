package controller;

import java.util.List;

import model.CopiaSeguridad;
import service.CopiaSeguridadService;

public class CopiaSeguridadController {
    private CopiaSeguridadService copiaSeguridadService;

    public CopiaSeguridadController() {
        this.copiaSeguridadService = new CopiaSeguridadService();
    }

    public void realizarBackup() {
        copiaSeguridadService.realizarBackup();
    }

    public List<CopiaSeguridad> listarCopias() {
        return copiaSeguridadService.listarCopias();
    }
}
