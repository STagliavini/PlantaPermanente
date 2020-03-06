/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.CargoEmpleadoDAO;
import ejb.EmpleadoDAO;
import entidades.CargoEmpleado;
import entidades.Empleado;
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
 * @author Santiago Tagliavini
 */
@Named(value = "modificarEmpleado")
@ViewScoped
public class modificarEmpleado implements Serializable {

    @EJB
    private EmpleadoDAO servicio;
    private Empleado emp;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;

    public modificarEmpleado() {
    }

    @PostConstruct
    public void Iniciar() {
        this.emp = new Empleado();
        existece=false;
        setIsExcecuted(false);
        setExist(false);
        setExito(false);
    }

    public Empleado getEmp() {
        return emp;
    }

    public void setEmp(Empleado emp) {
        this.emp = emp;
    }

    public String devolverMensaje() {
        String mensaje = "";
        if (emp.getEstadoEmpleado() == false) {
            mensaje = "El registro existia dado de baja. Si lo desea, puede modificar los campos y darlo de alta nuevamente";
        } else {
            mensaje = "Listo para modificar";
        }
        return mensaje;
    }

    public void verificarEmpleado() {
        List<Empleado> emple = this.getServicio().buscarporDNIyCodigo(emp);
        if (emple.size() != 0) {
            emp = emple.get(0);
            this.setIsExcecuted(true);
            this.setExist(true);
        } else {
            this.setIsExcecuted(true);
            setExist(false);
        }
    }

    public void confirmarCambio() {
        List<CargoEmpleado> ced = servicio.existeCargoEmpleado(emp.getCodigoEmpleado());
        if (ced.isEmpty()) {
            this.emp.setEstadoEmpleado(true);
            this.getServicio().modificar(emp);
            this.setExito(true);
            this.setExist(false);
            this.setIsExcecuted(false);
        }
        else{
            existece=true;
        }
    }

    public void cancelar() {
        this.setExist(false);
        this.setIsExcecuted(false);
        this.exito = false;
        limpiar();
    }

    private void limpiar() {
        emp.setApellidoEmpleado("");
        emp.setDireccionEmpleado("");
        emp.setCodigoEmpleado(null);
        emp.setDniEmpleado(0);
        emp.setEstadoEmpleado(true);
        emp.setMailEmpleado("");
        emp.setNacimientoEmpleado(null);
        emp.setNombreEmpleado("");
        emp.setSexoEmpleado("");
        emp.setTelefonoEmpleado("");
    }

    public String convertFecha() {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String fecha = df.format(emp.getNacimientoEmpleado());
        return fecha;
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
    public EmpleadoDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(EmpleadoDAO servicio) {
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
