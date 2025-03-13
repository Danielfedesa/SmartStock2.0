package controller;

import java.util.List;

import model.Producto;
import service.ProductoService;

public class ProductoController {
    private final ProductoService productoService;

    public ProductoController() {
        this.productoService = new ProductoService();
    }

    public void agregarProducto(Producto producto) {
        productoService.agregarProducto(producto);
    }

    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    public List<Producto> listarStockBajo() {
        return productoService.listarStockBajo();
    }

    public Producto obtenerProductoPorId(int idProducto) {
        return productoService.obtenerProductoPorId(idProducto);
    }

    public boolean actualizarProducto(Producto producto) {
        return productoService.actualizarProducto(producto);
    }

    public void eliminarProducto(int idProducto) {
        productoService.eliminarProducto(idProducto);
    }
    
    /**
     * Actualiza el stock de un producto.
     *
     * @param idProducto  ID del producto a actualizar.
     * @param nuevoStock  Nuevo valor de stock.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        return productoService.actualizarStock(idProducto, nuevoStock);
    }
    
}