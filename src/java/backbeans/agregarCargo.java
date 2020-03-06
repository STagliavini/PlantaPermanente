
package backbeans;

import ejb.CargoDAO;
import entidades.Cargo;
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
@Named(value = "agregarCargo")
@RequestScoped
public class agregarCargo {

    @EJB
    private CargoDAO servicio;
    private Cargo car;
    private Cargo auxcar;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    
    public agregarCargo() {
    }
    
    @PostConstruct
    public void Iniciar() {
        this.car = new Cargo();
        setAuxcar(new Cargo());
        isExcecuted = false;
        exito = false;
        existe = false;
    }

    public Cargo getCar() {
        return car;
    }

    public void setCar(Cargo car) {
        this.car = car;
    }

    public String devolverMensaje() {
        String mensaje = "";
        if (car.getEstadoCargo() == false) {
            this.car.setEstadoCargo(true);
            this.servicio.modificar(car);
            mensaje = "El registro existia dado de baja. Estado modificado a activo, si desea modificar los campos dirijase a la pagina de Modificar";
        } else {
            mensaje = "El cargo ya existe";
        }
        return mensaje;
    }

    public void guardarCargo() {
        List<Cargo> carg = this.servicio.buscarporCodigo(this.car.getCodigoCargo());
        if (carg.size() == 0) {
            car.setEstadoCargo(true);
            this.servicio.agregar(car);
            this.isExcecuted = true;
            this.setExito(true);
            getAuxcar().setCodigoCargo(car.getCodigoCargo());
            getAuxcar().setDescripcionCargo(car.getDescripcionCargo());
            getAuxcar().setEstadoCargo(car.getEstadoCargo());
            getAuxcar().setIdCargo(car.getIdCargo());
            getAuxcar().setNombreCargo(car.getNombreCargo());
            limpiar();
        } else {
            car = carg.get(0);
            existe=true;
            this.isExcecuted = true;
        }
    }

    public String cancelar() {
        this.isExcecuted = false;
        this.exito=false;
        limpiar();
        return "/vistas/agregarCargo";
    }

    private void limpiar() {
        
        car.setCodigoCargo(0);
        car.setEstadoCargo(true);
        car.setDescripcionCargo("");
        car.setNombreCargo("");
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

    /**
     * @return the auxcar
     */
    public Cargo getAuxcar() {
        return auxcar;
    }

    /**
     * @param auxcar the auxcar to set
     */
    public void setAuxcar(Cargo auxcar) {
        this.auxcar = auxcar;
    }

}
