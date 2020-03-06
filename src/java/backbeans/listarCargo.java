
package backbeans;

import ejb.CargoDAO;
import entidades.Cargo;
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
@Named(value = "listarCargo")
@RequestScoped
public class listarCargo {

    @EJB
    private CargoDAO servicio;
    private List<Cargo> cargos;
    
    public listarCargo() {
    }
    
    public CargoDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(CargoDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the Organismos
     */
    public List<Cargo> getCargos() {
        return servicio.listarTodos();
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
    
    
}
