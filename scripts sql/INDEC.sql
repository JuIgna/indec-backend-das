---------------------------------------------------- SCRIPT DE BASE DE DATOS DE INDEC -------------------------------------------------------------------
-- Este script maneja la creacion/eliminacion/actualizacion de tablas de indec.
-- Solo es necesario insertar los supermercados (2 para la 3 evaluacion / 4 para el final)
-- El resto de datos: paises, provincias, localidades, sucursales, productos, precios de productos se traen por medio de los endpoints de supermercados.

-- INDICE DEL SCRIPT:
-- 1: Eliminacion de Tablas (para vaciar la base de datos)
-- 2: Creacion de Tablas
-- 3: Inserci�n de Datos
-- 4: Procedimientos Almacenados (para consultar o actualizar informacion)

drop database indec 
create database indec

use indec
go

-- 1: ELIMINACION DE TABLAS
DELETE FROM productos_supermercados;
DELETE FROM servicios_supermercados;
DELETE FROM sucursales;
DELETE FROM localidades;
DELETE FROM idiomas_rubros_productos;
DELETE FROM idiomas_categorias_productos;
DELETE FROM idiomas_tipos_productos;
DELETE FROM productos;
DELETE FROM tipos_productos;

DELETE FROM tipos_productos_marcas;
DELETE FROM categorias_productos;
DELETE FROM marcas_productos;
DELETE FROM rubros_productos;
DELETE FROM provincias;
DELETE FROM supermercados;
DELETE FROM paises;
DELETE FROM idiomas;

select * from tipos_productos



-- validar que este vacia
select * from tipos_productos_marcas
select * from marcas_productos
select * from productos
select * from categorias_productos
select * from rubros_productos
select * from sucursales



-- reiniciar los identity
DBCC CHECKIDENT ('localidades', RESEED, 0);
DBCC CHECKIDENT ('rubros_productos', RESEED, 0);
DBCC CHECKIDENT ('categorias_productos', RESEED, 0);
DBCC CHECKIDENT ('marcas_productos', RESEED, 0);
DBCC CHECKIDENT ('tipos_productos', RESEED, 0);
DBCC CHECKIDENT ('supermercados', RESEED, 0);


-- 2: CREACION DE LAS TABLAS

