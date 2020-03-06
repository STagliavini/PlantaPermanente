
package backbeans;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import ejb.OrganismoDAO;
import entidades.CargoEmpleado;
import entidades.Organismo;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author mayra
 */
@Named(value = "eliminarOrganismo")
@ViewScoped
public class eliminarOrganismo implements Serializable{

    @EJB
    private OrganismoDAO servicio;
    private Organismo org;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;
    public eliminarOrganismo() {
    }
    
    @PostConstruct
    public void Iniciar() {
        existece=false;
        this.org = new Organismo();
        isExcecuted=false;
        exist=false;
        exito=false;
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
            mensaje="El organismo ya esta dado de baja";
            exist=false;
        }
        else{
            mensaje="Confirme si desea dar de baja al organismo";
        }
        return mensaje;
    }

    public void verificarOrganismo() {
        List<Organismo> orga=this.servicio.buscarPorCodigo(this.org.getCodigoOrganismo());
        if(orga.size()!=0){
            org=orga.get(0);
            this.isExcecuted=true;
            this.exist=true;
        }
        else{
            this.isExcecuted=true;
            exist=false;
        }
    }
    
    public void confirmarBaja(){
        List<CargoEmpleado>ced=servicio.existeCargoEmpleado(org.getIdOrganismo());
        if(ced.isEmpty()){
            this.org.setEstadoOrganismo(false);
        this.servicio.modificar(org);
        this.exito=true;
        this.exist=false;
        this.isExcecuted=false;
        }
        else{
            existece=true;
        }
    }
    
    public void cancelar(){
        this.exist=false;
        this.isExcecuted=false;
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
