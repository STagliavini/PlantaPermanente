
package backbeans;

import ejb.OrganismoDAO;
import entidades.Organismo;
import entidades.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mayra
 */
@Named(value = "listarOrganismo")
@RequestScoped
public class listarOrganismo {

    @EJB
    private OrganismoDAO servicio;
    private List<Organismo> organismos;
    
    public listarOrganismo() {
    }
    @PostConstruct
    public void iniciar(){
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        if(usu.getTipoUsuario().equals("AOrganismo")){
            setOrganismos(getServicio().buscarPorCodigo(Integer.parseInt(usu.getNombreUsuario())));
        }
        else{
            setOrganismos(getServicio().listarTodos());
        }
    }
    public OrganismoDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(OrganismoDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the Organismos
     */
    public List<Organismo> getOrganismos() {
        return organismos;
    }

    public void setOrganismos(List<Organismo> organismos) {
        this.organismos = organismos;
    }
    
    
}
