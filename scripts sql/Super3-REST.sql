---------------------------------------- SCRIPT DE BASE DE DATOS PARA SUPERMERCADO 3/NOMBRE/REST ------------------------------------------------------------------- 
-- Este script maneja los datos del supermercado Vea, que vendria a ser el que se maneja por servicios REST.

-- Indice del Script:
-- 1: Eliminacion de Tablas (para vaciar la base de datos)
-- 2: Creacion de Tablas
-- 3: Inserción de Datos
-- 4: Procedimientos Almacenados (para consultar o actualizar informacion)
drop database supermercado3
create database supermercado3

use supermercado3
go

-- 1: ELIMINACION DE TABLAS 

DELETE FROM productos_sucursales;
DELETE FROM horarios_sucursales;
DELETE FROM tipos_servicios_sucursales;
DELETE FROM sucursales;
DELETE FROM productos;
DELETE FROM tipos_productos_marcas;
DELETE FROM categorias_productos;
DELETE FROM rubros_productos;
DELETE FROM marcas_productos;
DELETE FROM tipos_productos;
DELETE FROM localidades;
DELETE FROM provincias;
DELETE FROM paises;
DELETE FROM tipos_servicios_supermercado;
DELETE FROM supermercado;
DELETE FROM empresas_externas;

execute obtener_sucursales_completas


-

-- reiniciar los identity
DBCC CHECKIDENT ('empresas_externas', RESEED, 0);
DBCC CHECKIDENT ('sucursales', RESEED, 0);
DBCC CHECKIDENT ('tipos_servicios_supermercado', RESEED, 0);
DBCC CHECKIDENT ('rubros_productos', RESEED, 0);
DBCC CHECKIDENT ('categorias_productos', RESEED, 0);
DBCC CHECKIDENT ('marcas_productos', RESEED, 0);
DBCC CHECKIDENT ('tipos_productos', RESEED, 0);
DBCC CHECKIDENT ('localidades', RESEED, 0);



-- 2: CREACION DE TABLAS

create table empresas_externas
(
	nro_empresa integer not null identity,
	razon_social varchar(50) not null,
	cuit_empresa varchar(20) not null,
	token_servicio varchar(255) not null,

	constraint PK__empresas_externas primary key (nro_empresa)
	
)
go

create table supermercado 
(
	cuit varchar(30) not null,
	razon_social varchar(50) not null,
	calle varchar(50) not null,
	nro_calle varchar(5) not null,
	telefonos varchar(80) not null,

	constraint PK__supermercado primary key (cuit)

)
go

create table paises
(
	cod_pais varchar(10) not null,
	nom_pais varchar(60) not null,
	-- añadir el atributo local ??
	
	constraint PK__paises primary key (cod_pais)

)
go

create table provincias
(
	cod_pais varchar(10) not null,
	cod_provincia varchar(10) not null,
	nom_provincia varchar (60) not null,

	constraint PK__provincias primary key (cod_pais, cod_provincia),
	constraint FK__provincias__paises foreign key (cod_pais) references paises
)
go

create table localidades
(
	nro_localidad integer not null identity,
	nom_localidad varchar(60) not null,
	cod_pais varchar(10) not null,
	cod_provincia varchar(10) not null,

	constraint PK__localidades primary key (nro_localidad),
	constraint AK1__localidades unique (nom_localidad, cod_pais, cod_provincia),
	constraint FK__localidades__paises foreign key (cod_pais) references paises,
	constraint FK__localidades__provincias foreign key (cod_pais, cod_provincia) references provincias,

)
go

create table sucursales
(
	nro_sucursal integer not null identity,
	nom_sucursal varchar(40) not null,
	calle varchar(60) not null,
	nro_calle varchar(5) not null,
	telefonos varchar(80) not null,
	coord_latitud varchar (80) not null,
	coord_longitud varchar(80) not null,
	nro_localidad integer not null,
	habilitada char(1) not null,

	constraint PK__sucursales primary key (nro_sucursal),
	constraint AK1__sucursales unique (nom_sucursal),
	constraint FK__sucursales__localidades foreign key (nro_localidad) references localidades,
	constraint CK1__habilitada check (habilitada in ('s' ,'n'))

)
go

