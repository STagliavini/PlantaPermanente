
package backbeans;

import ejb.OrganismoDAO;
import entidades.CargoEmpleado;
import entidades.Organismo;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author mayra
 */
@Named(value = "modificarOrganismo")
@ViewScoped
public class modificarOrganismo implements Serializable {

    @EJB
    private OrganismoDAO servicio;
    private Organismo org;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;
    public modificarOrganismo() {
    }
    
    @PostConstruct
    public void Iniciar() {
        existece=false;
        this.org = new Organismo();
        setIsExcecuted(false);
        setExist(false);
        setExito(false);
    }

    public Organismo getOrg() {
        return org;
    }

    public void setOrg(Organismo org) {
        this.org = org;
    }
    
    public String devolverMensaje(){
        String mensaje="";
        if(org.getEstadoOrganismo()==false){
            mensaje="El registro existia dado de baja. Si lo desea, puede modificar los campos y darlo de alta nuevamente";
        }
        else{
            mensaje="Listo para modificar";
        }
        return mensaje;
    }

    public void verificarOrganismo() {
        List<Organismo> orga=this.servicio.buscarPorCodigo(this.org.getCodigoOrganismo());
        if(orga.size()!=0){
            org=orga.get(0);
            this.setIsExcecuted(true);
            this.setExist(true);
        }
        else{
            this.setIsExcecuted(true);
            setExist(false);
        }
    }
    
    public void confirmarCambio(){
        List<CargoEmpleado>ced=servicio.existeCargoEmpleado(org.getIdOrganismo());
        if(ced.isEmpty()){
            this.org.setEstadoOrganismo(true);
        this.getServicio().modificar(org);
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
