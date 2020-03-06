package backbeans;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import ejb.CategoriaDAO;
import entidades.CargoEmpleado;
import entidades.Categoria;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;

/**
 *
 * @author mayra
 */
@Named(value = "eliminarCategoria")
@ViewScoped
public class eliminarCategoria implements Serializable {

    @EJB
    private CategoriaDAO servicio;
    private Categoria cat;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;

    public eliminarCategoria() {
    }

    @PostConstruct
    public void Iniciar() {
        setExistece(false);
        this.cat = new Categoria();
        isExcecuted = false;
        exist = false;
        exito = false;
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
            mensaje = "La categoria ya esta dada de baja";
            exist = false;
        } else {
            mensaje = "Confirme si desea dar de baja a la categoria";
        }
        return mensaje;
    }

    public void verificarCategoria() {
        List<Categoria> cate = this.servicio.buscarPorCodigo(this.cat.getCodigoCategoria());
        if (cate.size() != 0) {
            cat = cate.get(0);
            this.isExcecuted = true;
            this.exist = true;
        } else {
            this.isExcecuted = true;
            exist = false;
        }
    }

    public void confirmarBaja() {
        List<CargoEmpleado> ced = servicio.existeCargoEmpleado(cat.getIdCategoria());
        if (ced.isEmpty()) {
            this.cat.setEstadoCategoria(false);
            this.servicio.modificar(cat);
            this.exito = true;
            this.exist = false;
            this.isExcecuted = false;
        } else {
            existece = true;
        }
    }

    public void cancelar() {
        this.exist = false;
        this.isExcecuted = false;
        limpiar();
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
     * @return the exist
     */
    public boolean isExist() {
        return exist;
    }

    /**
     * @return the exito
     */
    public boolean isExito() {
        return exito;
    }

    /**
     * @return the existece
     */
    public boolean isExistece() {
        return existece;
    }

    /**
     * @param existece the existece to set
     */
    public void setExistece(boolean existece) {
        this.existece = existece;
    }

}