create table horarios_sucursales
(
	nro_sucursal integer not null,
	dia_semana varchar(10) not null,
	hora_desde time not null,
	hora_hasta time not null,

	constraint PK__horarios_sucursales primary key (nro_sucursal, dia_semana),
	constraint FK__horarios_sucursales__sucursales foreign key (nro_sucursal) references sucursales
)
go

create table tipos_servicios_supermercado
(
	nro_tipo_servicio smallint not null identity,
	nom_tipo_servicio varchar(50) not null,

	constraint PK__tipos_servicios_supermercado primary key (nro_tipo_servicio)
)
go


create table tipos_servicios_sucursales 
(
	nro_sucursal integer not null,
	nro_tipo_servicio smallint not null,
	vigente char(1) not null,
	
	constraint PK__tipos_servicios_sucursales primary key (nro_sucursal, nro_tipo_servicio),
	constraint FK__tipos_servicios_sucursales foreign key (nro_sucursal) references sucursales,
	constraint FK__nro_tipo_servicio foreign key (nro_tipo_servicio) references tipos_servicios_supermercado,
	constraint CK__tipos_servicios_sucursales__vigente check (vigente in ('s', 'n'))
)
go

create table rubros_productos
(
	nro_rubro smallint not null identity,
	nom_rubro varchar(50) not null,
	vigente char(1) not null,

	constraint PK__rubros_productos primary key (nro_rubro),
	constraint CK__rubros_productos__vigente check (vigente in ('s','n'))
)
go


create table categorias_productos
(
	nro_categoria smallint not null identity,
	nom_categoria varchar(50) not null,
	nro_rubro smallint not null,
	vigente char(1) not null,

	constraint PK__categorias_productos primary key (nro_categoria),
	constraint FK__categorias_productos__rubros_productos foreign key (nro_rubro) references rubros_productos,
	constraint CK__categorias_productos__vigente check (vigente in ('s','n'))
)
go

create table marcas_productos
(
	nro_marca integer not null identity,
	nom_marca varchar(50) not null,
	vigente char(1) not null,

	constraint PK__marcas_productos primary key (nro_marca),
	constraint CK__marcas_productos__vigente check (vigente in ('s','n'))
)
go


create table tipos_productos
(
	nro_tipo_producto smallint not null identity,
	nom_tipo_producto varchar(60) not null,

	constraint PK__tipos_productos primary key (nro_tipo_producto)
)
go

create table tipos_productos_marcas
(
	nro_marca integer not null,
	nro_tipo_producto smallint not null,
	vigente char(1) not null,

	constraint PK__tipos_productos_marcas primary key (nro_marca, nro_tipo_producto),
	constraint FK__tipos_productos_marcas__marcas_productos foreign key (nro_marca) references marcas_productos,
	constraint FK__tipos_productos_marcas__tipos_productos foreign key (nro_tipo_producto) references tipos_productos,
	constraint CK__tipos_productos_marcas__vigente check (vigente in('s','n') )
)
go

create table productos 
(
	cod_barra varchar(255) not null,
	nom_producto varchar(60) not null,
	desc_producto varchar(120) not null,
	nro_categoria smallint not null,
	imagen varchar(255) null,
	nro_marca integer not null,
	nro_tipo_producto smallint not null,
	vigente char(1) not null,

	constraint PK__productos primary key (cod_barra),
	constraint FK__productos__categorias_productos foreign key (nro_categoria) references categorias_productos,
	constraint FK__productos__tipos_productos_marcas foreign key (nro_marca, nro_tipo_producto) references tipos_productos_marcas,
	constraint CK__productos__vigente check (vigente in('s','n')),
)
go


