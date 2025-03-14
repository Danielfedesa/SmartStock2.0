# SmartStock 2.0 📦📊

Descripción del proyecto:

SmartStock 2.0 es una aplicación de escritorio desarrollada como proyecto intermodular de 2º curso de DAM.

Se ha desarrollado en Java con Swing, siguiendo el patrón de arquitectura MVC para mejorar la separación de responsabilidades y facilitar la escalabilidad del código. Su objetivo principal es la gestión de inventarios en pequeñas y medianas empresas, permitiendo administrar productos, categorías, usuarios y movimientos de stock de manera eficiente. Además, incorpora funcionalidades avanzadas como:

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
 ┃ ┣ 📂 chatservice -> Implementación del chat con Java Sockets bajo patrón MVC  
 ┃ ┣ 📂 controller -> Controladores del sistema  
 ┃ ┣ 📂 repository -> Clases de acceso a la base de datos metiante Hibernate  
 ┃ ┣ 📂 model -> Entidades mapeadas con Hibernate  
 ┃ ┣ 📂 process -> Automatización de procesos como copias de seguridad y monitoreo de stock  
 ┃ ┣ 📂 service -> Lógica de negocio intermedia entre el controlador y la capa de datos  
 ┃ ┣ 📂 view -> Interfaz gráfica desarrollada con Java Swing  
 ┃ ┗ 📜 App.java -> Punto de entrada del sistema  
 ┣ 📂 src/main/resources  
 ┃ ┣ 📂 images -> Recursos gráficos de la interfaz  
 ┃ ┗ 📜 hibernate.cfg.xml -> Configuración de conexión con la base de datos  
 ┗ 📜 pom.xml -> Archivo de configuración de Maven  


📌 Explicación de los paquetes principales:

chatservice: Implementación del chat con Java Sockets.  
controller: Gestiona la lógica de la aplicación y sirve de puente entre la vista y el modelo. También están los controladores de acceso y sesión.  
repository: Capa de acceso a datos, encargada de la persistencia en la base de datos mediante Hibernate.  
model: Representa las entidades del sistema, mapeadas con JPA  
process: Contiene los procesos automatizados como copias de seguridad automáticas y supervisión del stock mínimo.  
service: Contiene la lógica de negocio, asegurando que los controladores no accedan directamente a la base de datos.  
view: Interfaz gráfica desarrollada con Java Swing, separada de la lógica de negocio.  


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
