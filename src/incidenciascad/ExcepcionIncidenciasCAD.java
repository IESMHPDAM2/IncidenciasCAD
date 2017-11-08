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
public class ExcepcionIncidenciasCAD extends Exception {
    private int codigoErrorSistema;
    private String mensajeErrorSistema;
    private String mensajeErrorUsuario;
    private String sentenciaSQL;

    public ExcepcionIncidenciasCAD() {
    }

    public ExcepcionIncidenciasCAD(int codigoErrorSistema, String mensajeErrorSistema, String mensajeErrorUsuario, String sentenciaSQL) {
        this.codigoErrorSistema = codigoErrorSistema;
        this.mensajeErrorSistema = mensajeErrorSistema;
        this.mensajeErrorUsuario = mensajeErrorUsuario;
        this.sentenciaSQL = sentenciaSQL;
    }

    public int getCodigoErrorSistema() {
        return codigoErrorSistema;
    }

    public void setCodigoErrorSistema(int codigoErrorSistema) {
        this.codigoErrorSistema = codigoErrorSistema;
    }

    public String getMensajeErrorSistema() {
        return mensajeErrorSistema;
    }

    public void setMensajeErrorSistema(String mensajeErrorSistema) {
        this.mensajeErrorSistema = mensajeErrorSistema;
    }

    public String getMensajeErrorUsuario() {
        return mensajeErrorUsuario;
    }

    public void setMensajeErrorUsuario(String mensajeErrorUsuario) {
        this.mensajeErrorUsuario = mensajeErrorUsuario;
    }

    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }

    @Override
    public String toString() {
        return "ExcepcionIncidenciasCAD{" + "codigoErrorSistema=" + codigoErrorSistema + ", mensajeErrorSistema=" + mensajeErrorSistema + ", mensajeErrorUsuario=" + mensajeErrorUsuario + ", sentenciaSQL=" + sentenciaSQL + '}';
    }
}
