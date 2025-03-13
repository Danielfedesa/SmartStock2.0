package controller;

import java.util.List;

import model.Categoria;
import service.CategoriaService;

public class CategoriaController {
    private CategoriaService categoriaService = new CategoriaService();

    public List<Categoria> listarCategorias() {
        return categoriaService.obtenerTodasCategorias();
    }

    public Categoria obtenerCategoriaPorId(int id) {
        return categoriaService.obtenerCategoriaPorId(id);
    }

    public void agregarCategoria(Categoria categoria) {
        categoriaService.agregarCategoria(categoria);
    }

    public boolean actualizarCategoria(Categoria categoria) {
        return categoriaService.actualizarCategoria(categoria);
    }

    public boolean eliminarCategoria(int id) {
        return categoriaService.eliminarCategoria(id);
    }
}
