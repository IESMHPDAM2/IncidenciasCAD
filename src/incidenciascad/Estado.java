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
    
    private Integer estadoId;
    private String codigo;
    private String nombre;

    public Estado() {
    }

    public Estado(Integer estadoId, String codigo, String nombre) {
        this.estadoId = estadoId;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
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

    @Override
    public String toString() {
        return "Estado{" + "estadoId=" + estadoId + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
