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
public class Dependencia {
    private int dependenciaId;
    private String nombre;

    public Dependencia() {
    }

    public Dependencia(int dependenciaId, String nombre) {
        this.dependenciaId = dependenciaId;
        this.nombre = nombre;
    }

    public int getDependenciaId() {
        return dependenciaId;
    }

    public void setDependenciaId(int dependenciaId) {
        this.dependenciaId = dependenciaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
