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
public class Departamento {
    private int departamentoId;
    private String nombre;

    public Departamento() {
    }

    public Departamento(int departamentoId, String nombre) {
        this.departamentoId = departamentoId;
        this.nombre = nombre;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
