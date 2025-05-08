create database casino25;

CREATE TABLE clientes (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    edad INT NOT NULL,
    genero CHAR(1) NOT NULL,
    baja BOOLEAN NOT NULL,
    saldo DECIMAL(6,2) NOT NULL
);

CREATE TABLE juegos (
	id INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM("Blackjack", "Tragaperras") NOT NULL,
    activo BOOLEAN NOT NULL,
    dinero DECIMAL(6,2) NOT NULL
);

select * from clientes;
select * from juegos;

insert into clientes (nombre, edad, genero, baja, saldo) values("prueba", 19, "H", false, 300);
insert into juegos (tipo, activo, dinero) values("Blackjack", true , 3000);

delete from clientes;
delete from juegos;