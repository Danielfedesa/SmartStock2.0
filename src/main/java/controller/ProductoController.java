package controller;

import java.util.List;

import model.Producto;
import service.ProductoService;

/**
 * Controlador para gestionar los productos dentro del sistema.
 * 
 * Esta clase actua como intermediario entre la vista y la capa de servicio,
 * delegando las operaciones CRUD a {@link ProductoService}.
 * 
 * @version 1.0 03/2025
 */
public class ProductoController {
    private final ProductoService productoService;

    /**
     * Constructor que inicializa el servicio de productos.
     */
    public ProductoController() {
        this.productoService = new ProductoService();
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto Objeto {@link Producto} que representa el producto a agregar.
     */
    public void agregarProducto(Producto producto) {
        productoService.agregarProducto(producto);
    }

    /**
     * Obtiene una lista con todos los productos almacenados.
     *
     * @return Lista de objetos {@link Producto} disponibles en el inventario.
     */
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    /**
     * Obtiene una lista de productos con stock bajo.
     * 
     * Devuelve los productos cuyo stock ha caido por debajo del nivel minimo establecido.
     *
     * @return Lista de productos con stock bajo.
     */
    public List<Producto> listarStockBajo() {
        return productoService.listarStockBajo();
    }

    /**
     * Obtiene un producto segun su ID.
     *
     * @param idProducto ID del producto a buscar.
     * @return Objeto {@link Producto} si se encuentra en la base de datos, null en caso contrario.
     */
    public Producto obtenerProductoPorId(int idProducto) {
        return productoService.obtenerProductoPorId(idProducto);
    }

    /**
     * Actualiza la informacion de un producto existente.
     *
     * @param producto Objeto {@link Producto} con los datos actualizados.
     * @return true si la actualizacion fue exitosa, false en caso contrario.
     */
    public boolean actualizarProducto(Producto producto) {
        return productoService.actualizarProducto(producto);
    }

    /**
     * Elimina un producto de la base de datos segun su ID.
     *
     * @param idProducto ID del producto a eliminar.
     */
    public void eliminarProducto(int idProducto) {
        productoService.eliminarProducto(idProducto);
    }
    
    /**
     * Actualiza el stock de un producto especifico.
     *
     * @param idProducto ID del producto a actualizar.
     * @param nuevoStock Nuevo valor de stock.
     * @return true si la actualizacion fue exitosa, false en caso contrario.
     */
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        return productoService.actualizarStock(idProducto, nuevoStock);
    }
    
}