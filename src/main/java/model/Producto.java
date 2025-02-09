package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import DAO.DaoProducto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase Producto que representa la informacion, constructores y metodos
 * referentes a los productos del sistema.
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 2.0 02/2025
 */
@Entity
@Table(name = "Productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Producto")
	private int idProducto;

	@Column(name = "nombre_Producto", nullable = false)
	private String nombreProducto;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "precio", nullable = false)
	private double precio;

	@Column(name = "stock", nullable = false)
	private int stock;

	@Column(name = "stock_Minimo", nullable = false)
	private int stockMinimo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Categoria", nullable = false)
	private Categoria categoria; // Clave foránea de Categoria

	/**
	 * Constructor por defecto (vacio).
	 */
	public Producto() {
	}

	/**
	 * Constructor completo Producto.
	 * 
	 * @param idProducto     Identificador unico del producto.
	 * @param nombreProducto Nombre del producto.
	 * @param descripcion    Descripcion del producto.
	 * @param precio         Precio del producto.
	 * @param stock          Stock actual del producto.
	 * @param stockMinimo    Stock minimo establecido del producto.
	 * @param categoria      Categoria a la que pertenece el producto.
	 */
	public Producto(int idProducto, String nombreProducto, String descripcion, double precio, int stock,
			int stockMinimo, Categoria categoria) {
		super();
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.stockMinimo = stockMinimo;
		this.categoria = categoria;
	}

	/**
	 * Obtiene el identificador del producto.
	 * 
	 * @return Identificador del producto.
	 */
	public int getIdProducto() {
		return idProducto;
	}

	/**
	 * Establece el identificador del producto.
	 * 
	 * @param idProducto Identificador del producto.
	 */
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Obtiene el nombre del producto.
	 * 
	 * @return Nombre del producto.
	 */
	public String getNombreProducto() {
		return nombreProducto;
	}

	/**
	 * Establece el nombre del producto.
	 * 
	 * @param nombreProducto Nombre del producto.
	 */
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	/**
	 * Obtiene la descripcion del producto.
	 * 
	 * @return Descripcion del producto.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion del producto.
	 * 
	 * @param descripcion Descripcion del producto.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el precio del producto.
	 * 
	 * @return Precio del producto.
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Establece el precio del producto
	 * 
	 * @param precio Precio del producto.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Obtiene la cantidad de stock actual del producto.
	 * 
	 * @return Stock del producto.
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Establece la cantidad de stock del producto.
	 * 
	 * @param stock Stock del producto.
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Obtiene la cantidad de stock minimo estipulado del producto.
	 * 
	 * @return Stock minimo del producto.
	 */
	public int getStockMinimo() {
		return stockMinimo;
	}

	/**
	 * Establece la cantidad de stock minimo del producto.
	 * 
	 * @param stockMinimo Stock minimo del producto.
	 */
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	/**
	 * Obtiene el identificador de la categoria a la que pertenece el producto.
	 * 
	 * @return Identificador de categoria del producto.
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * Establece el identificador de la categoria a la que pertenece el producto.
	 * 
	 * @param idCategoria Identificador de categoria del producto.
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * Devuelve una representacion en forma de cadena del objeto Producto.
	 * 
	 * @return String Representacion en cadena del objeto Producto.
	 */

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", descripcion="
				+ descripcion + ", precio=" + precio + ", stock=" + stock + ", stockMinimo=" + stockMinimo
				+ ", categoria=" + categoria + "]";
	}

	/**
	 * Metodo para insertar un nuevo producto en la base de datos.
	 */
	public void crearProducto() {
		DaoProducto daoProducto = new DaoProducto();
		daoProducto.insertar(this);
	}

	/**
	 * Metodo para listar todos los productos de la base de datos mediante un objeto
	 * del dao.
	 * 
	 * @return Lista de objetos Producto.
	 */
	public List<Producto> listarProductos() {
		DaoProducto daoProducto = new DaoProducto();
		return daoProducto.listar();
	}

	/**
	 * Metodo para listar los productos con stock por debajo del mínimo en la base
	 * de datos mediante un objeto del dao.
	 * 
	 * @return Lista de objetos Producto que representa todos los productos con
	 *         stock bajo almacenadas en la base de datos.
	 */
	public List<Producto> listarProductosStockBajo() {
		DaoProducto daoProducto = new DaoProducto();
		return daoProducto.listarStockBajo();
	}

	/**
	 * Metodo para recuperar un producto por su ID y cargar sus datos.
	 * 
	 * @param idProducto Identificador unico del producto a recuperar.
	 * @return Objeto Producto recuperado de la base de datos.
	 */
	public Producto recuperarPro(int idProducto) {
		DaoProducto daoProducto = new DaoProducto();
		return daoProducto.leerProducto(idProducto);
	}

	/**
	 * Metodo para insertar la actualizacion de los datos de un producto en la base
	 * de datos.
	 * 
	 * @return true si la actualizacion fue correcta, false en caso
	 *         contrario.
	 */
	public boolean actualizarProducto() {
		DaoProducto daoProducto = new DaoProducto();
		return daoProducto.actualizar(this);
	}

	/**
	 * Metodo para eliminar un producto de la base de datos.
	 * @param idUsuario El ID del producto a eliminar.
	 */
	public void eliminarProducto(int idProducto) {
		DaoProducto daoProducto = new DaoProducto();
		daoProducto.eliminar(idProducto);
	}

	/**
	 * Metodo para verificar si algun producto tiene stock por debajo del minimo.
	 * 
	 * @return String Detalles de los productos con stock bajo, o un mensaje vacio
	 *         si no hay alertas.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	public String verificarStockMinimo() throws SQLException {
		DaoProducto daoProd = new DaoProducto();
		List<Producto> productos = daoProd.listarMinimo(); // Obtiene la lista de productos con stock bajo
		StringBuilder alertas = new StringBuilder();

		if (productos.isEmpty()) { // Verifica si la lista está vacía
			return "No hay productos con stock bajo.";
		}

		// Itera sobre los productos y genera las alertas
		for (Producto producto : productos) {
			alertas.append("Producto: ").append(producto.getNombreProducto()).append(", Stock actual: ")
					.append(producto.getStock()).append(", Stock mínimo: ").append(producto.getStockMinimo())
					.append("\n");
		}

		return alertas.toString();
	}

} // Class
