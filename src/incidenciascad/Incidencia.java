/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

/**
 *
 * @author usuario
 */
public class Incidencia {
    private Integer incidenciaId;
    private String posicionEquipoDependencia;
    private String descripcion;
    private String comentarioAdministrador;
    private Usuario usuario;
    private Equipo equipo;
    private Dependencia dependencia;

    public Incidencia() {
    }

    public Incidencia(Integer incidenciaId, String posicionEquipoDependencia, String descripcion, String comentarioAdministrador, Usuario usuario, Equipo equipo, Dependencia dependencia) {
        this.incidenciaId = incidenciaId;
        this.posicionEquipoDependencia = posicionEquipoDependencia;
        this.descripcion = descripcion;
        this.comentarioAdministrador = comentarioAdministrador;
        this.usuario = usuario;
        this.equipo = equipo;
        this.dependencia = dependencia;
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

    @Override
    public String toString() {
        return "Incidencia{" + "incidenciaId=" + incidenciaId + ", posicionEquipoDependencia=" + posicionEquipoDependencia + ", descripcion=" + descripcion + ", comentarioAdministrador=" + comentarioAdministrador + ", usuario=" + usuario + ", equipo=" + equipo + ", dependencia=" + dependencia + '}';
    }

}
