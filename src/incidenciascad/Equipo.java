/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

public class Equipo {

    private int equipoId;
    private String numeroEtiquetaConsejeria;
    private TipoEquipo tipoEquipo;

    public Equipo() {

    }

    public Equipo(int equipoId, String numeroEtiquetaConsejeria, TipoEquipo tipoEquipo) {
        this.equipoId = equipoId;
        this.numeroEtiquetaConsejeria = numeroEtiquetaConsejeria;
        this.tipoEquipo = tipoEquipo;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public String getNumeroEtiquetaConsejeria() {
        return numeroEtiquetaConsejeria;
    }

    public void setNumeroEtiquetaConsejeria(String numeroEtiquetaConsejeria) {
        this.numeroEtiquetaConsejeria = numeroEtiquetaConsejeria;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    @Override
    public String toString() {
        return "Equipo{" + "equipoId=" + equipoId + ", numeroEtiquetaConsejeria=" + numeroEtiquetaConsejeria + ", tipoEquipo=" + tipoEquipo + '}';
    }

}