create table paises
(
	cod_pais varchar(10) not null,
	nom_pais varchar(60) not null,
	-- a�adir el atributo local ??
	
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

create table supermercados 
(
	nro_supermercado integer not null identity,
	razon_social varchar(50) not null,
	cuit varchar(30) not null,

	constraint PK__supermercados primary key (nro_supermercado)
)
go

create table servicios_supermercados
(
	nro_supermercado integer not null,
	url_servicio varchar(300) not null,
	tipo_servicio varchar(50) not null,
	token_servicio varchar (300) not null,
	fecha_ult_act_servicio smalldatetime not null,

	constraint PK__servicios_supermercados primary key (nro_supermercado),
	constraint FK__servicios_supermercados__supermercados foreign key (nro_supermercado) references supermercados
)
go



create table idiomas 
(
	cod_idioma varchar(10) not null,
	nom_idioma varchar(15) not null,

	constraint PK__idiomas primary key (cod_idioma)
)
go

create table rubros_productos -- 1
(
	nro_rubro smallint not null identity,
	nom_rubro varchar(50) not null,
	vigente char(1) not null,

	constraint PK__rubros_productos primary key (nro_rubro),
	constraint CK__rubros_productos__vigente check (vigente in ('s','n'))
)
go

create table idiomas_rubros_productos
(
	nro_rubro smallint not null,
	cod_idioma varchar(10) not null,
	rubro varchar(50) not null,

	constraint PK__idiomas_rubros_productos primary key (nro_rubro, cod_idioma),
	constraint FK__idiomas_rubros_productos__rubros_productos foreign key (nro_rubro) references rubros_productos,
	constraint FK__idiomas_rubros_productos__idiomas foreign key (cod_idioma) references idiomas

)
go

create table categorias_productos --2 
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

create table idiomas_categorias_productos
(
	nro_categoria smallint not null,
	cod_idioma varchar(10) not null,
	categoria varchar(50) not null,

	constraint PK__idiomas_categorias_productos primary key (nro_categoria, cod_idioma),
	constraint FK__idiomas_categorias_productos__categorias_productos foreign key (nro_categoria) references categorias_productos,
	constraint FK__idiomas_categorias_productos__idiomas foreign key (cod_idioma) references idiomas
)
go

create table marcas_productos --3
(
	nro_marca integer not null identity,
	nom_marca varchar(50) not null,
	vigente char(1) not null,

	constraint PK__marcas_productos primary key (nro_marca),
	constraint CK__marcas_productos__vigente check (vigente in ('s','n'))
)
go

create table tipos_productos --4
(
	nro_tipo_producto smallint not null identity,
	nom_tipo_producto varchar(50) not null,

	constraint PK__tipos_productos primary key (nro_tipo_producto)
)
go

create table tipos_productos_marcas --5
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

create table idiomas_tipos_productos 
(
	nro_tipo_producto smallint not null,
	cod_idioma varchar(10) not null,
	tipo_producto varchar(50) not null,

	constraint PK__idiomas_tipos_productos primary key (nro_tipo_producto, cod_idioma),
	constraint FK__idiomas_tipos_productos__idiomas foreign key (cod_idioma) references idiomas,
	constraint FK__idiomas_tipos_productos_idiomas__tipos_productos foreign key (nro_tipo_producto) references tipos_productos
)
go

create table productos --6
( 
	cod_barra varchar(255) not null,
	nom_producto varchar(50) not null,
	desc_producto varchar(120) not null,
	nro_categoria smallint not null,
	imagen varchar(255)  null,
	nro_marca integer not null,
	nro_tipo_producto smallint not null,
	vigente char(1) not null,

	constraint PK__productos primary key (cod_barra),
	constraint FK__productos__categorias_productos foreign key (nro_categoria) references categorias_productos,
	constraint FK__productos__tipos_productos_marcas foreign key (nro_marca, nro_tipo_producto) references tipos_productos_marcas,
	constraint CK__productos__vigente check (vigente in('s','n')),
)
go



create table sucursales
(
	nro_supermercado integer not null,
	nro_sucursal integer identity not null,
	nom_sucursal varchar(50) not null,
	calle varchar(60) not null,
	nro_calle varchar(10) not null,
	telefonos varchar(60) not null,
	coord_latitud varchar(255) not null,
	coord_longitud varchar(255) not null,
	horario_sucursal varchar(160) null,
	servicios_disponibles varchar(160) null,
	nro_localidad integer not null,
	habilitada char(1) not null,
	
	constraint PK__sucursales primary key(nro_supermercado, nro_sucursal),
	constraint AK1__sucursales unique (nom_sucursal),
	constraint FK__sucurusales__localidades foreign key (nro_localidad) references localidades,
	constraint FK__sucursales__supermercados foreign key (nro_supermercado) references supermercados,
	constraint CK__habilitada check (habilitada in ('s','n'))
)
go

alter table 



create table productos_supermercados
(
	nro_supermercado integer not null,
	nro_sucursal integer not null,
	cod_barra varchar(255) not null,
	precio decimal(10,2) ,
	fecha_ult_actualizacion smalldatetime,

	constraint PK__productos_supermercados primary key(nro_supermercado, nro_sucursal, cod_barra),
	constraint FK__productos_supermercados__productos foreign key (cod_barra) references productos,
	constraint FK__productos_supermercados__sucursales foreign key (nro_supermercado, nro_sucursal) references sucursales

)
go

ALTER TABLE productos_supermercados
alter COLUMN fecha_ult_actualizacion smalldatetime NULL;

ALTER TABLE productos_supermercados
alter COLUMN precio decimal(10,2) NULL;





-- 3: INSERCION DE DATOS
-- Insertamos la informacion de los supermercados con los que INDEC trabaja

-- Insertar los dos supermercados (SOAP y REST)

-- Supermercado1 con rest
INSERT INTO supermercados (razon_social, cuit)
VALUES ('Supermercado Disco', 'rest123');

-- Supermercado2 con soap
INSERT INTO supermercados (razon_social, cuit)
VALUES ('Supermercado Dia', 'WS123');

-- Supermercado3 con rest
INSERT INTO supermercados (razon_social, cuit)
VALUES ('Supermercado Vea', 'rest1234');

-- Supermercado4 con soap
INSERT INTO supermercados (razon_social, cuit)
VALUES ('Supermercado Carrefour', 'WS1234');


-- Insertar servicio para el supermercado REST

INSERT INTO servicios_supermercados (nro_supermercado, url_servicio, tipo_servicio, token_servicio, fecha_ult_act_servicio)
VALUES (1, 'http://localhost:8082/api/v1/supermercado1', 'REST', 'rest123', GETDATE());


INSERT INTO servicios_supermercados (nro_supermercado, url_servicio, tipo_servicio, token_servicio, fecha_ult_act_servicio)
VALUES (3, 'http://localhost:8083/api/v1/supermercado3', 'REST', 'rest1234', GETDATE());

-- Insertar servicio para el supermercado SOAP

INSERT INTO servicios_supermercados (nro_supermercado, url_servicio, tipo_servicio, token_servicio, fecha_ult_act_servicio)
VALUES (2, 'http://localhost:8080/services/supermercado.wsdl', 'SOAP', 'WS123', GETDATE());


INSERT INTO servicios_supermercados (nro_supermercado, url_servicio, tipo_servicio, token_servicio, fecha_ult_act_servicio)
VALUES (4, 'http://localhost:8081/services/supermercado.wsdl', 'SOAP', 'WS1234', GETDATE());

-- Ejemplo de formato de url SOAP: http://localhost:8080/services/supermercado.wsdl, http://localhost:8081/services/supermercado.wsdl
-- Ejemplo de formato de url REST: http://localhost:8082/api/v1/supermercado1 , http://localhost:8083/api/v1/supermercado1


exec obtener_supermercados


-- Insertamos la informacion de Paises, Provincias y Localidades que son compartidos entre INDEC y los Supermercados
-- Insert into paises
INSERT INTO paises (cod_pais, nom_pais) VALUES ('AR', 'Argentina');

-- Insert into provincias
INSERT INTO provincias (cod_pais, cod_provincia, nom_provincia) VALUES ('AR', 'BA', 'Buenos Aires');
INSERT INTO provincias (cod_pais, cod_provincia, nom_provincia) VALUES ('AR', 'CBA', 'C�rdoba');
INSERT INTO provincias (cod_pais, cod_provincia, nom_provincia) VALUES ('AR', 'SF', 'Santa Fe');

-- Insert into localidades for Buenos Aires
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('La Plata', 'AR', 'BA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Mar del Plata', 'AR', 'BA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Tandil', 'AR', 'BA');

-- Insert into localidades for C�rdoba
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('C�rdoba Capital', 'AR', 'CBA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Villa Carlos Paz', 'AR', 'CBA');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('R�o Cuarto', 'AR', 'CBA');

-- Insert into localidades for Santa Fe
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Rosario', 'AR', 'SF');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Santa Fe Capital', 'AR', 'SF');
INSERT INTO localidades (nom_localidad, cod_pais, cod_provincia) VALUES ('Reconquista', 'AR', 'SF');


