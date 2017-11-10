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
    private int tipòEquipoId;
    private String codigo;
    private String nombre;

    public TipoEquipo() {
    }

    public TipoEquipo(int tipòEquipoId, String codigo, String nombre) {
        this.tipòEquipoId = tipòEquipoId;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getTipòEquipoId() {
        return tipòEquipoId;
    }

    public void setTipòEquipoId(int tipòEquipoId) {
        this.tipòEquipoId = tipòEquipoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoEquipo{" + "tip\u00f2EquipoId=" + tipòEquipoId + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }

}