/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author usuario
 */
public class IncidenciaPlus {
    private Integer incidenciaId;
    private String posicionEquipoDependencia;
    private String descripcion;
    private String comentarioAdministrador;
    private Date fechaEstadoActual;
    private Usuario usuario;
    private Equipo equipo;
    private Dependencia dependencia;
    private Estado estado;
    private Estado estadoInicial;
    private Date fechaEstadoInicial;
    private Estado estadoFinal;
    private Date fechaEstadoFinal;
    private ArrayList<Historial> historiales;
            
    public IncidenciaPlus() {
    }

    public IncidenciaPlus(Integer incidenciaId, String posicionEquipoDependencia, String descripcion, String comentarioAdministrador, Date fechaEstadoActual, Usuario usuario, Equipo equipo, Dependencia dependencia, Estado estado, Estado estadoInicial, Date fechaEstadoInicial, Estado estadoFinal, Date fechaEstadoFinal, ArrayList<Historial> historiales) {
        this.incidenciaId = incidenciaId;
        this.posicionEquipoDependencia = posicionEquipoDependencia;
        this.descripcion = descripcion;
        this.comentarioAdministrador = comentarioAdministrador;
        this.fechaEstadoActual = fechaEstadoActual;
        this.usuario = usuario;
        this.equipo = equipo;
        this.dependencia = dependencia;
        this.estado = estado;
        this.estadoInicial = estadoInicial;
        this.fechaEstadoInicial = fechaEstadoInicial;
        this.estadoFinal = estadoFinal;
        this.fechaEstadoFinal = fechaEstadoFinal;
        this.historiales = historiales;
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

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Date getFechaEstadoInicial() {
        return fechaEstadoInicial;
    }

    public void setFechaEstadoInicial(Date fechaEstadoInicial) {
        this.fechaEstadoInicial = fechaEstadoInicial;
    }

    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(Estado estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public Date getFechaEstadoFinal() {
        return fechaEstadoFinal;
    }

    public void setFechaEstadoFinal(Date fechaEstadoFinal) {
        this.fechaEstadoFinal = fechaEstadoFinal;
    }

    public ArrayList<Historial> getHistoriales() {
        return historiales;
    }

    public void setHistoriales(ArrayList<Historial> historiales) {
        this.historiales = historiales;
    }

    @Override
    public String toString() {
        return "IncidenciaPlus{" + "incidenciaId=" + incidenciaId + ", posicionEquipoDependencia=" + posicionEquipoDependencia + ", descripcion=" + descripcion + ", comentarioAdministrador=" + comentarioAdministrador + ", fechaEstadoActual=" + fechaEstadoActual + ", usuario=" + usuario + ", equipo=" + equipo + ", dependencia=" + dependencia + ", estado=" + estado + ", estadoInicial=" + estadoInicial + ", fechaEstadoInicial=" + fechaEstadoInicial + ", estadoFinal=" + estadoFinal + ", fechaEstadoFinal=" + fechaEstadoFinal + ", historiales=" + historiales + '}';
    }

}