INSERT INTO idiomas (cod_idioma, nom_idioma) VALUES ('es-AR', 'Espa�ol');
INSERT INTO idiomas (cod_idioma, nom_idioma) VALUES ('en', 'English');


-- Rubros de productos 5
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Aceites', 's'); -- 1
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('L�cteos', 's'); -- 2
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Alimentos Secos', 's'); --3
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Panificados', 's'); --4
INSERT INTO rubros_productos (nom_rubro, vigente) VALUES ('Limpieza', 's'); --5


 
--Categorias de productos  12
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Aceites de Cocina', 1, 's'); --1
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Vinagres de Cocina', 1, 's'); --2
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Leches y Derivados L�cteos', 2, 's'); --3
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Harinas', 3, 's'); -- 4
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Panes de Molde', 4, 's'); --5
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Desinfectantes', 5, 's');--6
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Arroces', 3, 's'); --7
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Az�car', 3, 's'); --8
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Yerbas', 3, 's');--9
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Pastas', 3, 's');--10
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Ingrediente de Cocina', 3, 's'); --11
INSERT INTO categorias_productos (nom_categoria, nro_rubro, vigente) VALUES ('Infusiones', 3, 's'); --12



-- Tipos de productos 11 
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Aceite'); --1 
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Bebida L�ctea'); --2
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Ingrediente de Cocina'); --3
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Pan de Molde'); --4
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Desinfectante');--5
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Natural');--6
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Edulcorante Natural'); --7
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Infusi�n'); --8
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Pasta'); --9
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Dulces'); -- 10
INSERT INTO tipos_productos (nom_tipo_producto) VALUES ('Vinagres'); --11


-- Rubros de productos en ingl�s y espa�ol
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (1, 'en', 'Oils');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (1, 'es-AR', 'Aceites');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (2, 'en', 'Dairy');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (2, 'es-AR', 'L�cteos');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (3, 'en', 'Dry Foods');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (3, 'es-AR', 'Alimentos Secos');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (4, 'en', 'Baked Goods');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (4, 'es-AR', 'Panificados');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (5, 'en', 'Cleaning');
INSERT INTO idiomas_rubros_productos (nro_rubro, cod_idioma, rubro) VALUES (5, 'es-AR', 'Limpieza');


-- Categor�as de productos en ingl�s y espa�ol
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (1, 'en', 'Cooking Oils');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (1, 'es-AR', 'Aceites de Cocina');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (2, 'en', 'Cooking Vinegars');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (2, 'es-AR', 'Vinagres de Cocina');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (3, 'en', 'Milk and Dairy Products');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (3, 'es-AR', 'Leches y Derivados L�cteos');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (4, 'en', 'Flours');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (4, 'es-AR', 'Harinas');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (5, 'en', 'Sliced Bread');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (5, 'es-AR', 'Panes de Molde');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (6, 'en', 'Disinfectants');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (6, 'es-AR', 'Desinfectantes');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (7, 'en', 'Rice');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (7, 'es-AR', 'Arroces');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (8, 'en', 'Sugar');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (8, 'es-AR', 'Az�car');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (9, 'en', 'Yerba Mate');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (9, 'es-AR', 'Yerbas');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (10, 'en', 'Pasta');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (10, 'es-AR', 'Pastas');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (11, 'es-AR', 'Ingrediente de Cocina');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (11, 'es-AR', 'Infusiones');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (12, 'en', 'Cooking Ingredient');
INSERT INTO idiomas_categorias_productos (nro_categoria, cod_idioma, categoria) VALUES (12, 'en', 'Infusions');



