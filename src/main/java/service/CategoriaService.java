package service;

import java.util.List;

import DAO.DaoCategoria;
import model.Categoria;

public class CategoriaService {
	
	private DaoCategoria daoCategoria = new DaoCategoria();
	
	public List<Categoria> obtenerTodasCategorias() {
        return daoCategoria.listar();
    }

    public Categoria obtenerCategoriaPorId(int id) {
        return daoCategoria.leerCategoria(id);
    }

    public void agregarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        daoCategoria.insertar(categoria);
    }

    public boolean actualizarCategoria(Categoria categoria) {
        if (categoria.getNombreCategoria().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        return daoCategoria.actualizar(categoria);
    }

    public boolean eliminarCategoria(int id) {
        return daoCategoria.eliminar(id);
    }
}
