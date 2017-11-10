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
    private String codigo;
    private String nombre;

    public Dependencia() {
    }

    public Dependencia(int dependenciaId, String codigo, String nombre) {
        this.dependenciaId = dependenciaId;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getDependenciaId() {
        return dependenciaId;
    }

    public void setDependenciaId(int dependenciaId) {
        this.dependenciaId = dependenciaId;
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
        return "Dependencia{" + "dependenciaId=" + dependenciaId + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }

}