create table productos_sucursales
(
	nro_sucursal integer not null,
	cod_barra varchar(255) not null,
	precio decimal(10,2) not null,
	vigente char(1) not null,

	constraint PK__productos_sucursales primary key (nro_sucursal, cod_barra),
	constraint FK__productos_sucursales__productos foreign key (cod_barra) references productos,
	constraint FK__productos_sucursales__sucursales foreign key (nro_sucursal) references sucursales,
	constraint CK__productos_sucursales__vigente check (vigente in ('s','n'))

)
go

-- 3: INSERCIONES DE DATOS
-- Insert into paises
INSERT INTO paises (cod_pais, nom_pais) VALUES ('AR', 'Argentina');

-- Insert into provincias
INSERT INTO provincias (cod_pais, cod_provincia, nom_provincia) VALUES ('AR', 'BA', 'Buenos Aires');
INSERT INTO provincias (cod_pais, cod_provincia, nom_provincia) VALUES ('AR', 'CBA', 'Córdoba');
INSERT INTO provincias (cod_pais, cod_provincia, nom_provincia) VALUES ('AR', 'SF', 'Santa Fe');

-- Insert into localidades for Buenos Aires
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('La Plata', 'AR', 'BA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Mar del Plata', 'AR', 'BA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Tandil', 'AR', 'BA');

-- Insert into localidades for Córdoba
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Córdoba Capital', 'AR', 'CBA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Villa Carlos Paz', 'AR', 'CBA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Río Cuarto', 'AR', 'CBA');

-- Insert into localidades for Santa Fe
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Rosario', 'AR', 'SF');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Santa Fe Capital', 'AR', 'SF');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Reconquista', 'AR', 'SF');



-- Insert into sucursales (with multiple branches per locality)
--1 la plata
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea La Plata 1', 'Sarmiento', '345', '221-1234567', '-34.92145', '-57.95453', 1, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea La Plata 2', 'Varela', '456', '221-2345678', '-34.92234', '-57.95532', 1, 's');

--2 mar del plata
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Mar del Plata 1', 'Nuñez', '345', '221-1234567', '-34.92145', '-57.95453', 2, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Mar del Plata 2', 'Giordano', '456', '221-2345678', '-34.92234', '-57.95532', 2, 's');

--3 tandil
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Tandil 1', 'Pinto', '345', '221-1234567', '-34.92145', '-57.95453', 3, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Tandil 2', 'Mondino', '456', '221-2345678', '-34.92234', '-57.95532', 3, 's');

--4 cordoba
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Córdoba Centro', 'Av. Colón', '876', '351-5554321', '-31.42008', '-64.18877', 4, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Córdoba Norte', 'Calle Mayor', '789', '351-5551234', '-31.4250', '-64.1950', 4, 's');

--5 carlos paz
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Villa Carlos Paz 1', 'Libertad', '345', '221-1234567', '-34.92145', '-57.95453', 5, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Villa Carlos Paz 2', 'Esperanza', '456', '221-2345678', '-34.92234', '-57.95532', 5, 's');

--6 rio cuarto
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Rio Cuarto 1', 'Alberdi', '345', '221-1234567', '-34.92145', '-57.95453', 6, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Rio Cuarto 2', 'San Martin', '456', '221-2345678', '-34.92234', '-57.95532', 6, 's');

--7 rosario
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Rosario Centro', 'Vekez Sarfield', '345', '221-1234567', '-34.92145', '-57.95453', 7, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Rosario Sur', 'Yrigoyen', '456', '221-2345678', '-34.92234', '-57.95532', 7, 's');

--8 santa fe
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Santa Fe Centro', 'Vekez Sarfield', '345', '221-1234567', '-34.92145', '-57.95453', 8, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Santa Fe Sur', 'Yrigoyen', '456', '221-2345678', '-34.92234', '-57.95532', 8, 's');

