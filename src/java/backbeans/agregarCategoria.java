
package backbeans;

import ejb.CategoriaDAO;
import entidades.Categoria;
import java.math.BigDecimal;
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
@Named(value = "agregarCategoria")
@RequestScoped
public class agregarCategoria {

    @EJB
    private CategoriaDAO servicio;
    private Categoria cat;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    
    public agregarCategoria() {
    }
    
    @PostConstruct
    public void Iniciar() {
        this.cat = new Categoria();
        isExcecuted = false;
        exito=false;
        existe=false;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    public String devolverMensaje() {
        String mensaje = "";
        if (cat.getEstadoCategoria() == false) {
            this.cat.setEstadoCategoria(true);
            this.servicio.modificar(cat);
            mensaje = "El registro existia dado de baja. Estado modificado a activo, si desea modificar los campos dirijase a la pagina de Modificar";
        } else {
            mensaje = "La categoria ya existe";
        }
        return mensaje;
    }

    public void guardarCategoria() {
        List<Categoria> cate = this.servicio.buscarPorCodigo(this.cat.getCodigoCategoria());
        if (cate.size()==0) {
            cat.setEstadoCategoria(true);
            this.servicio.agregar(cat);
            this.isExcecuted = true;
            this.setExito(true);
        } else {
            cat = cate.get(0);
            existe=true;
            this.isExcecuted = true;
        }
    }

    public String cancelar() {
        this.isExcecuted = false;
        this.exito=false;
        limpiar();
        return "/vistas/agregarCategoria.xhtml";
    }

    private void limpiar() {
        cat.setCodigoCategoria(0);
        cat.setSueldoCategoria(new BigDecimal(0));
        cat.setEstadoCategoria(true);
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

}
