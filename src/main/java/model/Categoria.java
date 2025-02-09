package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import DAO.DaoCategoria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase Categoria que representa la informacion, constructores y metodos
 * referentes a las categorias del sistema.
 * 
 * @author Daniel Fernandez Sanchez.
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
	 * @return idCategoria Identificador de la categoria.
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * Establece el identificador de la categoria.
	 * 
	 * @param idCategoria Identificador de la categoria.
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Obtiene el nombre de la categoria.
	 * 
	 * @return nombreCategoria Nombre de la categoria.
	 */
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	/**
	 * Establece el nombre de la categoria.
	 * 
	 * @param nombreCategoria Nombre de la categoria.
	 */
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	/**
	 * Obtiene la descripcion de la categoria.
	 * 
	 * @return descripcion Descripcion de la categoria.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion de la categoria.
	 * 
	 * @param descripcion Descripcion de la categoria.
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

	/**
	 * Metodo para crear una nueva categoria en la base de datos.
	 */
	public void crearCategoria() throws SQLException {
		DaoCategoria daoCategoria = new DaoCategoria();
		daoCategoria.insertar(this);
	}

	/**
	 * Metodo para listar todas las categorias de la base de datos mediante un
	 * objeto del dao.
	 * 
	 * @return Lista de categorias.
	 */
	public List<Categoria> listarCategorias() {
		DaoCategoria daoCategoria = new DaoCategoria();
		return daoCategoria.listar();
	}

	/**
	 * Metodo para recuperar una categoria por su ID y cargar sus datos.
	 * 
	 * @param idCategoria Identificador unico de la categoria a recuperar.
	 * @return Objeto Categoria recuperado de la base de datos.
	 */
	public Categoria recuperarCat(int idCategoria) throws SQLException {
		DaoCategoria daoCategoria = new DaoCategoria();
		return daoCategoria.leerCategoria(idCategoria);
	}

	/**
	 * Metodo para insertar la actualizacion de los datos de una categoria en la
	 * base de datos.
	 * 
	 * @return boolean true si la actualizacion fue correcta, false en caso
	 *         contrario.
	 */
	public boolean actualizarCategoria() throws SQLException {
		DaoCategoria daoCategoria = new DaoCategoria();
		return daoCategoria.actualizar(this);
	}

	/**
	 * Metodo para eliminar una categoria de la base de datos.
	 * 
	 * @param idCategoria El ID del usuario a eliminar.
	 */
	public void eliminarCategoria(int idCategoria) {
		DaoCategoria daoCategoria = new DaoCategoria();
		daoCategoria.eliminar(idCategoria);
	}

} // Class
