/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.UsuarioDAO;
import entidades.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author santiagoat
 */
@Named(value = "cambiarContrasenia")
@ViewScoped
public class cambiarContrasenia implements Serializable{

    @EJB
    private UsuarioDAO servicio;
    private String contrasenia_anterior;
    private String contrasenia_nueva;
    private String contrasenia_confirmar;
    private String nombre_usuario;
    private String tipo_usuario;
    private String contrasenia_traer;
    private int id_usuario;
    private boolean contrasenia_coincide;
    private boolean contrasenia_iguales;
    private boolean isExecuted;
    public cambiarContrasenia() {
    }
    @PostConstruct
    public void iniciar(){
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        setTipo_usuario(usu.getTipoUsuario());
        setNombre_usuario(usu.getNombreUsuario());
        setContrasenia_traer(usu.getContraseniaUsuario());
        setId_usuario((int) usu.getIdUsuario());
        contrasenia_anterior="";
        contrasenia_nueva="";
        contrasenia_confirmar="";
        contrasenia_coincide=false;
        isExecuted=false;
        contrasenia_iguales=false;
    }
    public void confirmarCambio(){
        isExecuted=true;
        if(contrasenia_traer.equals(contrasenia_anterior)){
            contrasenia_coincide=true;
        }
        if(contrasenia_confirmar.equals(contrasenia_nueva)){
            contrasenia_iguales=true;
        }
        if(contrasenia_coincide&&contrasenia_iguales){
            Usuario us=new Usuario(id_usuario,nombre_usuario, contrasenia_nueva, tipo_usuario);
            servicio.modificar(us);
        }
}
    public String cancelar(){
        return "/vistas/cambiarContrasenia.xhtml";
    }

    /**
     * @return the contrasenia_anterior
     */
    public String getContrasenia_anterior() {
        return contrasenia_anterior;
    }

    /**
     * @param contrasenia_anterior the contrasenia_anterior to set
     */
    public void setContrasenia_anterior(String contrasenia_anterior) {
        this.contrasenia_anterior = contrasenia_anterior;
    }

    /**
     * @return the contrasenia_nueva
     */
    public String getContrasenia_nueva() {
        return contrasenia_nueva;
    }

    /**
     * @param contrasenia_nueva the contrasenia_nueva to set
     */
    public void setContrasenia_nueva(String contrasenia_nueva) {
        this.contrasenia_nueva = contrasenia_nueva;
    }

    /**
     * @return the nombre_usuario
     */
    public String getNombre_usuario() {
        return nombre_usuario;
    }

    /**
     * @param nombre_usuario the nombre_usuario to set
     */
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    /**
     * @return the tipo_usuario
     */
    public String getTipo_usuario() {
        return tipo_usuario;
    }

    /**
     * @param tipo_usuario the tipo_usuario to set
     */
    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    /**
     * @return the contrasenia_traer
     */
    public String getContrasenia_traer() {
        return contrasenia_traer;
    }

    /**
     * @param contrasenia_traer the contrasenia_traer to set
     */
    public void setContrasenia_traer(String contrasenia_traer) {
        this.contrasenia_traer = contrasenia_traer;
    }

    /**
     * @return the contrasenia_coincide
     */
    public boolean isContrasenia_coincide() {
        return contrasenia_coincide;
    }

    /**
     * @param contrasenia_coincide the contrasenia_coincide to set
     */
    public void setContrasenia_coincide(boolean contrasenia_coincide) {
        this.contrasenia_coincide = contrasenia_coincide;
    }

    /**
     * @return the isExecuted
     */
    public boolean isIsExecuted() {
        return isExecuted;
    }

    /**
     * @param isExecuted the isExecuted to set
     */
    public void setIsExecuted(boolean isExecuted) {
        this.isExecuted = isExecuted;
    }

    /**
     * @return the servicio
     */
    public UsuarioDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(UsuarioDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the contrasenia_iguales
     */
    public boolean isContrasenia_iguales() {
        return contrasenia_iguales;
    }

    /**
     * @param contrasenia_iguales the contrasenia_iguales to set
     */
    public void setContrasenia_iguales(boolean contrasenia_iguales) {
        this.contrasenia_iguales = contrasenia_iguales;
    }

    /**
     * @return the id_usuario
     */
    public int getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario the id_usuario to set
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     * @return the contrasenia_confirmar
     */
    public String getContrasenia_confirmar() {
        return contrasenia_confirmar;
    }

    /**
     * @param contrasenia_confirmar the contrasenia_confirmar to set
     */
    public void setContrasenia_confirmar(String contrasenia_confirmar) {
        this.contrasenia_confirmar = contrasenia_confirmar;
    }
}
