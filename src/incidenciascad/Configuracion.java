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
public class Configuracion {
    private String empresaConsejeriaNombre;
    private String empresaConsejeriaTelefono;
    private String empresaConsejeriaEmail;
    private String iesNombre;
    private String iesCif;
    private String iesCodigoCentro;
    private String iesPersonaContactoNombre;
    private String iesPersonaContactoApellido1;
    private String iesPersonaContactoApellido2;
    private String iesEmail;
    private Estado estadoInicial;
    private Estado estadoFinal;
    private String ldapUrl;
    private String ldapDominio;
    private String ldapDn;
    private String ldapAtributoCuenta;
    private String ldapAtributoNombre;
    private String ldapAtributoApellido;
    private String ldapAtributoDepartamento;
    private String ldapAtributoPerfil;
    

    public Configuracion() {
        
    }

    public Configuracion(String empresaConsejeriaNombre, String empresaConsejeriaTelefono, String empresaConsejeriaEmail, String iesNombre, String iesCif, 
            String iesCodigoCentro, String iesPersonaContactoNombre, String iesPersonaContactoApellido1, String iesPersonaContactoApellido2, String iesEmail, 
            Estado estadoInicial, Estado estadoFinal, String ldapUrl, String ldapDominio, String ldapDn, String ldapAtributoCuenta, String ldapAtributoNombre, 
            String ldapAtributoApellido, String ldapAtributoDepartamento, String ldapAtributoPerfil) {
        this.empresaConsejeriaNombre = empresaConsejeriaNombre;
        this.empresaConsejeriaTelefono = empresaConsejeriaTelefono;
        this.empresaConsejeriaEmail = empresaConsejeriaEmail;
        this.iesNombre = iesNombre;
        this.iesCif = iesCif;
        this.iesCodigoCentro = iesCodigoCentro;
        this.iesPersonaContactoNombre = iesPersonaContactoNombre;
        this.iesPersonaContactoApellido1 = iesPersonaContactoApellido1;
        this.iesPersonaContactoApellido2 = iesPersonaContactoApellido2;
        this.iesEmail = iesEmail;
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.ldapUrl = ldapUrl;
        this.ldapDominio = ldapDominio;
        this.ldapDn = ldapDn;
        this.ldapAtributoCuenta = ldapAtributoCuenta;
        this.ldapAtributoNombre = ldapAtributoNombre;
        this.ldapAtributoApellido = ldapAtributoApellido;
        this.ldapAtributoDepartamento = ldapAtributoDepartamento;
        this.ldapAtributoPerfil = ldapAtributoPerfil;
    }

    public String getEmpresaConsejeriaNombre() {
        return empresaConsejeriaNombre;
    }

    public void setEmpresaConsejeriaNombre(String empresaConsejeriaNombre) {
        this.empresaConsejeriaNombre = empresaConsejeriaNombre;
    }

    public String getEmpresaConsejeriaTelefono() {
        return empresaConsejeriaTelefono;
    }

    public void setEmpresaConsejeriaTelefono(String empresaConsejeriaTelefono) {
        this.empresaConsejeriaTelefono = empresaConsejeriaTelefono;
    }

    public String getEmpresaConsejeriaEmail() {
        return empresaConsejeriaEmail;
    }

    public void setEmpresaConsejeriaEmail(String empresaConsejeriaEmail) {
        this.empresaConsejeriaEmail = empresaConsejeriaEmail;
    }

    public String getIesNombre() {
        return iesNombre;
    }

    public void setIesNombre(String iesNombre) {
        this.iesNombre = iesNombre;
    }

    public String getIesCif() {
        return iesCif;
    }

    public void setIesCif(String iesCif) {
        this.iesCif = iesCif;
    }

    public String getIesCodigoCentro() {
        return iesCodigoCentro;
    }

    public void setIesCodigoCentro(String iesCodigoCentro) {
        this.iesCodigoCentro = iesCodigoCentro;
    }

