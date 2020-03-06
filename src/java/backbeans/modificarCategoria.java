
package backbeans;

import ejb.CategoriaDAO;
import entidades.CargoEmpleado;
import entidades.Categoria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author mayra
 */
@Named(value = "modificarCategoria")
@ViewScoped
public class modificarCategoria implements Serializable{

    @EJB
    private CategoriaDAO servicio;
    private Categoria cat;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;
    public modificarCategoria() {
    }
    
    @PostConstruct
    public void Iniciar() {
        existece=false;
        this.cat = new Categoria();
        setIsExcecuted(false);
        setExist(false);
        setExito(false);
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }
    
    public String devolverMensaje(){
        String mensaje="";
        if(cat.getEstadoCategoria() ==false){
            mensaje="El registro existia dado de baja. Si lo desea, puede modificar los campos y darlo de alta nuevamente";
        }
        else{
            mensaje="Listo para modificar";
        }
        return mensaje;
    }
    
    public void verificarCategoria() {
        List<Categoria> cate = this.servicio.buscarPorCodigo(this.cat.getCodigoCategoria());
        //List<Categoria> cate = this.getServicio().buscarporCodigo(this.cat.getCodigoCategoria());
        if (cate.size() != 0) {
            cat = cate.get(0);
            this.setIsExcecuted(true);
            this.setExist(true);
        } else {
            this.setIsExcecuted(true);
            setExist(false);
        }
    }

    public void confirmarCambio(){
        List<CargoEmpleado>ced=servicio.existeCargoEmpleado(cat.getIdCategoria());
        if(ced.isEmpty()){
            this.cat.setEstadoCategoria(true);
        this.getServicio().modificar(cat);
        this.setExito(true);
        this.setExist(false);
        this.setIsExcecuted(false);
        }
        else{
            existece=true;
        }
    }
    
    public void cancelar(){
        this.setExist(false);
        this.setIsExcecuted(false);
        this.exito=false;
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
     * @param isExcecuted the isExcecuted to set
     */
    public void setIsExcecuted(boolean isExcecuted) {
        this.isExcecuted = isExcecuted;
    }

    /**
     * @param exist the exist to set
     */
    public void setExist(boolean exist) {
        this.exist = exist;
    }

    /**
     * @param exito the exito to set
     */
    public void setExito(boolean exito) {
        this.exito = exito;
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
