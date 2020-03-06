
package backbeans;

import ejb.CategoriaDAO;
import entidades.Categoria;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author mayra
 */
@Named(value = "listarCategoria")
@RequestScoped
public class listarCategoria {

    @EJB
    private CategoriaDAO servicio;
    private List<Categoria> categorias;
    
    public listarCategoria() {
    }
    
    /**
     * @return the servicio
     */
    public CategoriaDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(CategoriaDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the categorias
     */
    public List<Categoria> getCategorias() {
        return servicio.listarTodos();
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    
}
