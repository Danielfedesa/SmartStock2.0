package model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import DAO.DaoHistorialInventario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase HistorialInventario que representa la informacion, constructores y
 * metodos referentes a los movimientos del inventario del sistema.
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 2.0 02/2025
 */
@Entity
@Table(name = "HistorialInventario")
public class HistorialInventario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Historial")
	private int idHistorial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Producto", nullable = false)
	private Producto producto; // Clave foránea

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Usuario", nullable = false)
	private Usuario usuario; // Clave foránea

	@Column(name = "cantidad", nullable = false)
	private int cantidad;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_Movimiento", nullable = false)
	private TipoMovimiento tipoMovimiento;

	@Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp fecha;

	/**
	 * Constructor por defecto (vacio).
	 */
	public HistorialInventario() {
	}

	/**
	 * Constructor completo HistorialInventario.
	 * 
	 * @param idHistorial    Identificador unico de los movimientos.
	 * @param producto       Identificador unico de los productos.
	 * @param cantidad       Cantidad de unidades del producto.
	 * @param tipoMovimiento Tipo de movimiento de stock: Entrada o Salida.
	 * @param fecha          Fecha del movimiento.
	 */
	public HistorialInventario(int idHistorial, Producto producto, Usuario usuario, int cantidad,
			TipoMovimiento tipoMovimiento, Timestamp fecha) {
		this.idHistorial = idHistorial;
		this.producto = producto;
		this.usuario = usuario;
		this.cantidad = cantidad;
		this.tipoMovimiento = tipoMovimiento;
		this.fecha = fecha;
	}

	/**
	 * Constructor HistorialInventario sin idHistorial.
	 * 
	 * @param producto       Identificador unico de los productos.
	 * @param cantidad       Cantidad de unidades del producto.
	 * @param tipoMovimiento Tipo de movimiento de stock: Entrada o Salida.
	 * @param fecha          Fecha del movimiento.
	 */
	public HistorialInventario(Producto producto, Usuario usuario, int cantidad, TipoMovimiento tipoMovimiento,
			Timestamp fecha) {
		this.producto = producto;
		this.usuario = usuario;
		this.cantidad = cantidad;
		this.tipoMovimiento = tipoMovimiento;
		this.fecha = fecha;
	}

	/**
	 * Obtiene el identificador del historial.
	 * 
	 * @return idHistorial Identificador del historial.
	 */
	public int getIdHistorial() {
		return idHistorial;
	}

	/**
	 * Establece el identificador del historial.
	 * 
	 * @param idHistorial Identificador del historial.
	 */
	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}

	/**
	 * Obtiene el identificador del producto.
	 * 
	 * @return producto Identificador del producto.
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Establece el identificador del producto.
	 * 
	 * @param producto Identificador del producto.
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return usuario Identificador del usuario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el identificador del usuario.
	 * 
	 * @param usuario Identificador del usuario.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene la cantidad de productos en el movimiento.
	 * 
	 * @return cantidad Cantidad de productos en el movimiento.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad de productos en el movimiento.
	 * 
	 * @param cantidad Cantidad de productos en el movimiento.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el tipo de movimiento (Entrada o Salida).
	 * 
	 * @return tipoMovimiento tipo de movimiento (Entrada o Salida).
	 */
	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * Establece el tipo de movimiento (Entrada o Salida)
	 * 
	 * @param tipoMovimiento tipo de movimiento (Entrada o Salida).
	 */
	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * Obtiene la fecha del movimiento.
	 * 
	 * @return fecha Fecha del movimiento.
	 */
	public Timestamp getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del movimiento.
	 * 
	 * @param fecha Fecha del movimiento.
	 */
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	// Enum para el tipo de movimiento
	public enum TipoMovimiento {
		ENTRADA, SALIDA
	}

	/**
	 * Devuelve una representacion en forma de cadena del objeto
	 * HistorialInventario.
	 * 
	 * @return String Representacion en cadena del objeto HistorialInventario.
	 */
	@Override
	public String toString() {
		return "HistorialInventario [idHistorial=" + idHistorial + ", producto=" + producto + ", usuario=" + usuario
				+ ", cantidad=" + cantidad + ", tipoMovimiento=" + tipoMovimiento + ", fecha=" + fecha + "]";
	}

	/**
	 * Metodo para insertar un nuevo movimiento de stock en la base de datos.
	 * 
	 */
	public void crearMovimiento() throws SQLException {
		DaoHistorialInventario daoHistorialInventario = new DaoHistorialInventario();
		daoHistorialInventario.insertar(this);
	}

	/**
	 * Metodo para listar todos los movimientos de stock de la base de datos
	 * mediante un objeto del dao.
	 * 
	 * @return Lista de objetos HistorialInventario que representa todos los
	 *         movimientos almacenados en la base de datos.
	 */
	public List<HistorialInventario> listarMovimientos() {
		DaoHistorialInventario daoHistorialInventario = new DaoHistorialInventario();
		return daoHistorialInventario.listar();
	}

} // Class
