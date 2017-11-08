/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

/**
 *
 * @author DAM220
 */
public class Estado {
    
    private int estadoId;
    private String codigo;
    private String nombre;
    private int orden;

    public Estado() {
    }

    public Estado(int estadoId, String codigo, String nombre, int orden) {
        this.estadoId = estadoId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.orden = orden;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
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

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "Estado{" + "estadoId=" + estadoId + ", codigo=" + codigo + ", nombre=" + nombre + ", orden=" + orden + '}';
    }
    
}
