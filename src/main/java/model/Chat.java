package model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


	@Entity
	@Table(name = "Mensajes")
	public class Chat {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;

		@Column(name = "contenido", nullable = false)
		private String contenido;

		@Column(name = "usuario", nullable = false)
		private String usuario;

		@Column(name = "fecha", nullable = false)
		private LocalDateTime fecha;

		public Chat() {
		}
		
		public Chat(int id, String contenido, String usuario, LocalDateTime fecha) {
			this.id = id;
			this.contenido = contenido;
			this.usuario = usuario;
			this.fecha = fecha;
		}
		
		public Chat(String contenido, String usuario, LocalDateTime fecha) {
			this.contenido = contenido;
			this.usuario = usuario;
			this.fecha = fecha;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getContenido() {
			return contenido;
		}

		public void setContenido(String contenido) {
			this.contenido = contenido;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public LocalDateTime getFecha() {
			return fecha;
		}

		public void setFecha(LocalDateTime fecha) {
			this.fecha = fecha;
		}
}
