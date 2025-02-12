CREATE DATABASE smartstockdb;

USE smartstockdb;

CREATE TABLE Usuarios (
    id_Usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre_Usuario VARCHAR(255) NOT NULL,
    apellido1 VARCHAR(255) NOT NULL,
	apellido2 VARCHAR(255),
    telefono INT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('admin', 'empleado') NOT NULL
);

CREATE TABLE Categorias (
	id_Categoria INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre_Categoria VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE Productos (
    id_Producto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre_Producto VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    stock_Minimo INT DEFAULT 5,
    id_Categoria INT,
    FOREIGN KEY (id_Categoria) REFERENCES Categorias(id_Categoria)
);

CREATE TABLE HistorialInventario (
    id_Historial INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_Producto INT NOT NULL,
    id_Usuario INT NOT NULL,
    cantidad INT NOT NULL,
    tipo_Movimiento ENUM('entrada', 'salida') NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_Producto) REFERENCES Productos(id_Producto),
    FOREIGN KEY (id_Usuario) REFERENCES Usuarios(id_Usuario)
);

CREATE TABLE CopiasSeguridad (
	id_Backup INT AUTO_INCREMENT PRIMARY KEY,
    fecha_Backup TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ruta_Archivo VARCHAR(255) NOT NULL
);

CREATE TABLE Mensajes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contenido TEXT NOT NULL,
    usuario VARCHAR(255),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Datos de prueba

INSERT INTO Usuarios (nombre_Usuario, apellido1, apellido2, telefono, email, contrasena, rol)
VALUES
('Daniel','Fernández', 'Sánchez', '123456789', 'administrador@smartstock.com', '1234', 'admin'),
('Melani','Martinez', 'Sanz', '987654321', 'melmarsanz@smartstock.com', '1234', 'empleado'),
('Pepe','Pacheco', NULL, '555555555', 'pepepa@smartstock.com', '1234', 'empleado'),
('Sebastián','García', NULL, '666666666', 'sebasgarcia@smartstock.com', '1234', 'empleado')
;

INSERT INTO Categorias (nombre_Categoria, descripcion)
VALUES
('Camisetas1','Prendas superiores casuales MANGA CORTA'),
('Camisetas2','Prendas superiores casuales MANGA LARGA'),
('Camisetas3','Prendas superiores formales MANGA CORTA'),
('Camisetas4','Prendas superiores casuales MANGA LARGA'),
('Pantalones1', 'Pantalones cortos CASUALES'),
('Pantalones2', 'Pantalones cortos FORMALES'),
('Pantalones3', 'Pantalones largos CASUALES'),
('Pantalones4', 'Pantalones largos FORMALES'),
('Chaquetas1', 'Abrigos de INVIERNO'),
('Chaquetas2', 'Chaquetas de VESTIR'),
('Chaquetas3', 'Chaquetas de TRAJE'),
('Chaquetas4', 'Pantalones largos FORMALES'),
('Calzado1', 'Zapatillas DEPORTE'),
('Calzado2', 'Zapatillas de VESTIR'),
('Calzado3', 'Zapatos de VESTIR'),
('Calzado4', 'Botines de media caña')
;

INSERT INTO Productos (nombre_Producto, descripcion, precio, stock, id_Categoria)
VALUES
('Camiseta Básica Blanca', 'Camiseta de algodón básica, manga corta', 12.99, 50, 1),
('Camiseta Manga Larga Negra', 'Camiseta de algodón negra, manga larga', 15.99, 30, 2),
('Pantalón Corto Casual', 'Pantalón corto cómodo para verano', 19.99, 40, 5),
('Pantalón Largo Formal', 'Pantalón de vestir para eventos formales', 39.99, 20, 8),
('Chaqueta Invierno Acolchada', 'Chaqueta acolchada ideal para el invierno', 59.99, 15, 9),
('Zapatillas Deportivas Running', 'Zapatillas deportivas ligeras para correr', 49.99, 35, 13),
('Zapatos de Vestir Negros', 'Zapatos de vestir elegantes de cuero', 89.99, 10, 15),
('Botines de Media Caña Marrones', 'Botines cómodos de media caña para el día a día', 69.99, 25, 16)
;

INSERT INTO HistorialInventario (id_Producto, id_Usuario, cantidad, tipo_Movimiento)
VALUES
(1, 1, 20, 'entrada'),
(2, 2, 5, 'salida'),
(3, 3, 15, 'entrada'),
(4, 2, 10, 'entrada'),
(5, 2, 2, 'salida'),
(6, 2, 12, 'entrada'),
(7, 1, 8, 'entrada'),
(8, 1, 3, 'salida'),
(1, 1, 5, 'salida'),
(2, 2, 10, 'entrada'),
(6, 1, 7, 'salida')
;

INSERT INTO CopiasSeguridad (ruta_Archivo)
VALUES
('/backups/smartstock_backup_2025-01-08.sql'),
('/backups/smartstock_backup_2025-01-07.sql'),
('/backups/smartstock_backup_2025-01-06.sql'),
('/backups/smartstock_backup_2025-01-05.sql'),
('/backups/smartstock_backup_2025-01-04.sql')
;