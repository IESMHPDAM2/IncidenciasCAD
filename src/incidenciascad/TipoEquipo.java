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
    private String nombre;

    public TipoEquipo() {
    }

    public TipoEquipo(int tipòEquipoId, String nombre) {
        this.tipòEquipoId = tipòEquipoId;
        this.nombre = nombre;
    }

    public int getTipòEquipoId() {
        return tipòEquipoId;
    }

    public void setTipòEquipoId(int tipòEquipoId) {
        this.tipòEquipoId = tipòEquipoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
