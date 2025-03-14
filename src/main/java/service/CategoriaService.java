package service;

import java.util.List;

import model.Categoria;
import repository.CategoriaRepository;

public class CategoriaService {
	
	private CategoriaRepository categoriaRepository = new CategoriaRepository();
	
	public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.listar();
    }

    public Categoria obtenerCategoriaPorId(int id) {
        return categoriaRepository.leerCategoria(id);
    }

    public void agregarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        categoriaRepository.insertar(categoria);
    }

    public boolean actualizarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        return categoriaRepository.actualizar(categoria);
    }

    public boolean eliminarCategoria(int id) {
        return categoriaRepository.eliminar(id);
    }
}
