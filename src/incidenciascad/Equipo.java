/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

public class Equipo {

    private Integer equipoId;
    private String numeroEtiquetaConsejeria;
    private TipoEquipo tipoEquipo;

    public Equipo() {

    }

    public Equipo(Integer equipoId, String numeroEtiquetaConsejeria, TipoEquipo tipoEquipo) {
        this.equipoId = equipoId;
        this.numeroEtiquetaConsejeria = numeroEtiquetaConsejeria;
        this.tipoEquipo = tipoEquipo;
    }

    public Integer getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Integer equipoId) {
        this.equipoId = equipoId;
    }

    public String getNumeroEtiquetaConsejeria() {
        if (numeroEtiquetaConsejeria != null) {
            if (numeroEtiquetaConsejeria.equals("")) return null;
        }
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