-- Tipos de productos en ingl�s y espa�ol
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (1, 'en', 'Oil');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (1, 'es-AR', 'Aceite');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (2, 'en', 'Dairy Beverage');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (2, 'es-AR', 'Bebida L�ctea');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (3, 'en', 'Cooking Ingredient');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (3, 'es-AR', 'Ingrediente de Cocina');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (4, 'en', 'Sliced Bread');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (4, 'es-AR', 'Pan de Molde');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (5, 'en', 'Disinfectant');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (5, 'es-AR', 'Desinfectante');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (6, 'en', 'Natural');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (6, 'es-AR', 'Natural');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (7, 'en', 'Natural Sweetener');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (7, 'es-AR', 'Edulcorante Natural');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (8, 'en', 'Infusion');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (8, 'es-AR', 'Infusi�n');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (9, 'en', 'Pasta');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (9, 'es-AR', 'Pasta');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (10, 'es-AR', 'Dulces');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (10, 'en', 'Sweetmeats');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (11, 'es-AR', 'Vinagres');
INSERT INTO idiomas_tipos_productos (nro_tipo_producto, cod_idioma, tipo_producto) VALUES (11, 'en', 'Vinegars');



-- 4: Procedimientos Almacenados para INDEC

-- PROCEDIMIENTOS DE REGIONALIZACION

-- Procedimiento para obtener idiomas
create or alter procedure dbo.obtener_idiomas
as
begin
	select cod_idioma, nom_idioma
	from idiomas
end

exec obtener_idiomas


-- Procedimiento para obtener la traduccion de rubros, categorias y tipos de productos al idioma seleccionado
create or alter procedure dbo.obtener_traduccion
    @cod_idioma VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    -- Rubros de productos con fallback al espa�ol
    SELECT 
        r.nro_rubro,
        COALESCE(ir.rubro, r.nom_rubro) AS nom_rubro
    FROM rubros_productos r
    LEFT JOIN idiomas_rubros_productos ir 
        ON r.nro_rubro = ir.nro_rubro 
        AND ir.cod_idioma = @cod_idioma;

    -- Categor�as de productos con fallback al espa�ol
    SELECT 
        c.nro_categoria,
        c.nro_rubro,
        COALESCE(ic.categoria, c.nom_categoria) AS nom_categoria
    FROM categorias_productos c
    LEFT JOIN idiomas_categorias_productos ic 
        ON c.nro_categoria = ic.nro_categoria 
        AND ic.cod_idioma = @cod_idioma;

    -- Tipos de productos con fallback al espa�ol
    SELECT 
        t.nro_tipo_producto,
        COALESCE(it.tipo_producto, t.nom_tipo_producto) AS nom_tipo_producto
    FROM tipos_productos t
    LEFT JOIN idiomas_tipos_productos it 
        ON t.nro_tipo_producto = it.nro_tipo_producto 
        AND it.cod_idioma = @cod_idioma;
END;
GO

exec obtener_traduccion @cod_idioma = 'ENG'



-- PROCEDIMIENTO PARA OBTENER PRODUCTOS
CREATE OR ALTER PROCEDURE dbo.obtener_productos_completos
AS
BEGIN
    SELECT 
        p.cod_barra,
        p.nom_producto,
        p.desc_producto,
        p.nro_categoria,
        c.nom_categoria,
        c.nro_rubro,
        r.nom_rubro,
        p.nro_marca,
        m.nom_marca,
        p.nro_tipo_producto,
        t.nom_tipo_producto,
        p.vigente,
        p.imagen
    FROM productos p
    INNER JOIN categorias_productos c ON p.nro_categoria = c.nro_categoria
    INNER JOIN rubros_productos r ON c.nro_rubro = r.nro_rubro
    INNER JOIN marcas_productos m ON p.nro_marca = m.nro_marca
    INNER JOIN tipos_productos t ON p.nro_tipo_producto = t.nro_tipo_producto
    WHERE p.vigente = 's'; -- Opcional: Filtrar solo los productos vigentes
END;
GO


exec dbo.obtener_productos_completos


-- PROCEDIMIENTOS DE CONSULTA DE INFORMACION 
-- Procedimiento Almacenado Para Obtener Supermercados
CREATE OR ALTER PROCEDURE dbo.obtener_supermercados
AS
BEGIN
    SELECT 
        s.nro_supermercado,
        s.razon_social,
        s.cuit ,
        ss.tipo_servicio ,
        ss.url_servicio,
        ss.token_servicio
    FROM 
        supermercados s
    INNER JOIN 
        servicios_supermercados ss ON s.nro_supermercado = ss.nro_supermercado;
