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
    private int incidenciaId;
    private String descripcion;
    private Usuario usuario;
    private Equipo equipo;
    private Dependencia dependencia;

    public Incidencia() {
    }

    public Incidencia(int incidenciaId, String descripcion, Usuario usuario, Equipo equipo, Dependencia dependencia) {
        this.incidenciaId = incidenciaId;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.equipo = equipo;
        this.dependencia = dependencia;
    }

    public int getIncidenciaId() {
        return incidenciaId;
    }

    public void setIncidenciaId(int incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        return "Incidencia{" + "incidenciaId=" + incidenciaId + ", descripcion=" + descripcion + ", usuario=" + usuario + ", equipo=" + equipo + ", dependencia=" + dependencia + '}';
    }


}
