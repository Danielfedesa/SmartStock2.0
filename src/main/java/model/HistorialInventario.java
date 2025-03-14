package model;

import java.sql.Timestamp;

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
 * Representa un movimiento de inventario en el sistema.
 * 
 * Esta clase está mapeada a la tabla 'HistorialInventario' en la base de datos mediante Hibernate.
 * Utiliza las siguientes anotaciones:
 * 
 * <ul>
 *   <li>{@literal @Entity} indica que la clase es una entidad de la base de datos.</li>
 *   <li>{@literal @Table(name = "HistorialInventario")} define el nombre de la tabla en la base de datos.</li>
 *   <li>{@literal @Id} especifica la clave primaria.</li>
 *   <li>{@literal @GeneratedValue(strategy = GenerationType.IDENTITY)} define el campo como autoincremental.</li>
 *   <li>{@literal @ManyToOne} establece relaciones con las entidades Producto y Usuario.</li>
 *   <li>{@literal @Enumerated(EnumType.STRING)} almacena el tipo de movimiento como una cadena en la base de datos.</li>
 *   <li>{@literal @Column} personaliza los atributos en la base de datos.</li>
 * </ul>
 * 
 * @author Daniel Fernandez Sanchez
 * @version 3.0 03/2025
 */
@Entity
@Table(name = "HistorialInventario")
public class HistorialInventario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Historial")
	private int idHistorial;

	@ManyToOne(fetch = FetchType.EAGER) // Relación EAGER con Producto
	@JoinColumn(name = "id_Producto", nullable = false)
	private Producto producto; // Relación con la entidad Producto

	@ManyToOne(fetch = FetchType.EAGER) // Relación EAGER con Usuario
	@JoinColumn(name = "id_Usuario", nullable = false)
	private Usuario usuario; // Relación con la entidad Usuario

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
	 * @return Identificador del historial.
	 */
	public int getIdHistorial() {
		return idHistorial;
	}

	/**
	 * Establece el identificador del historial.
	 * 
	 * @param Identificador del historial.
	 */
	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}

	/**
	 * Obtiene el identificador del producto.
	 * 
	 * @return Identificador del producto.
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Establece el identificador del producto.
	 * 
	 * @param Identificador del producto.
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return Identificador del usuario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el identificador del usuario.
	 * 
	 * @param Identificador del usuario.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene la cantidad de productos en el movimiento.
	 * 
	 * @return Cantidad de productos en el movimiento.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad de productos en el movimiento.
	 * 
	 * @param Cantidad de productos en el movimiento.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el tipo de movimiento (Entrada o Salida).
	 * 
	 * @return tipo de movimiento (Entrada o Salida).
	 */
	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * Establece el tipo de movimiento (Entrada o Salida)
	 * 
	 * @param tipo de movimiento (Entrada o Salida).
	 */
	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * Obtiene la fecha del movimiento.
	 * 
	 * @return Fecha del movimiento.
	 */
	public Timestamp getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del movimiento.
	 * 
	 * @param Fecha del movimiento.
	 */
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	/**
	 * Enum que representa el tipo de movimiento de stock.
	 */
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

} // Class
