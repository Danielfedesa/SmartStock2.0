package service;

import java.util.List;

import model.Producto;
import repository.ProductoRepository;

public class ProductoService {
	
    private final ProductoRepository productoRepository;

    public ProductoService() {
        this.productoRepository = new ProductoRepository();
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
        productoRepository.insertar(producto);
    }

    /**
     * Obtiene la lista de productos del inventario.
     *
     * @return Lista de productos.
     */
    public List<Producto> listarProductos() {
        return productoRepository.listar();
    }

    /**
     * Obtiene la lista de productos con stock bajo.
     *
     * @return Lista de productos con stock inferior al mínimo.
     */
    public List<Producto> listarStockBajo() {
        return productoRepository.listarMinimo();
    }

    /**
     * Busca un producto por su ID.
     *
     * @param idProducto ID del producto a buscar.
     * @return El producto encontrado o null si no existe.
     */
    public Producto obtenerProductoPorId(int idProducto) {
        return productoRepository.leerProducto(idProducto);
    }

    /**
     * Actualiza un producto en la base de datos.
     *
     * @param producto Producto con datos actualizados.
     */
    public boolean actualizarProducto(Producto producto) {
        return productoRepository.actualizar(producto);
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param idProducto ID del producto a eliminar.
     */
    public void eliminarProducto(int idProducto) {
        productoRepository.eliminar(idProducto);
    }
    
    /**
     * Actualiza el stock de un producto en la base de datos.
     *
     * @param idProducto  ID del producto a actualizar.
     * @param nuevoStock  Nuevo valor de stock.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        Producto producto = productoRepository.leerProducto(idProducto);
        
        if (producto != null) {
            producto.setStock(nuevoStock);
            return productoRepository.actualizar(producto);
        }
        
        return false;
    }
    
}