END;
GO

exec dbo.obtener_supermercados



-- PROCEDIMIENTOS DE LOCALIDADES 

-- Procedimiento almacenado para obtener paises
CREATE OR ALTER PROCEDURE dbo.obtener_paises
AS
BEGIN
    SELECT cod_pais, nom_pais
    FROM paises
    ORDER BY nom_pais;
END;

exec dbo.obtener_paises


-- procedimiento almacenado para obtener provincias
CREATE OR ALTER PROCEDURE dbo.obtener_provincias
    @cod_pais VARCHAR(10)
AS
BEGIN
    SELECT cod_provincia, nom_provincia
    FROM provincias
    WHERE cod_pais = @cod_pais
    ORDER BY nom_provincia;
END;

exec dbo.obtener_provincias @cod_pais = 'AR'

-- Procedimiento almacenado para obtener las lodalidades de indec
CREATE OR ALTER PROCEDURE obtener_localidades_indec
AS
BEGIN
    SELECT nro_localidad, nom_localidad, cod_provincia, cod_pais
    FROM localidades;
END;

exec obtener_localidades_indec

-- procedimiento almacenado para obtener localidades
CREATE OR ALTER PROCEDURE dbo.obtener_localidades
    @cod_pais VARCHAR(10),
    @cod_provincia VARCHAR(10)
AS
BEGIN
    SELECT nro_localidad, nom_localidad
    FROM localidades
    WHERE cod_pais = @cod_pais
      AND cod_provincia = @cod_provincia
    ORDER BY nom_localidad;
END;

exec dbo.obtener_localidades @cod_pais ='AR', @cod_provincia = 'CBA'


-- SUCURSALES POR LOCALIDAD
CREATE or alter PROCEDURE dbo.obtener_sucursales_por_localidad
    @nro_localidad INT,
	@lista_supermercados VARCHAR (MAX)	
AS
BEGIN
    SELECT 
        s.nro_supermercado,
        sm.razon_social AS nom_supermercado,
        s.nro_sucursal, --borrar 
        s.nom_sucursal,
        s.calle,
        s.nro_calle,
        s.telefonos,
        s.coord_latitud,
        s.coord_longitud,
        s.horario_sucursal,
        s.servicios_disponibles,
        l.nom_localidad,  -- Nombre de la localidad
        p.nom_provincia,  -- Nombre de la provincia
        pa.nom_pais,      -- Nombre del pa�s
        s.habilitada
    FROM 
        sucursales s
    JOIN 
        localidades l ON s.nro_localidad = l.nro_localidad
    JOIN 
        provincias p ON l.cod_pais = p.cod_pais AND l.cod_provincia = p.cod_provincia
    JOIN 
        paises pa ON p.cod_pais = pa.cod_pais
    JOIN 
        supermercados sm ON s.nro_supermercado = sm.nro_supermercado
    WHERE
        s.nro_localidad = @nro_localidad
		AND s.nro_supermercado IN (
			SELECT TRY_CAST (value AS INT)
			FROM STRING_SPLIT (@lista_supermercados, ',')
			WHERE TRY_CAST (value AS INT) IS NOT NULL
			);
END;
GO


CREATE OR ALTER PROCEDURE dbo.obtener_sucursales_por_localidad
    @nro_localidad INT,
    @lista_supermercados VARCHAR(MAX)	
