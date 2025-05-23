DROP DATABASE IF EXISTS casino25;
create database casino25;
use casino25;

CREATE TABLE clientes (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    edad INT NOT NULL,
    genero CHAR(1) NOT NULL,
    activo BOOLEAN NOT NULL,
    saldo DECIMAL(8,2) NOT NULL
);

CREATE TABLE juegos (
	id INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM("Blackjack", "Tragaperras") NOT NULL,
    activo BOOLEAN NOT NULL,
    dinero DECIMAL(8,2) NOT NULL
);

CREATE TABLE partidas (
	id INT PRIMARY KEY AUTO_INCREMENT,
	id_cliente INT,
    id_juego INT,
    tipo_juego ENUM("Blackjack", "Tragaperras") NOT NULL,
    resultado_apuesta DECIMAL(8,2) NOT NULL,
    cliente_gana BOOLEAN NOT NULL,
    fecha DATETIME,
	FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE SET NULL,
	FOREIGN KEY (id_juego) REFERENCES juegos(id) ON DELETE SET NULL
);




INSERT INTO clientes (nombre, edad, genero, activo, saldo) VALUES ('Pepe', 32, 'H', true, 2030.0);
INSERT INTO juegos (tipo, activo, dinero) VALUES ('Tragaperras', true, 50000.0);
INSERT INTO juegos (tipo, activo, dinero) VALUES ('Blackjack', true, 50000.0);

/*select * from clientes;
select * from juegos;

insert into clientes (nombre, edad, genero, baja, saldo) values("prueba", 19, "H", false, 300);
insert into juegos (tipo, activo, dinero) values("Blackjack", true , 3000);

delete from clientes;
delete from juegos;*/