    public String getIesPersonaContactoNombre() {
        return iesPersonaContactoNombre;
    }

    public void setIesPersonaContactoNombre(String iesPersonaContactoNombre) {
        this.iesPersonaContactoNombre = iesPersonaContactoNombre;
    }

    public String getIesPersonaContactoApellido1() {
        return iesPersonaContactoApellido1;
    }

    public void setIesPersonaContactoApellido1(String iesPersonaContactoApellido1) {
        this.iesPersonaContactoApellido1 = iesPersonaContactoApellido1;
    }

    public String getIesPersonaContactoApellido2() {
        return iesPersonaContactoApellido2;
    }

    public void setIesPersonaContactoApellido2(String iesPersonaContactoApellido2) {
        this.iesPersonaContactoApellido2 = iesPersonaContactoApellido2;
    }

    public String getIesEmail() {
        return iesEmail;
    }

    public void setIesEmail(String iesEmail) {
        this.iesEmail = iesEmail;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(Estado estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public String getLdapUrl() {
        return ldapUrl;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public String getLdapDominio() {
        return ldapDominio;
    }

    public void setLdapDominio(String ldapDominio) {
        this.ldapDominio = ldapDominio;
    }

    public String getLdapDn() {
        return ldapDn;
    }

    public void setLdapDn(String ldapDn) {
        this.ldapDn = ldapDn;
    }

    public String getLdapAtributoCuenta() {
        return ldapAtributoCuenta;
    }

    public void setLdapAtributoCuenta(String ldapAtributoCuenta) {
        this.ldapAtributoCuenta = ldapAtributoCuenta;
    }

    public String getLdapAtributoNombre() {
        return ldapAtributoNombre;
    }

    public void setLdapAtributoNombre(String ldapAtributoNombre) {
        this.ldapAtributoNombre = ldapAtributoNombre;
    }

    public String getLdapAtributoApellido() {
        return ldapAtributoApellido;
    }

    public void setLdapAtributoApellido(String ldapAtributoApellido) {
        this.ldapAtributoApellido = ldapAtributoApellido;
    }

    public String getLdapAtributoDepartamento() {
        return ldapAtributoDepartamento;
    }

    public void setLdapAtributoDepartamento(String ldapAtributoDepartamento) {
        this.ldapAtributoDepartamento = ldapAtributoDepartamento;
    }

    public String getLdapAtributoPerfil() {
        return ldapAtributoPerfil;
    }

    public void setLdapAtributoPerfil(String ldapAtributoPerfil) {
        this.ldapAtributoPerfil = ldapAtributoPerfil;
    }

    @Override
    public String toString() {
        return "Configuracion{" + "empresaConsejeriaNombre=" + empresaConsejeriaNombre + ", empresaConsejeriaTelefono=" + empresaConsejeriaTelefono + ", empresaConsejeriaEmail=" + empresaConsejeriaEmail + ", iesNombre=" + iesNombre + ", iesCif=" + iesCif + ", iesCodigoCentro=" + iesCodigoCentro + ", iesPersonaContactoNombre=" + iesPersonaContactoNombre + ", iesPersonaContactoApellido1=" + iesPersonaContactoApellido1 + ", iesPersonaContactoApellido2=" + iesPersonaContactoApellido2 + ", iesEmail=" + iesEmail + ", estadoInicial=" + estadoInicial + ", estadoFinal=" + estadoFinal + ", ldapUrl=" + ldapUrl + ", ldapDominio=" + ldapDominio + ", ldapDn=" + ldapDn + ", ldapAtributoCuenta=" + ldapAtributoCuenta + ", ldapAtributoNombre=" + ldapAtributoNombre + ", ldapAtributoApellido=" + ldapAtributoApellido + ", ldapAtributoDepartamento=" + ldapAtributoDepartamento + ", ldapAtributoPerfil=" + ldapAtributoPerfil + '}';
    }
    
}