AS
BEGIN
    SET NOCOUNT ON;

    -- Tabla temporal para almacenar los supermercados
    CREATE TABLE #SupermercadosSolicitados (
        nro_supermercado INT
    );

    -- Insertar los supermercados en la tabla temporal
    INSERT INTO #SupermercadosSolicitados (nro_supermercado)
    SELECT DISTINCT TRY_CAST(value AS INT)
    FROM STRING_SPLIT(@lista_supermercados, ',')
    WHERE TRY_CAST(value AS INT) IS NOT NULL;

    -- Consulta principal
    SELECT 
        s.nro_supermercado,
        sm.razon_social AS nom_supermercado,
        s.nro_sucursal,
        s.nom_sucursal,
        s.calle,
        s.nro_calle,
        s.telefonos,
        s.coord_latitud,
        s.coord_longitud,
        s.horario_sucursal,
        s.servicios_disponibles,
        l.nom_localidad,
        p.nom_provincia,
        pa.nom_pais,
        s.habilitada
    FROM 
        sucursales s
    JOIN 
        localidades l ON s.nro_localidad = l.nro_localidad
    JOIN 
        provincias p ON l.cod_pais = p.cod_pais AND l.cod_provincia = p.cod_provincia
    JOIN 
        paises pa ON p.cod_pais = pa.cod_pais
    JOIN 
        supermercados sm ON s.nro_supermercado = sm.nro_supermercado
    WHERE
        s.nro_localidad = @nro_localidad
        AND s.nro_supermercado IN (SELECT nro_supermercado FROM #SupermercadosSolicitados);
END;

select * from localidades -- 4
select * from supermercados

use indec

exec obtener_sucursales_por_localidad @nro_localidad = 4 , @lista_supermercados = '1,2,3,4'

select * from productos_supermercados


-- PROCEDIMIENTOS DEL COMPARAR PRECIOS 
CREATE OR ALTER PROCEDURE dbo.comparar_precios (
    @codigos_barras VARCHAR(MAX),
    @nro_localidad INT
)
AS
BEGIN
    SET NOCOUNT ON;

    -- Validar parámetros de entrada
    IF @codigos_barras IS NULL OR @codigos_barras = '' OR @nro_localidad IS NULL
    BEGIN
        RAISERROR ('Parámetros inválidos: codigos_barras o nro_localidad no pueden ser nulos o vacíos.', 16, 1);
        RETURN;
    END;

    -- Tabla temporal para almacenar los códigos de barra recibidos
    CREATE TABLE #ProductosSolicitados (
        cod_barra BIGINT
    );

    -- Insertar los códigos de barra en la tabla temporal
    INSERT INTO #ProductosSolicitados (cod_barra)
    SELECT DISTINCT TRY_CAST(value AS BIGINT)
    FROM STRING_SPLIT(@codigos_barras, ',')
    WHERE TRY_CAST(value AS BIGINT) IS NOT NULL;

    -- Obtener todos los supermercados en la localidad y el mejor precio de cada producto
    SELECT 
        p.cod_barra,
        p.nom_producto,
        p.imagen,
        sm.nro_supermercado,
        sm.razon_social,
        COALESCE(MIN(ps.precio), 0) AS mejor_precio
    FROM supermercados sm
    JOIN sucursales s ON sm.nro_supermercado = s.nro_supermercado
    CROSS JOIN productos p
    LEFT JOIN productos_supermercados ps ON p.cod_barra = ps.cod_barra 
        AND ps.nro_supermercado = s.nro_supermercado 
        AND ps.nro_sucursal = s.nro_sucursal
    WHERE p.cod_barra IN (SELECT cod_barra FROM #ProductosSolicitados)
        AND s.nro_localidad = @nro_localidad
    GROUP BY 
        p.cod_barra,
        p.nom_producto,
        p.imagen,
        sm.nro_supermercado,
        sm.razon_social
    ORDER BY p.cod_barra, sm.nro_supermercado;
END;

exec dbo.comparar_precios @codigos_barras = '1,10', @nro_localidad = 2



-- LOS 3 PROCEDIMIENTOS DE ACTUALIZACION DE INFORMACION BATCH:
-- NUEVO: SUCURSALES con JSON
CREATE OR ALTER PROCEDURE dbo.actualizar_sucursal
    @nro_supermercado INT,
    @json_sucursales NVARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;

    -- Crear tabla temporal para almacenar el JSON sin nro_sucursal
    CREATE TABLE #TempSucursales (
        nom_sucursal VARCHAR(50),
        calle VARCHAR(60),
        nro_calle VARCHAR(10),
        telefonos VARCHAR(60),
        coord_latitud VARCHAR(255),
        coord_longitud VARCHAR(255),
        nom_localidad VARCHAR(100),
        cod_provincia VARCHAR(10),
        cod_pais VARCHAR(10),
        habilitada VARCHAR(1),
        horario_sucursal VARCHAR(160),
        servicios_disponibles VARCHAR(160),
        nro_localidad INT NULL
    );

    -- Insertar datos del JSON en la tabla temporal
    INSERT INTO #TempSucursales (nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, 
                                 nom_localidad, cod_provincia, cod_pais, habilitada, horario_sucursal, servicios_disponibles)
    SELECT 
        nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, 
        nom_localidad, cod_provincia, cod_pais, habilitada, horario_sucursal, servicios_disponibles
    FROM OPENJSON(@json_sucursales)
    WITH (
        nom_sucursal VARCHAR(50),
        calle VARCHAR(60),
        nro_calle VARCHAR(10),
        telefonos VARCHAR(60),
        coord_latitud VARCHAR(255),
        coord_longitud VARCHAR(255),
        nom_localidad VARCHAR(100),
        cod_provincia VARCHAR(10),
        cod_pais VARCHAR(10),
        habilitada VARCHAR(1),
        horario_sucursal VARCHAR(160),
        servicios_disponibles VARCHAR(160)
    );

    -- Asignar nro_localidad desde la tabla localidades
    UPDATE t
    SET nro_localidad = l.nro_localidad
    FROM #TempSucursales t
    INNER JOIN localidades l ON 
        t.nom_localidad = l.nom_localidad 
        AND t.cod_provincia = l.cod_provincia 
        AND t.cod_pais = l.cod_pais;

    -- **Actualizar sucursales existentes en INDEC por nom_sucursal**
    UPDATE s
    SET 
        s.calle = t.calle,
        s.nro_calle = t.nro_calle,
        s.telefonos = t.telefonos,
        s.coord_latitud = t.coord_latitud,
        s.coord_longitud = t.coord_longitud,
        s.horario_sucursal = t.horario_sucursal,
        s.servicios_disponibles = t.servicios_disponibles,
        s.nro_localidad = t.nro_localidad,
        s.habilitada = t.habilitada
    FROM sucursales s
    INNER JOIN #TempSucursales t 
        ON s.nro_supermercado = @nro_supermercado 
        AND s.nom_sucursal = t.nom_sucursal;

    -- **Insertar solo sucursales con nom_sucursal que no existen**
    INSERT INTO sucursales (nro_supermercado, nom_sucursal, calle, nro_calle, telefonos, coord_latitud, coord_longitud, horario_sucursal, servicios_disponibles, nro_localidad, habilitada)
    SELECT @nro_supermercado, t.nom_sucursal, t.calle, t.nro_calle, t.telefonos, t.coord_latitud, t.coord_longitud, t.horario_sucursal, t.servicios_disponibles, t.nro_localidad, t.habilitada
    FROM #TempSucursales t
    WHERE NOT EXISTS (
        SELECT 1 FROM sucursales s 
        WHERE s.nro_supermercado = @nro_supermercado 
        AND s.nom_sucursal = t.nom_sucursal
    );

