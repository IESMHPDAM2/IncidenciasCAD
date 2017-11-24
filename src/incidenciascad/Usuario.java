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
public class Usuario {
    private Integer usuarioId;
    private String cuenta;
    private String nombre;
    private String apellido;
    private String departamento;

    public Usuario() {
    }

    public Usuario(Integer usuarioId, String cuenta, String nombre, String apellido, String departamento) {
        this.usuarioId = usuarioId;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCuenta() {
        if (cuenta != null) {
            if (cuenta.equals("")) return null;
        }
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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

    public String getApellido() {
        if (apellido != null) {
            if (apellido.equals("")) return null;
        }
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDepartamento() {
        if (departamento != null) {
            if (departamento.equals("")) return null;
        }
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuarioId=" + usuarioId + ", cuenta=" + cuenta + ", nombre=" + nombre + ", apellido=" + apellido + ", departamento=" + departamento + '}';
    }

}
