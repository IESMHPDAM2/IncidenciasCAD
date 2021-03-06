/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

import java.util.Date;

/**
 *
 * @author DAM202
 */
public class Historial 
{
    private Integer historialId;
    private Date fecha;
    private Incidencia incidencia;
    private Estado estado;

    public Historial() {
    }

    public Historial(Integer historialId, Date fecha, Incidencia incidencia, Estado estado) {
        this.historialId = historialId;
        this.fecha = fecha;
        this.incidencia = incidencia;
        this.estado = estado;
    }

    public Integer getHistorialId() {
        return historialId;
    }

    public void setHistorialId(Integer historialId) {
        this.historialId = historialId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Historial{" + "historialId=" + historialId + ", fecha=" + fecha + ", incidencia=" + incidencia + ", estado=" + estado + '}';
    }
}
