package controller;

import java.util.List;

import model.Categoria;
import service.CategoriaService;

/**
 * Controlador para gestionar las categorias dentro del sistema.
 * 
 * Esta clase actua como intermediario entre la vista y la capa de servicio, 
 * delegando las operaciones CRUD a {@link CategoriaService}.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class CategoriaController {
	
    private CategoriaService categoriaService = new CategoriaService();

    /**
     * Obtiene una lista de todas las categorias almacenadas en la base de datos.
     *
     * @return Lista de categorias disponibles.
     */
    public List<Categoria> listarCategorias() {
        return categoriaService.obtenerTodasCategorias();
    }

    /**
     * Obtiene una categoria especifica segun su identificador.
     *
     * @param id Identificador unico de la categoria.
     * @return La categoria correspondiente al ID, o null si no se encuentra.
     */
    public Categoria obtenerCategoriaPorId(int id) {
        return categoriaService.obtenerCategoriaPorId(id);
    }

    /**
     * Agrega una nueva categoria al sistema.
     *
     * @param categoria Objeto de tipo Categoria a agregar.
     * @throws IllegalArgumentException Si el nombre de la categoria esta vacio.
     */
    public void agregarCategoria(Categoria categoria) {
        categoriaService.agregarCategoria(categoria);
    }

    /**
     * Actualiza una categoria existente en la base de datos.
     *
     * @param categoria Objeto de tipo Categoria con los datos actualizados.
     * @return true si la actualizacion fue exitosa, false en caso contrario.
     * @throws IllegalArgumentException Si el nombre de la categoria esta vacio.
     */
    public boolean actualizarCategoria(Categoria categoria) {
        return categoriaService.actualizarCategoria(categoria);
    }

    /**
     * Elimina una categoria segun su identificador.
     *
     * @param id Identificador de la categoria a eliminar.
     * @return true si la eliminacion fue exitosa, false en caso de error.
     */
    public boolean eliminarCategoria(int id) {
        return categoriaService.eliminarCategoria(id);
    }
}
