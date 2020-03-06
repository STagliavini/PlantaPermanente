/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.CargoDAO;
import entidades.Cargo;
import entidades.CargoEmpleado;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author santiagoat
 */
@Named(value = "eliminarCargo")
@ViewScoped
public class eliminarCargo implements Serializable{

    @EJB
    private CargoDAO servicio;
    private Cargo car;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;
    public eliminarCargo() {
    }
    
    @PostConstruct
    public void Iniciar() {
        setExistece(false);
        this.car = new Cargo();
        isExcecuted=false;
        exist=false;
        exito=false;
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
            mensaje="El cargo ya esta dado de baja";
            exist=false;
        }
        else{
            mensaje="Confirme si desea dar de baja al cargo";
        }
        return mensaje;
    }

    public void verificarCargo() {
        List<Cargo> carg=this.servicio.buscarporCodigo(this.car.getCodigoCargo());
        if(carg.size()!=0){
            car=carg.get(0);
            this.isExcecuted=true;
            this.exist=true;
        }
        else{
            this.isExcecuted=true;
            exist=false;
        }
    }
    
    public void confirmarBaja(){
        List<CargoEmpleado>ced=servicio.existeCargoEmpleado(car.getIdCargo());
        if(ced.isEmpty()){
            this.car.setEstadoCargo(false);
        this.servicio.modificar(car);
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
