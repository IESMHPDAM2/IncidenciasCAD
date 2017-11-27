delimiter //

create trigger INCIDENCIA_AFTER_UPDATE after update on INCIDENCIA
 for each row

begin

	if OLD.ESTADO_ID != NEW.ESTADO_ID THEN
		insert into HISTORIAL(FECHA,INCIDENCIA_ID,ESTADO_ID)

    		values(OLD.FECHA_ESTADO_ACTUAL,OLD.INCIDENCIA_ID,OLD.ESTADO_ID);

	END IF;
end//

