package service;

import java.util.List;

import DAO.DaoProducto;
import model.Producto;

public class ProductoService {
	
    private final DaoProducto daoProducto;

    public ProductoService() {
        this.daoProducto = new DaoProducto();
    }

    /**
     * Agrega un nuevo producto al inventario.
     *
     * @param producto Objeto de tipo Producto a insertar.
     */
    public void agregarProducto(Producto producto) {
        if (producto.getNombreProducto().isEmpty() || producto.getPrecio() <= 0 || producto.getStock() < 0) {
            throw new IllegalArgumentException("Los datos del producto son inválidos.");
        }
        daoProducto.insertar(producto);
    }

    /**
     * Obtiene la lista de productos del inventario.
     *
     * @return Lista de productos.
     */
    public List<Producto> listarProductos() {
        return daoProducto.listar();
    }

    /**
     * Obtiene la lista de productos con stock bajo.
     *
     * @return Lista de productos con stock inferior al mínimo.
     */
    public List<Producto> listarStockBajo() {
        return daoProducto.listarMinimo();
    }

    /**
     * Busca un producto por su ID.
     *
     * @param idProducto ID del producto a buscar.
     * @return El producto encontrado o null si no existe.
     */
    public Producto obtenerProductoPorId(int idProducto) {
        return daoProducto.leerProducto(idProducto);
    }

    /**
     * Actualiza un producto en la base de datos.
     *
     * @param producto Producto con datos actualizados.
     */
    public boolean actualizarProducto(Producto producto) {
        return daoProducto.actualizar(producto);
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param idProducto ID del producto a eliminar.
     */
    public void eliminarProducto(int idProducto) {
        daoProducto.eliminar(idProducto);
    }
    
    /**
     * Actualiza el stock de un producto en la base de datos.
     *
     * @param idProducto  ID del producto a actualizar.
     * @param nuevoStock  Nuevo valor de stock.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        Producto producto = daoProducto.leerProducto(idProducto);
        
        if (producto != null) {
            producto.setStock(nuevoStock);
            return daoProducto.actualizar(producto);
        }
        
        return false;
    }
    
}