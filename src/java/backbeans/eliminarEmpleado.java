/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import ejb.EmpleadoDAO;
import entidades.CargoEmpleado;
import entidades.Empleado;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
/**
 *
 * @author Santiago Tagliavini
 */
@Named(value = "eliminarEmpleado")
@ViewScoped
public class eliminarEmpleado implements Serializable{

    @EJB
    private EmpleadoDAO servicio;
    private Empleado emp;
    private boolean isExcecuted;
    private boolean exist;
    private boolean exito;
    private boolean existece;
    public eliminarEmpleado() {
    }
    @PostConstruct
    public void Iniciar() {
        existece=false;
        this.emp = new Empleado();
        isExcecuted=false;
        exist=false;
        exito=false;
    }

    public Empleado getEmp() {
        return emp;
    }

    public void setEmp(Empleado emp) {
        this.emp = emp;
    }
    public String devolverMensaje(){
        String mensaje="";
        if(emp.getEstadoEmpleado()==false){
            mensaje="El empleado ya esta dado de baja";
            exist=false;
        }
        else{
            mensaje="Confirme si desea dar de baja al empleado";
        }
        return mensaje;
    }

    public void verificarEmpleado() {
        List<Empleado> emple=this.servicio.buscarporDNIyCodigo(emp);
        if(emple.size()!=0){
            emp=emple.get(0);
            this.isExcecuted=true;
            this.exist=true;
        }
        else{
            this.isExcecuted=true;
            exist=false;
        }
    }
    public void confirmarBaja(){
        List<CargoEmpleado>ced=servicio.existeCargoEmpleado(emp.getCodigoEmpleado());
        if(ced.isEmpty()){
            this.emp.setEstadoEmpleado(false);
        this.servicio.modificar(emp);
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
    private void limpiar(){
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
    public String convertFecha(){
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String fecha=df.format(emp.getNacimientoEmpleado());
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