END;
GO


-- PROCEDIMIENTOS DE ACTUALIZACION DE INFORMACION POR BATCH
-- NUEVO: Productos con JSON
CREATE OR ALTER PROCEDURE dbo.actualizar_productos
    @json_productos NVARCHAR(MAX),
    @nro_supermercado INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Tabla temporal para cargar los productos del JSON
    CREATE TABLE #TempProductos (
        nro_sucursal INT,
        cod_barra VARCHAR(255),
        nom_producto VARCHAR(60),
        desc_producto VARCHAR(120),
        nom_categoria VARCHAR(50),
        nom_rubro VARCHAR(50),
        nom_marca VARCHAR(50),
        nom_tipo_producto VARCHAR(60),
        imagen VARCHAR(255),
        tipo_producto_marca_vigente VARCHAR(1),
        producto_vigente VARCHAR(1),
        nro_categoria SMALLINT NULL,
        nro_rubro SMALLINT NULL,
        nro_marca INT NULL,
        nro_tipo_producto SMALLINT NULL
    );

    -- Insertar datos desde JSON
    INSERT INTO #TempProductos (nro_sucursal, cod_barra, nom_producto, desc_producto, nom_categoria, nom_rubro, nom_marca, nom_tipo_producto, imagen, tipo_producto_marca_vigente, producto_vigente)
    SELECT 
        nro_sucursal,
        cod_barra,
        nom_producto,
        desc_producto,
        nom_categoria,
        nom_rubro,
        nom_marca,
        nom_tipo_producto,
        imagen,
        tipo_producto_marca_vigente,
        producto_vigente
    FROM OPENJSON(@json_productos)
    WITH (
        nro_sucursal INT,
        cod_barra VARCHAR(255),
        nom_producto VARCHAR(60),
        desc_producto VARCHAR(120),
        nom_categoria VARCHAR(50),
        nom_rubro VARCHAR(50),
        nom_marca VARCHAR(50),
        nom_tipo_producto VARCHAR(60),
        imagen VARCHAR(255),
        tipo_producto_marca_vigente VARCHAR(1),
        producto_vigente VARCHAR(1)
    );

    -- Mapear Rubros
    UPDATE t
    SET nro_rubro = r.nro_rubro
    FROM #TempProductos t
    INNER JOIN rubros_productos r ON t.nom_rubro = r.nom_rubro;

    -- Mapear Categor�as
    UPDATE t
    SET nro_categoria = c.nro_categoria
    FROM #TempProductos t
    INNER JOIN categorias_productos c ON t.nom_categoria = c.nom_categoria AND t.nro_rubro = c.nro_rubro;

	-- Insertar nuevas marcas si no existen (y asignar 'S' por defecto a vigente)
	INSERT INTO marcas_productos (nom_marca, vigente)
	SELECT DISTINCT t.nom_marca, 'S'  -- Asignamos 'S' como valor por defecto
	FROM #TempProductos t
	LEFT JOIN marcas_productos m ON t.nom_marca = m.nom_marca
	WHERE m.nom_marca IS NULL;


	-- Mapear Marcas (despu�s de insertar nuevas)
	UPDATE t
	SET nro_marca = m.nro_marca
	FROM #TempProductos t
	INNER JOIN marcas_productos m ON t.nom_marca = m.nom_marca;


    -- Mapear Tipos de Producto
    UPDATE t
    SET nro_tipo_producto = tp.nro_tipo_producto
    FROM #TempProductos t
    INNER JOIN tipos_productos tp ON t.nom_tipo_producto = tp.nom_tipo_producto;

	-- Asegurar que existen combinaciones en tipos_productos_marcas
	INSERT INTO tipos_productos_marcas (nro_marca, nro_tipo_producto, vigente)
	SELECT DISTINCT t.nro_marca, t.nro_tipo_producto, ISNULL(t.tipo_producto_marca_vigente, 'S')  -- Si es NULL, asignar 'S'
	FROM #TempProductos t
	WHERE NOT EXISTS (
		SELECT 1 FROM tipos_productos_marcas tpm
		WHERE tpm.nro_marca = t.nro_marca
		AND tpm.nro_tipo_producto = t.nro_tipo_producto
	);



    -- Insertar o actualizar en productos
    MERGE INTO productos AS target
    USING (
        SELECT DISTINCT cod_barra, nom_producto, desc_producto, nro_categoria, nro_marca, nro_tipo_producto, imagen, producto_vigente
        FROM #TempProductos
    ) AS source
    ON target.cod_barra = source.cod_barra
    WHEN MATCHED THEN 
        UPDATE SET 
            target.nom_producto = source.nom_producto,
            target.desc_producto = source.desc_producto,
            target.nro_categoria = source.nro_categoria,
            target.nro_marca = source.nro_marca,
            target.nro_tipo_producto = source.nro_tipo_producto,
            target.imagen = source.imagen,
            target.vigente = source.producto_vigente
    WHEN NOT MATCHED THEN
        INSERT (cod_barra, nom_producto, desc_producto, nro_categoria, nro_marca, nro_tipo_producto, imagen, vigente)
        VALUES (source.cod_barra, source.nom_producto, source.desc_producto, source.nro_categoria, source.nro_marca, source.nro_tipo_producto, source.imagen, source.producto_vigente);

    -- Asociar productos con la sucursal del supermercado
    INSERT INTO productos_supermercados (nro_supermercado, nro_sucursal, cod_barra)
    SELECT DISTINCT @nro_supermercado, nro_sucursal, cod_barra
    FROM #TempProductos
    WHERE NOT EXISTS (
        SELECT 1 FROM productos_supermercados ps
        WHERE ps.nro_supermercado = @nro_supermercado
        AND ps.nro_sucursal = #TempProductos.nro_sucursal
        AND ps.cod_barra = #TempProductos.cod_barra
    );

    DROP TABLE #TempProductos;
