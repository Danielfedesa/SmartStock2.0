# SmartStock 2.0 📦📊

Descripción del proyecto:

SmartStock 2.0 es una aplicación de escritorio desarrollada como proyecto intermodular de 2º curso de DAM.

Está desarrollada en Java con Swing y su función principal es la gestión de inventarios en pequeñas y medianas empresas. Esta herramienta permite administrar productos, categorías, usuarios y movimientos de inventario de manera eficiente, además de integrar funcionalidades avanzadas como:

✅ Gestión de stock con alertas automáticas.  
✅ Persistencia de datos con Hibernate ORM y MySQL.  
✅ Autenticación segura con cifrado de contraseñas mediante BCrypt.  
✅ Automatización de copias de seguridad de la base de datos mediante hilos.  
✅ Chat interno en tiempo real utilizando Java Sockets con cifrado AES.  
✅ Diseño modular y escalable con patrón de arquitectura MVC.  

Lenguaje: Java  
Interfaz gráfica: Swing  
Base de datos: MySQL  
ORM: Hibernate  
Autenticación: BCrypt  
Cifrado de mensajes: AES  
Manejo de hilos: Java Threads  
Gestión del proyecto: Maven  


🏗 Estructura del proyecto

📂 SmartStock2.0  
 ┣ 📂 src/main/java  
 ┃ ┣ 📂 chatservice  
 ┃ ┣ 📂 controller  
 ┃ ┣ 📂 DAO  
 ┃ ┣ 📂 model  
 ┃ ┣ 📂 process  
 ┃ ┣ 📂 view  
 ┃ ┗ 📜 App.java  
 ┣ 📂 src/main/resources  
 ┃ ┣ 📂 images  
 ┃ ┗ 📜 hibernate.cfg.xml  
 ┗ 📜 pom.xml  


📌 Explicación de los paquetes principales:

controller: Controladores de acceso y sesión.  
dao: Clases de acceso a la base de datos utilizando Hibernate.  
model: Entidades mapeadas con Hibernate.  
view: Interfaz gráfica con Java Swing.  
chatservice: Implementación del chat con Java Sockets.  


 📡 Funcionalidades principales
 
🔹 Gestión de inventarios y stock  
Creación, edición y eliminación de productos, categorías, usuarios y movimientos de inventario.  
Control de stock con notificación automática cuando un producto cae por debajo del mínimo establecido.

🔹 Persistencia y acceso a la base de datos  
Uso de Hibernate ORM para evitar consultas SQL manuales.  
Mapeo de entidades en base de datos mediante anotaciones JPA.  
Conexión segura con MySQL a través del archivo hibernate.cfg.xml.

🔹 Autenticación segura  
Cifrado de contraseñas con BCrypt antes de almacenarlas en la base de datos.  
Validación de credenciales en el inicio de sesión con comparación segura.  
Diferenciación de roles de usuario (admin y empleado).

🔹 Automatización de procesos con hilos  
Supervisión de stock mínimo ejecutándose en intervalos de 20 segundos.  
Generación de copias de seguridad automáticas de la base de datos en formato .sql.

🔹 Chat interno cifrado  
Comunicación en tiempo real utilizando Java Sockets.  
Cifrado de mensajes con AES antes de almacenarlos en la base de datos.  
Recuperación de mensajes previos al conectarse para mantener la conversación.


💻 Desarrollado por Daniel Fernández Sánchez - 2º DAM 🚀
