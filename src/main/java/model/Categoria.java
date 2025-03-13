package model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa una categoria dentro del sistema.
 * 
 * <p>
 * Esta clase esta mapeada a la tabla 'Categorias' en la base de datos mediante Hibernate.
 * Utiliza las siguientes anotaciones:
 * </p>
 * <ul>
 *   <li>{@literal @Entity} indica que la clase es una entidad de la base de datos.</li>
 *   <li>{@literal @Table(name = "Categorias")} define el nombre de la tabla en la base de datos.</li>
 *   <li>{@literal @Id} especifica la clave primaria.</li>
 *   <li>{@literal @GeneratedValue(strategy = GenerationType.IDENTITY)} define el campo como autoincremental.</li>
 *   <li>{@literal @Column} personaliza los atributos en la base de datos.</li>
 *   <li>{@literal @OneToMany} establece la relacion con la entidad Producto.</li>
 * </ul>
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
@Entity
@Table(name = "Categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Categoria")
	private int idCategoria;

	@Column(name = "nombre_Categoria", nullable = false)
	private String nombreCategoria;

	@Column(name = "descripcion")
	private String descripcion;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private List<Producto> productos;

	/**
	 * Constructor por defecto (vacio).
	 */
	public Categoria() {
	}

	/**
	 * Constructor completo Categoria.
	 * 
	 * @param idCategoria     Identificador unico de la categoria.
	 * @param nombreCategoria Nombre de la categoria.
	 * @param descripcion     Descripcion de la categoria.
	 */
	public Categoria(int idCategoria, String nombreCategoria, String descripcion) {
		super();
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
		this.descripcion = descripcion;
	}

	/**
	 * Constructor para crear una Categoria sin id.
	 * 
	 * @param nombreCategoria Nombre de la categoria.
	 * @param descripcion     Descripcion de la categoria.
	 */
	public Categoria(String nombreCategoria, String descripcion) {
		super();
		this.nombreCategoria = nombreCategoria;
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el identificador de la categoria.
	 * 
	 * @return Identificador de la categoria.
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * Establece el identificador de la categoria.
	 * 
	 * @param Identificador de la categoria.
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Obtiene el nombre de la categoria.
	 * 
	 * @return Nombre de la categoria.
	 */
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	/**
	 * Establece el nombre de la categoria.
	 * 
	 * @param Nombre de la categoria.
	 */
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	/**
	 * Obtiene la descripcion de la categoria.
	 * 
	 * @return Descripcion de la categoria.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion de la categoria.
	 * 
	 * @param Descripcion de la categoria.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve una representacion en forma de cadena del objeto Categoria.
	 * 
	 * @return String Representacion en cadena del objeto Categoria.
	 */
	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombreCategoria=" + nombreCategoria + ", descripcion="
				+ descripcion + "]";
	}


} // Class