-- 9 reconquista
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Reconquista Centro', 'Velez Sarfield', '345', '221-1234567', '-34.92145', '-57.95453', 9, 's');
INSERT INTO sucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, nro_localidad, habilitada)
VALUES ('Vea Reconquista Sur', 'Yrigoyen', '456', '221-2345678', '-34.92234', '-57.95532', 9, 's');

select * from localidades


-- Insertar horarios para todos los dias en todas las sucursales
INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (1, 'Lunes', '09:00', '22:00'), (1, 'Martes', '09:00', '22:00'), (1, 'Miércoles', '09:00', '22:00'),
       (1, 'Jueves', '09:00', '22:00'), (1, 'Viernes', '09:00', '22:00'), (1, 'Sábado', '10:00', '22:00'),
       (1, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (2, 'Lunes', '09:00', '22:00'), (2, 'Martes', '09:00', '22:00'), (2, 'Miércoles', '09:00', '22:00'),
       (2, 'Jueves', '09:00', '22:00'), (2, 'Viernes', '09:00', '22:00'), (2, 'Sábado', '10:00', '22:00'),
       (2, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (3, 'Lunes', '09:00', '22:00'), (3, 'Martes', '09:00', '22:00'), (3, 'Miércoles', '09:00', '22:00'),
       (3, 'Jueves', '09:00', '22:00'), (3, 'Viernes', '09:00', '22:00'), (3, 'Sábado', '10:00', '22:00'),
       (3, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (4, 'Lunes', '09:00', '22:00'), (4, 'Martes', '09:00', '22:00'), (4, 'Miércoles', '09:00', '22:00'),
       (4, 'Jueves', '09:00', '22:00'), (4, 'Viernes', '09:00', '22:00'), (4, 'Sábado', '10:00', '22:00'),
       (4, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (5, 'Lunes', '09:00', '22:00'), (5, 'Martes', '09:00', '22:00'), (5, 'Miércoles', '09:00', '22:00'),
       (5, 'Jueves', '09:00', '22:00'), (5, 'Viernes', '09:00', '22:00'), (5, 'Sábado', '10:00', '22:00'),
       (5, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (6, 'Lunes', '09:00', '22:00'), (6, 'Martes', '09:00', '22:00'), (6, 'Miércoles', '09:00', '22:00'),
       (6, 'Jueves', '09:00', '22:00'), (6, 'Viernes', '09:00', '22:00'), (6, 'Sábado', '10:00', '22:00'),
       (6, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (7, 'Lunes', '09:00', '22:00'), (7, 'Martes', '09:00', '22:00'), (7, 'Miércoles', '09:00', '22:00'),
       (7, 'Jueves', '09:00', '22:00'), (7, 'Viernes', '09:00', '22:00'), (7, 'Sábado', '10:00', '22:00'),
       (7, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (8, 'Lunes', '09:00', '22:00'), (8, 'Martes', '09:00', '22:00'), (8, 'Miércoles', '09:00', '22:00'),
       (8, 'Jueves', '09:00', '22:00'), (8, 'Viernes', '09:00', '22:00'), (8, 'Sábado', '10:00', '22:00'),
       (8, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (9, 'Lunes', '09:00', '22:00'), (9, 'Martes', '09:00', '22:00'), (9, 'Miércoles', '09:00', '22:00'),
       (9, 'Jueves', '09:00', '22:00'), (9, 'Viernes', '09:00', '22:00'), (9, 'Sábado', '10:00', '22:00'),
       (9, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (10, 'Lunes', '09:00', '22:00'), (10, 'Martes', '09:00', '22:00'), (10, 'Miércoles', '09:00', '22:00'),
       (10, 'Jueves', '09:00', '22:00'), (10, 'Viernes', '09:00', '22:00'), (10, 'Sábado', '10:00', '22:00'),
       (10, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (11, 'Lunes', '09:00', '22:00'), (11, 'Martes', '09:00', '22:00'), (11, 'Miércoles', '09:00', '22:00'),
       (11, 'Jueves', '09:00', '22:00'), (11, 'Viernes', '09:00', '22:00'), (11, 'Sábado', '10:00', '22:00'),
       (11, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (12, 'Lunes', '09:00', '22:00'), (12, 'Martes', '09:00', '22:00'), (12, 'Miércoles', '09:00', '22:00'),
       (12, 'Jueves', '09:00', '22:00'), (12, 'Viernes', '09:00', '22:00'), (12, 'Sábado', '10:00', '22:00'),
       (12, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (13, 'Lunes', '09:00', '22:00'), (13, 'Martes', '09:00', '22:00'), (13, 'Miércoles', '09:00', '22:00'),
       (13, 'Jueves', '09:00', '22:00'), (13, 'Viernes', '09:00', '22:00'), (13, 'Sábado', '10:00', '22:00'),
       (13, 'Domingo', '10:00', '22:00');


INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (14, 'Lunes', '09:00', '22:00'), (14, 'Martes', '09:00', '22:00'), (14, 'Miércoles', '09:00', '22:00'),
       (14, 'Jueves', '09:00', '22:00'), (14, 'Viernes', '09:00', '22:00'), (14, 'Sábado', '10:00', '22:00'),
       (14, 'Domingo', '10:00', '22:00');


INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (15, 'Lunes', '09:00', '22:00'), (15, 'Martes', '09:00', '22:00'), (15, 'Miércoles', '09:00', '22:00'),
       (15, 'Jueves', '09:00', '22:00'), (15, 'Viernes', '09:00', '22:00'), (15, 'Sábado', '10:00', '22:00'),
       (15, 'Domingo', '10:00', '22:00');

INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (16, 'Lunes', '09:00', '22:00'), (16, 'Martes', '09:00', '22:00'), (16, 'Miércoles', '09:00', '22:00'),
       (16, 'Jueves', '09:00', '22:00'), (16, 'Viernes', '09:00', '22:00'), (16, 'Sábado', '10:00', '22:00'),
       (16, 'Domingo', '10:00', '22:00');


INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (17, 'Lunes', '09:00', '22:00'), (17, 'Martes', '09:00', '22:00'), (17, 'Miércoles', '09:00', '22:00'),
       (17, 'Jueves', '09:00', '22:00'), (17, 'Viernes', '09:00', '22:00'), (17, 'Sábado', '10:00', '22:00'),
       (17, 'Domingo', '10:00', '22:00');


INSERT INTO horarios_sucursales (nro_sucursal, dia_semana, hora_desde, hora_hasta)
VALUES (18, 'Lunes', '09:00', '22:00'), (18, 'Martes', '09:00', '22:00'), (18, 'Miércoles', '09:00', '22:00'),
       (18, 'Jueves', '09:00', '22:00'), (18, 'Viernes', '09:00', '22:00'), (18, 'Sábado', '10:00', '22:00'),
       (18, 'Domingo', '10:00', '22:00');


-- Insert into tipos_servicios_supermercado
INSERT INTO tipos_servicios_supermercado (nom_tipo_servicio) VALUES ('Delivery');
INSERT INTO tipos_servicios_supermercado (nom_tipo_servicio) VALUES ('Retiro en tienda');
INSERT INTO tipos_servicios_supermercado (nom_tipo_servicio) VALUES ('AutoServicio');

-- Assign services to all branches
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (1, 1, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (1, 2, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (2, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (3, 1, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (4, 2, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (5, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (6, 1, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (7, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (8, 2, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (9, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (10, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (11, 1, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (12, 1, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (13, 2, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (14, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (15, 2, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (16, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (17, 3, 's');
INSERT INTO tipos_servicios_sucursales (nro_sucursal, nro_tipo_servicio, vigente) VALUES (18, 2, 's');


-- Rubros de productos 5
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Aceites', 's'); -- 1
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Lácteos', 's'); -- 2
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Alimentos Secos', 's'); --3
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Panificados', 's'); --4
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Limpieza', 's'); --5
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Azucares' , 's'); -- 6


 
--Categorias de productos  12
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Aceites de Cocina', 1, 's'); --1
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Vinagres de Cocina', 1, 's'); --2
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Leches y Derivados Lácteos', 2, 's'); --3
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Harinas', 3, 's'); -- 4
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Panes de Molde', 4, 's'); --5
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Desinfectantes', 5, 's');--6
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Arroces', 3, 's'); --7
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Azúcar', 6, 's'); --8
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Yerbas', 3, 's');--9
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Pastas', 3, 's');--10
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Ingrediente de Cocina', 3, 's'); --11
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Infusiones', 3, 's'); --12



-- Tipos de productos 11 
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Aceite'); --1 
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Bebida Láctea'); --2
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Ingrediente de Cocina'); --3
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Pan de Molde'); --4
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Desinfectante');--5
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Natural');--6
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Edulcorante Natural'); --7
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Infusión'); --8
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Pasta'); --9
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Dulces'); -- 10
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Vinagres'); --11


-- Insert into marcas_productos with popular brands
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Arcor', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('La Serenísima', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Sancor', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Fargo', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Ayudin', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Pureza', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Legitimo', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Lucchetti', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('CBSé', 's');

INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Dos Anclas', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Nescafé', 's');
INSERT INTO marcas_productos (nom_marca, vigente) VALUES ('Redil', 's');




-- Vincular tipos y marcas de productos
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (7, 1, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (2, 2, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (6, 3, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (4, 4, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (5, 5, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (8, 6, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (1, 7, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (9, 8, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (1, 9, 's');

INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (9, 3, 's'); -- Dos Anclas - Ingrediente de Cocina
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (10, 8, 's'); -- Nescafé - Infusión
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (2, 10, 's'); -- La Serenisima - Dulces
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (12, 11, 's'); -- Redil - Vinagres

INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (3, 3, 's');
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (11, 8, 's');

INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (10, 3, 's');-- Redil - Vinagres
INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente) VALUES (2, 6, 's');

select * from marcas_productos where nom_marca = 'Redil'

select * from marcas_productos


-- Insert into productos with more canasta básica products
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('1', 'Aceite de Girasol', 'Aceite para cocina', 1, 'https://carrefourar.vtexassets.com/arquivos/ids/263188/7798316700808_02.jpg?v=638042211872630000', 7, 1, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('2', 'Leche Entera', 'Leche de vaca', 3, 'https://masonlineprod.vtexassets.com/arquivos/ids/283731/Leche-La-Serenisima-Larga-Vida-Cl-sica-1-L-2-43469.jpg?v=638736082887300000', 2, 2, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('3', 'Harina de Trigo', 'Harina común', 4, 'https://pureza.com.ar/wp-content/uploads/2019/06/3D_PZA-HNA-ULTRA-REF_000_FTE-web.png', 6, 3, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('4', 'Pan Lactal', 'Pan de molde', 4, 'https://www.casa-segal.com/wp-content/uploads/2020/03/pan-blanco-fargo-320g-panificados-casa-segal-mendoza-600x600.jpg', 4, 4, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('5', 'Lavandina', 'Desinfectante', 6, 'https://www.multifood.com.ar/images/000Z-009-001-02124992Z-009-001-021-AyudinMaximaPureza.jpg', 5, 5, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('6', 'Arroz Largo Fino', 'Arroz blanco', 7, 'https://almacenfamily.com/productos/arroz-lucchetti-largofino-1kg.png', 8, 6, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('7', 'Azúcar Blanca', 'Azúcar refinada', 8, 'https://laprovedeampip.com.ar/wp-content/uploads/2024/07/Azucar-arcor.png', 1, 7, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('8', 'Yerba Mate', 'Yerba clásica', 9, 'https://shop.gustoargentino.com/cdn/shop/files/Yerba-Mate-Hierbas-Serranas-500g-Cbse.png?v=1682612325&width=480', 9, 8, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('9', 'Fideos Spaghetti', 'Fideos largos', 4, 'https://arcorencasa.com/wp-content/uploads/2024/10/20241008-13478.webp', 1, 9, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('10', 'Manteca', 'Manteca cremosa', 3, 'https://latiendadelceliaco.com.ar/wp-content/uploads/2021/11/MANTECA-X200G-LA-SERENISIMA.jpg', 2, 6, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('11', 'Yogur Frutilla 900G', 'Yogur saborizado', 3, 'https://d1on8qs0xdu5jz.cloudfront.net/webapp/images/productos/b/0000012000/12051.jpg', 2, 2, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('12', 'Sal Fina', 'Sal de mesa', 11, 'https://ardiaprod.vtexassets.com/arquivos/ids/309071/Sal-Fina-en-Paquete-Celusal-500-Gr-_1.jpg?v=638599351982970000', 10, 3, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('13', 'Café Instantáneo', 'Café soluble', 12, 'https://static.cotodigital3.com.ar/sitios/fotos/large/00574300/00574317.jpg', 11, 8, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('14', 'Vinagre', 'Vinagre de manzana', 2, 'https://golozinalujan.com/wp-content/uploads/2023/01/Diseno-sin-titulo-36-6.png', 3, 3, 's');
INSERT INTO productos (cod_barra, nom_producto, desc_producto, nro_categoria, imagen, nro_marca, nro_tipo_producto, vigente)
VALUES ('15', 'Dulce de Leche', 'Dulce clasico', 3, 'https://delimart.com.ar/user-content/c464ce30-3883-4f05-a6db-148ab262c0e4.png', 2, 10, 's');



-- Insert into productos_sucursales with 10 products and slight price variation for each branch

--1
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '1', 2100.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '1', 2100.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '1', 2100.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '1', 2100.50, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '1', 2100.50, 's');

--2
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '2', 1300.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '2', 1300.00, 's');

--3
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '3', 750.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '3', 750.00, 's');


--4
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '4', 2230.00, 's');

INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '4', 2230.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '4', 2230.00, 's');


--5
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '5', 1130.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '5', 1130.00, 's');

--6
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '6', 1680.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '6', 1680.00, 's');

--7
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '7', 1299.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '7', 1299.00, 's');

--8
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '8', 3200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '8', 3200.00, 's');

--9
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '9', 1000.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '9', 1000.00, 's');


--10
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '10', 2420.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '10', 2420.00, 's');

--11
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '11', 1099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '11', 1099.00, 's');


--12
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '12', 1200.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '12', 1200.00, 's');


--13
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '13', 2310.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '13', 2310.00, 's');

--14
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '14', 1400.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '14', 1400.00, 's');

--15
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (1, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (2, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (3, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (4, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (5, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (6, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (7, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (8, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (9, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (10, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (11, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (12, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (13, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (14, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (15, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (16, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (17, '15', 2099.00, 's');
INSERT INTO productos_sucursales (nro_sucursal, cod_barra, precio, vigente) VALUES (18, '15', 2099.00, 's');




-- 4: PROCEDIMIENTOS ALMACENADOS (3 PRINCIPALES)

-- Procedimiento almacenado para obtener la informacion completa de las sucursales del supermercado
CREATE OR ALTER PROCEDURE dbo.obtener_sucursales_completas
AS
BEGIN
    SELECT 
        --s.nro_sucursal,
        s.nom_sucursal,
        s.calle,
        s.nro_calle,
        s.telefonos,
        s.coord_latitud,
        s.coord_longitud,
        l.nom_localidad,  -- Se añade el nombre de la localidad
        l.cod_provincia,  -- Se añade la provincia
        l.cod_pais,       -- Se añade el país
        s.habilitada,
        -- Concatenar los horarios en un formato de texto
        STUFF((
            SELECT ', ' + dia_semana + ': ' + 
                   LEFT(CONVERT(VARCHAR, hora_desde, 108), 5) + '-' + 
                   LEFT(CONVERT(VARCHAR, hora_hasta, 108), 5)
            FROM horarios_sucursales hs
            WHERE hs.nro_sucursal = s.nro_sucursal
            FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 2, '') AS horario_sucursal,
        -- Concatenar los servicios disponibles en un formato de texto
        STUFF((
            SELECT ', ' + ts.nom_tipo_servicio
            FROM tipos_servicios_sucursales tss
            JOIN tipos_servicios_supermercado ts ON tss.nro_tipo_servicio = ts.nro_tipo_servicio
            WHERE tss.nro_sucursal = s.nro_sucursal AND tss.vigente = 's'
            FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 2, '') AS servicios_disponibles
    FROM 
        sucursales s
    JOIN 
        localidades l ON s.nro_localidad = l.nro_localidad;
END;
GO


exec dbo.obtener_sucursales_completas


-- Procedimiento almacenado para obtener la informacion completa de todos los productos del supermercado
-- Procedimiento almacenado para obtener la informacion completa de todos los productos del supermercado
CREATE OR ALTER PROCEDURE dbo.obtener_informacion_productos_completa
AS
BEGIN
    SELECT 
        -- ps.nro_sucursal,
		s.nom_sucursal,
        p.cod_barra,
        p.nom_producto,
        p.desc_producto,
        cp.nom_categoria,
        rp.nom_rubro,
        p.imagen,
        mp.nom_marca,
        tp.nom_tipo_producto,
        tpm.vigente AS tipo_producto_marca_vigente,
        p.vigente AS producto_vigente
    FROM 
        productos p
    JOIN categorias_productos cp ON p.nro_categoria = cp.nro_categoria
    JOIN rubros_productos rp ON cp.nro_rubro = rp.nro_rubro
    JOIN marcas_productos mp ON p.nro_marca = mp.nro_marca
    JOIN tipos_productos tp ON p.nro_tipo_producto = tp.nro_tipo_producto
    JOIN tipos_productos_marcas tpm ON p.nro_marca = tpm.nro_marca 
        AND p.nro_tipo_producto = tpm.nro_tipo_producto
    JOIN productos_sucursales ps ON p.cod_barra = ps.cod_barra
	JOIN sucursales s ON s.nro_sucursal = ps.nro_sucursal
    WHERE 
        p.vigente = 's' AND ps.vigente = 's'
    ORDER BY 
        p.cod_barra;
END;
GO
exec dbo.obtener_informacion_productos_completa
select * from sucursales


-- Procedimiento almacenado para obtener los precios de los productos en sus distintas sucursales
CREATE OR ALTER PROCEDURE dbo.obtener_precios_productos
AS
BEGIN
    SELECT 
       -- s.nro_sucursal,
		s.nom_sucursal,
        p.cod_barra,
        ps.precio,
        ps.vigente
    FROM 
        productos p
    JOIN productos_sucursales ps ON p.cod_barra = ps.cod_barra
    JOIN sucursales s ON ps.nro_sucursal = s.nro_sucursal
    ORDER BY 
        s.nro_sucursal, p.cod_barra;
END;
GO

-- OTRAS YERBAS


INSERT INTO supermercado (cuit, razon_social, calle, nro_calle, telefonos) VALUES
('30-53707910-6', 'Supermercado Disco', 'General Roca ',200 ,'351-603501')

select * from supermercado

update supermercado
set razon_social = 'Supermercado Disco Cencosud'
where razon_social = 'Supermercado Disco'

select * from sucursales
