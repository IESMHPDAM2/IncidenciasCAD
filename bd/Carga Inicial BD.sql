-- Tipo equipo

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("TO", "Torre");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("ILASER", "Impresora láser");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("MON", "Monitor");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("RA", "Ratón");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("TE", "Teclado");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("PRO", "Proyector");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("CRED", "Conector de red");

-- Dependencia

INSERT INTO dependencia(codigo, nombre) VALUES ("IF01", "Clase de informática 1");

INSERT INTO dependencia(codigo, nombre) VALUES ("IF02", "Clase de informática 2");

INSERT INTO dependencia(codigo, nombre) VALUES ("SG01", "Clase de administración 1");

INSERT INTO dependencia(codigo, nombre) VALUES ("SG02", "Clase de administración 2");

INSERT INTO dependencia(codigo, nombre) VALUES ("ED01", "Clase de edificación 1");

INSERT INTO dependencia(codigo, nombre) VALUES ("ED02", "Clase de edificación 2");

INSERT INTO dependencia(codigo, nombre) VALUES ("OB01", "Clase de obra civil 1");

-- Usuario

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("ffernandez", "Fernando", "Fernández", "Informática");

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("lruiz", "Luis", "Ruiz", "Informática");

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("joseh", "Jose", "Hernández", "Administración");

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("jluis", "Jose Luis", "Gomez", "Administración");

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("ifontecha", "Ignacio", "Fontecha", "Informática");

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("luish", "Luis", "Herrero", "Informática");

INSERT INTO usuario(cuenta, nombre, apellido, departamento) VALUES ("roberto", "Roberto", "Macho", "Edificación");

-- Equipo

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("1111", 7);

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("2222", 3);

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("3333", 2);

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("4444", 6);

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("5555", 5);

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("6666", 4);

INSERT INTO equipo(numero_etiqueta_consejeria, tipo_equipo_id) VALUES ("7777", 1);

-- Estado

INSERT INTO estado(codigo, nombre) VALUES ("RE", "00 - Recibida");

INSERT INTO estado(codigo, nombre) VALUES ("ET", "01 - En trámite");

INSERT INTO estado(codigo, nombre) VALUES ("PE", "02 - Pendiente de empresa colaboradoraa");

INSERT INTO estado(codigo, nombre) VALUES ("RT", "99 - Resuelta");

-- Incidencia

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("20", "Problema en el monitor", "Problema en cable VGA", CURDATE(), 1, 2, 4, 2);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("02", "Ordenador no enciende", "Problema en cable de alimentación", CURDATE(), 4, 1, 2, 3);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("06", "Proyector no funciona", "Cable VGA no está conectado a splitter", CURDATE(), 6, 4, 1, 1);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("15", "No funciona la impresora", null, CURDATE(), 5, 3, 6, 2);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("01", "No hay internet", "Problema en cable Ethernet", CURDATE(), 6, 1, 5, 3);
    
INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("16", "Problema en el monitor", "Mala conexión con cable VGA", CURDATE(), 2, 4, 3, 4);
    
INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("10", "Teclado no funciona", null, CURDATE(), 3, 6, 6, 1);
    
-- Historial

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES ("2017-10-10", 2, 1);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES ("2017-12-07", 5, 3);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES ("2016-09-15", 1, 2);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES ("2017-04-10", 6, 1);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES ("2017-02-22", 4, 4);
