/*create database trabajadores;*/
use trabajadores;

CREATE TABLE trabajador
(
    id         INT PRIMARY KEY,
    nombre     VARCHAR(45) NULL,
    puesto     VARCHAR(45) NULL,
    salario    INT NULL,
    fecha_alta date null

);

DELETE * FROM trabajador;

