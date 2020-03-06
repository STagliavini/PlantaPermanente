
package backbeans;

import ejb.OrganismoDAO;
import ejb.UsuarioDAO;
import entidades.Organismo;
import entidades.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author mayra
 */
@Named(value = "agregarOrganismo")
@RequestScoped
public class agregarOrganismo {

    @EJB
    private OrganismoDAO servicio;
    private Organismo org;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    @EJB
    private UsuarioDAO servicio2;
    private Usuario usuario;
    public agregarOrganismo() {
    }
    
    @PostConstruct
    public void Iniciar() {
        usuario=new Usuario();
        this.org = new Organismo();
        isExcecuted = false;
        exito=false;
        existe=false;
    }

    public Organismo getOrg() {
        return org;
    }

    public void setOrg(Organismo org) {
        this.org = org;
    }

    public String devolverMensaje() {
        String mensaje = "";
        if (org.getEstadoOrganismo() == false) {
            this.org.setEstadoOrganismo(true);
            this.servicio.modificar(org);
            mensaje = "El registro existia dado de baja. Estado modificado a activo, si desea modificar los campos dirijase a la pagina de Modificar";
        } else {
            mensaje = "El organismo ya existe";
        }
        return mensaje;
    }

    public void guardarOrganismo() {
        List<Organismo> orga = this.servicio.buscarPorCodigo(this.org.getCodigoOrganismo());
        if (orga.size() == 0) {
            org.setEstadoOrganismo(true);
            this.servicio.agregar(org);
            this.isExcecuted = true;
            this.setExito(true);
            getUsuario().setNombreUsuario(Long.toString(org.getCodigoOrganismo()));
            getUsuario().setContraseniaUsuario("admin");
            getUsuario().setTipoUsuario("AOrganismo");
            servicio2.agregar(getUsuario());
        } else {
            org = orga.get(0);
            existe=true;
            this.isExcecuted = true;
        }
    }

    public String cancelar() {
        this.isExcecuted = false;
        this.exito=false;
        limpiar();
        return "/vistas/agregarOrganismo.xhtml";
    }

    private void limpiar() {
        org.setDireccionOrganismo("");
        org.setCodigoOrganismo(0);        
        org.setEstadoOrganismo(true);
        org.setMailOrganismo("");        
        org.setNombreOrganismo("");        
        org.setTelefonoOrganismo("");
    }
    
    
    /**
     * @return the isExcecuted
     */
    public boolean isIsExcecuted() {
        return isExcecuted;
    }

    /**
     * @return the exito
     */
    public boolean isExito() {
        return exito;
    }

    /**
     * @param exito the exito to set
     */
    public void setExito(boolean exito) {
        this.exito = exito;
    }

    /**
     * @return the existe
     */
    public boolean isExiste() {
        return existe;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
