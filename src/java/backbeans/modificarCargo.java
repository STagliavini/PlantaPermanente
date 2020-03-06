
package backbeans;

import ejb.CargoDAO;
import entidades.Cargo;
import entidades.CargoEmpleado;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
//import javax.faces.view.ViewScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author mayra
 */
@Named(value = "modificarCargo")
@ViewScoped
public class modificarCargo implements Serializable{

    @EJB
    private CargoDAO servicio;
    private Cargo car;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;
    public modificarCargo() {
    }
    
    @PostConstruct
    public void Iniciar() {
        setExistece(false);
        this.car = new Cargo();
        setIsExcecuted(false);
        setExist(false);
        setExito(false);
    }

    public Cargo getCar() {
        return car;
    }

    public void setCar(Cargo car) {
        this.car = car;
    }
    public String devolverMensaje(){
        String mensaje="";
        if(car.getEstadoCargo()==false){
            mensaje="El registro existia dado de baja. Si lo desea, puede modificar los campos y darlo de alta nuevamente";
        }
        else{
            mensaje="Listo para modificar";
        }
        return mensaje;
    }

    public void verificarCargo() {
        List<Cargo> carg=this.getServicio().buscarporCodigo(this.car.getCodigoCargo());
        if(carg.size()!=0){
            car = carg.get(0);
            this.setIsExcecuted(true);
            this.setExist(true);
        }
        else{
            this.setIsExcecuted(true);
            setExist(false);
        }
    }
    
    public void confirmarCambio(){
        List<CargoEmpleado>ced=servicio.existeCargoEmpleado(car.getIdCargo());
        if(ced.isEmpty()){
            this.car.setEstadoCargo(true);
        this.getServicio().modificar(car);
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
    
    private String limpiar() {
        
        car.setCodigoCargo(0);
        car.setEstadoCargo(true);
        car.setDescripcionCargo("");
        car.setNombreCargo("");
        return "/index.xhtml";
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
