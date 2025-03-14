package service;

import java.util.List;

import model.Categoria;
import repository.CategoriaRepository;

/**
 * Servicio para gestionar las operaciones de la entidad Categoria.
 * 
 * Esta clase actua como intermediario entre el controlador y el repositorio,
 * aplicando reglas de negocio antes de interactuar con la base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class CategoriaService {
	
	/**
     * Constructor que inicializa el repositorio de categorias.
     */
	private CategoriaRepository categoriaRepository = new CategoriaRepository();
	
	/**
     * Obtiene una lista de todas las categorias almacenadas en la base de datos.
     * 
     * @return Lista de objetos {@link Categoria}.
     */
	public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.listar();
    }

	/**
     * Busca una categoria en la base de datos por su ID.
     * 
     * @param id ID de la categoria a buscar.
     * @return Objeto {@link Categoria} si se encuentra, de lo contrario retorna {@code null}.
     */
    public Categoria obtenerCategoriaPorId(int id) {
        return categoriaRepository.leerCategoria(id);
    }

    /**
     * Agrega una nueva categoria a la base de datos.
     * <p>
     * Se valida que el nombre de la categoria no este vacio antes de proceder con la insercion.
     * </p>
     * 
     * @param categoria Objeto {@link Categoria} a insertar.
     * @throws IllegalArgumentException Si el nombre de la categoria esta vacio.
     */
    public void agregarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        categoriaRepository.insertar(categoria);
    }

    /**
     * Actualiza los datos de una categoria en la base de datos.
     * 
     * Se valida que el nombre de la categoria no este vacio antes de proceder con la actualizacion.
     * 
     * @param categoria Objeto {@link Categoria} con los datos actualizados.
     * @return {@code true} si la actualizacion fue exitosa, {@code false} en caso contrario.
     * @throws IllegalArgumentException Si el nombre de la categoria esta vacio.
     */
    public boolean actualizarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        return categoriaRepository.actualizar(categoria);
    }

    /**
     * Elimina una categoria de la base de datos por su ID.
     * 
     * @param id ID de la categoria a eliminar.
     * @return {@code true} si la eliminacion fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminarCategoria(int id) {
        return categoriaRepository.eliminar(id);
    }
}
