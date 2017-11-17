/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

import java.util.Date;

/**
 *
 * @author usuario
 */
public class Incidencia {
    private Integer incidenciaId;
    private String posicionEquipoDependencia;
    private String descripcion;
    private String comentarioAdministrador;
    private Date fechaEstadoActual;
    private Usuario usuario;
    private Equipo equipo;
    private Dependencia dependencia;
    private Estado estado;

    public Incidencia() {
    }

    public Incidencia(Integer incidenciaId, String posicionEquipoDependencia, String descripcion, String comentarioAdministrador, Date fechaEstadoActual, Usuario usuario, Equipo equipo, Dependencia dependencia, Estado estado) {
        this.incidenciaId = incidenciaId;
        this.posicionEquipoDependencia = posicionEquipoDependencia;
        this.descripcion = descripcion;
        this.comentarioAdministrador = comentarioAdministrador;
        this.fechaEstadoActual = fechaEstadoActual;
        this.usuario = usuario;
        this.equipo = equipo;
        this.dependencia = dependencia;
        this.estado = estado;
    }

    public Integer getIncidenciaId() {
        return incidenciaId;
    }

    public void setIncidenciaId(Integer incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public String getPosicionEquipoDependencia() {
        return posicionEquipoDependencia;
    }

    public void setPosicionEquipoDependencia(String posicionEquipoDependencia) {
        this.posicionEquipoDependencia = posicionEquipoDependencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentarioAdministrador() {
        return comentarioAdministrador;
    }

    public void setComentarioAdministrador(String comentarioAdministrador) {
        this.comentarioAdministrador = comentarioAdministrador;
    }

    public Date getFechaEstadoActual() {
        return fechaEstadoActual;
    }

    public void setFechaEstadoActual(Date fechaEstadoActual) {
        this.fechaEstadoActual = fechaEstadoActual;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Incidencia{" + "incidenciaId=" + incidenciaId + ", posicionEquipoDependencia=" + posicionEquipoDependencia + ", descripcion=" + descripcion + ", comentarioAdministrador=" + comentarioAdministrador + ", fechaEstadoActual=" + fechaEstadoActual + ", usuario=" + usuario + ", equipo=" + equipo + ", dependencia=" + dependencia + ", estado=" + estado + '}';
    }

}