END;
GO



CREATE OR ALTER PROCEDURE dbo.actualizar_precios_productos
    @json_precios NVARCHAR(MAX),
    @nro_supermercado INT
AS
BEGIN
    SET NOCOUNT ON;

    CREATE TABLE #TempPrecios (
        nro_supermercado INT,
        nro_sucursal INT,
        cod_barra VARCHAR(255),
        precio DECIMAL(10,2),
        vigente VARCHAR(1)

    );

    INSERT INTO #TempPrecios (nro_supermercado, nro_sucursal, cod_barra, precio, vigente)
    SELECT 
        @nro_supermercado,
        nro_sucursal,
        cod_barra,
        precio,
        vigente
    FROM OPENJSON(@json_precios)
    WITH (
        nro_sucursal INT,
        cod_barra VARCHAR(255),
        precio DECIMAL(10,2),
        vigente VARCHAR(1)
    );

    -- Actualizar solo si el producto est� vigente ('s')
    UPDATE ps
    SET 
        ps.precio = tp.precio,
        ps.fecha_ult_actualizacion = GETDATE()
    FROM productos_supermercados ps
    INNER JOIN #TempPrecios tp
        ON ps.nro_supermercado = tp.nro_supermercado
        AND ps.nro_sucursal = tp.nro_sucursal
        AND ps.cod_barra  = tp.cod_barra
    WHERE tp.vigente = 's';

    -- Insertar nuevos registros solo si est�n vigentes ('s')
    INSERT INTO productos_supermercados (nro_supermercado, nro_sucursal, cod_barra, precio, fecha_ult_actualizacion)
    SELECT tp.nro_supermercado, tp.nro_sucursal, tp.cod_barra, tp.precio, GETDATE()
    FROM #TempPrecios tp
    WHERE tp.vigente = 's' AND NOT EXISTS (
        SELECT 1 FROM productos_supermercados ps
        WHERE ps.nro_supermercado = tp.nro_supermercado
        AND ps.nro_sucursal = tp.nro_sucursal
        AND ps.cod_barra = tp.cod_barra
    );


    delete ps  FROM productos_supermercados ps
		    JOIN #TempPrecios tp
            ON ps.nro_supermercado = tp.nro_supermercado
        AND ps.nro_sucursal = tp.nro_sucursal
        AND ps.cod_barra  = tp.cod_barra
        WHERE tp.vigente = 'n'

END;
GO

select * from sucursales
