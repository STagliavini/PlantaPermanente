/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.UsuarioDAO;
import entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author santiagoat
 */
@Named(value = "loginManager")
@SessionScoped
public class loginManager implements Serializable {

    public static final String HOME_PAGE_REDIRECT
            = "/faces/vistas/listarEmpleados.xhtml?faces-redirect=true";
    public static final String LOGIN_PAGE_REDIRECT
            = "/faces/index.xhtml";
    @EJB
    private UsuarioDAO servicio;
    private String nombreUsuario;
    private String contraseniaUsuario;
    private Usuario usuarioActual;
    private boolean logueado;

    public String login() {
        List<Usuario> us=servicio.traerUsuario(nombreUsuario, contraseniaUsuario);
        if(us.size()>0){
            usuarioActual=us.get(0);
        }
        if (getUsuarioActual() != null) {
            setLogueado(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioActual",usuarioActual );
            return HOME_PAGE_REDIRECT;
        }
        return LOGIN_PAGE_REDIRECT;
    }

    public String logout() {
        if(logueado){
            setLogueado(false);
            FacesContext.getCurrentInstance().getExternalContext()
                    .invalidateSession();
        }
        return LOGIN_PAGE_REDIRECT+"?faces-redirect=true";
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
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the contraseniaUsuario
     */
    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    /**
     * @param contraseniaUsuario the contraseniaUsuario to set
     */
    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }

    /**
     * @return the usuarioActual
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param usuarioActual the usuarioActual to set
     */
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * @return the logueado
     */
    public boolean isLogueado() {
        return logueado;
    }

    /**
     * @param logueado the logueado to set
     */
    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }
}
