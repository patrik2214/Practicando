 --C ciudad,R rural,S selva
create table ciudad(
codigoCiudad int not null primary key,
nombre varchar(30) not null,
tipoCiudad char(1) not null)

create table cliente(
dni char(8) not null primary key,
nombres varchar(80) not null,
direccion varchar(50) not null,
codigoCiudad int not null,
vigencia boolean not null);

alter table cliente add constraint FK_Cli_Ciu foreign key (codigoCiudad) references ciudad;

create table tipo_Unidad(
codigoTipo int not null primary key,
nombreTipo varchar(30) not null,
descripcion varchar(100) not null,
vigencia boolean not null);

create table Marca(
codigoMarca int not null primary key,
nombre varchar(30) not null);

create table unidad(
codigoUnidad int not null primary key,
placa char(6) not null,
codigoMarca int not null,
modelo varchar(30) not null,
año integer not null,
color varchar(20) not null,
numAsientos integer not null,
estado boolean not null,
codigoTipo int not null,
dni char(8) not null);

alter table unidad add constraint FK_Uni_Tip foreign key (codigoTipo) references tipo_unidad;
alter table unidad add constraint FK_Uni_Cli foreign key (dni) references cliente;
alter table unidad add constraint FK_Uni_Mar foreign key (codigoMarca) references marca;

insert into marca values(1,'Honda');
insert into marca values(2,'Suzuki');
insert into marca values(3,'Toyota');
insert into marca values(4,'Mitsubishi');
insert into marca values(5,'Nissan');

insert into ciudad values(1,'Lima','C');
insert into ciudad values(2,'Olmos','R');
insert into ciudad values(3,'Iquitos','S');
insert into ciudad values(4,'Trujillo','C');
insert into ciudad values(5,'Nueva Cajamarca','R');

insert into tipo_unidad values(1,'Auto','Auto familiar 5 asientos',true);
insert into tipo_unidad values(2,'Camioneta cerrada','6 asientos',true);
insert into tipo_unidad values(3,'Camioneta abierta','Camioneta para carga',false);
insert into tipo_unidad values(4,'Deportivo','Auto deportivo 4 asientos',true);
insert into tipo_unidad values(5,'Combi','Auto familiar 12 asientos',true);

INSERT INTO cliente VALUES('72120524','Patricio Estrella', 'Las piñas 123',1,true);
INSERT INTO cliente VALUES('72120523','Bod Esponja', 'Las piñas 124',2,true);

INSERT INTO unidad VALUES(1,'123rg6', 1,'Lamborguini',1971,'azul',3,true,3,'72120524');
INSERT INTO unidad VALUES(2,'123KL5', 3,'Retro',1972,'blanco',4,true,4,'72120523');

/*
select * from marca;
select * from ciudad;
select * from tipo_unidad;
SELECT* FROM cliente;
select * from unidad;

SELECT COALESCE(MAX(codigounidad),0)+1 AS codigo FROM unidad;

SELECT cliente.nombres, unidad.codigounidad, marca.nombre, tipo_unidad.nombretipo
FROM cliente INNER JOIN unidad ON unidad.dni=cliente.dni INNER JOIN tipo_unidad ON
tipo_unidad.codigotipo=unidad.codigotipo INNER JOIN marca on marca.codigomarca=unidad.codigomarca;

SELECT cliente.nombres, unidad.codigounidad, marca.nombre, tipo_unidad.nombretipo
FROM cliente INNER JOIN unidad ON unidad.dni=cliente.dni INNER JOIN tipo_unidad ON
tipo_unidad.codigotipo=unidad.codigotipo INNER JOIN marca on marca.codigomarca=unidad.codigomarca
*/

CREATE OR REPLACE FUNCTION filtro(opc varchar(10), tipo integer, mar integer)RETURNS TABLE(CodigoU integer, pla char(6),marca integer, model varchar(30), a integer, col varchar(20), numa integer, estado boolean, codt integer, d char(8))AS 
$$
DECLARE 
BEGIN
	IF(opc='Tipo unidad')THEN 
		RETURN QUERY 
		SELECT unidad.* FROM unidad WHERE codigotipo=tipo;
	END IF;

	IF(opc='Todos')THEN 
		RETURN QUERY 
		SELECT * FROM unidad;
	END IF;

	IF(opc='Marca')THEN
		RETURN QUERY 
		SELECT unidad.* FROM unidad WHERE codigomarca=mar;
	END IF;

END;
$$language 'plpgsql';



CREATE OR REPLACE FUNCTION filtrarClient(cli char(8)) RETURNS TABLE (Documento char(8), Nombre varchar(80), auto varchar(30), cod integer, plac char(6), model varchar(30))AS 
$$
DECLARE

BEGIN
	if(cli is null)then 
		RETURN QUERY
		SELECT cliente.dni, cliente.nombres, ciudad.nombre, unidad.codigounidad,unidad.placa, unidad.modelo FROM cliente 
		INNER JOIN ciudad ON ciudad.codigociudad=cliente.codigociudad
		INNER JOIN unidad ON unidad.dni=cliente.dni ;
	else
		RETURN QUERY
		SELECT cliente.dni, cliente.nombres, ciudad.nombre, unidad.codigounidad,unidad.placa, unidad.modelo FROM cliente 
		INNER JOIN ciudad ON ciudad.codigociudad=cliente.codigociudad
		INNER JOIN unidad ON unidad.dni=cliente.dni 
		WHERE cliente.dni=cli;
	end if ;

END;
$$language'plpgsql'

