/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

/**
 *
 * @author ifontecha
 */
public class TipoEquipo {
    private Integer tipoEquipoId;
    private String codigo;
    private String nombre;

    public TipoEquipo() {
    }

    public TipoEquipo(Integer tipoEquipoId, String codigo, String nombre) {
        this.tipoEquipoId = tipoEquipoId;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getTipoEquipoId() {
        return tipoEquipoId;
    }

    public void setTipoEquipoId(Integer tipoEquipoId) {
        this.tipoEquipoId = tipoEquipoId;
    }

    public String getCodigo() {
        if (codigo != null) {
            if (codigo.equals("")) return null;
        }
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        if (nombre != null) {
            if (nombre.equals("")) return null;
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoEquipo{" + "tipoEquipoId=" + tipoEquipoId + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }

}
