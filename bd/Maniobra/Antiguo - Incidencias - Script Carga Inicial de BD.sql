-- Tipo equipo

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("Torre", "Torre");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("ILaser", "Impresora láser");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("Monitor", "Monitor");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("Portátil", "Portátil");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("Smartphone", "Smartphone");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("Proyector", "Proyector");

INSERT INTO tipo_equipo(codigo, nombre) VALUES ("Switch", "Switch");

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

INSERT INTO estado(codigo, nombre) VALUES ("00-RE", "00 - Recibida");

INSERT INTO estado(codigo, nombre) VALUES ("01-ET", "01 - En trámite");

INSERT INTO estado(codigo, nombre) VALUES ("02-PE", "02 - Pendiente de empresa colaboradoraa");

INSERT INTO estado(codigo, nombre) VALUES ("99-RT", "99 - Resuelta");

-- Incidencia

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("20", "Problema en el monitor", "Problema en cable VGA", CURDATE()-1, CURDATE(), 1, 2, 4, 2);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("02", "Ordenador no enciende", "Problema en cable de alimentación", CURDATE()-2, CURDATE(), 4, 7, 2, 3);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("06", "Proyector no funciona", "Cable VGA no está conectado a splitter", CURDATE(), CURDATE(), 6, 4, 1, 1);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("15", "No funciona la impresora", null, CURDATE()-4, CURDATE()-2, 5, 3, 6, 2);

INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("01", "No hay internet", "Problema en cable Ethernet", CURDATE()-5, CURDATE()-2, 6, 1, 5, 3);
    
INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("16", "La imagen sale borrosa", "Mala conexión con cable VGA", CURDATE()-6, CURDATE()-3, 2, 4, 3, 4);
    
INSERT INTO incidencia(posicion_equipo_dependencia, descripcion, comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id,
	equipo_id, dependencia_id, estado_id) VALUES ("10", "Teclado no funciona", null, CURDATE(), CURDATE(), 3, 6, 6, 1);
    
-- Historial

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-2, 2, 1);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-5, 5, 1);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-1, 1, 1);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-6, 6, 1);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-5, 6, 2);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-4, 6, 3);

INSERT INTO historial(fecha, incidencia_id, estado_id) VALUES (CURDATE()-4, 4, 1);

INSERT INTO configuracion
        (empresa_consejeria_nombre, empresa_consejeria_telefono, empresa_consejeria_email,
        ies_nombre, ies_cif, ies_codigo_centro, ies_persona_contacto_nombre, ies_persona_contacto_apellido1, ies_persona_contacto_apellido2, ies_email,
        estado_inicial_incidencia, estado_final_incidencia, 
        ldap_url, ldap_dominio, ldap_dn, ldap_atributo_cuenta, ldap_atributo_nombre, ldap_atributo_apellido, ldap_atributo_departamento, ldap_atributo_perfil) 
    VALUES 
        ('Infortec', '942676765', 'asistencia@infortec.es',
	 'IES Miguel Herrero Pereda', 'Q123456789A', 'X232323', 'Pepe', 'Ruiz', 'Lara', 'pepe.ruizlara@educantabria.es',
         1, 4,
         'ldap://10.0.1.48', 'iesmhp.local', 'dc=iesmhp,dc=local', 'name', 'givenName', 'sn', 'department', 'title');
