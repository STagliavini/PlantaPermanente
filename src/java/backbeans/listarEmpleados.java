/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.EmpleadoDAO;
import entidades.Empleado;
import entidades.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Santiago Tagliavini
 */
@Named(value = "listarEmpleados")
@RequestScoped
public class listarEmpleados {
    @EJB
    private EmpleadoDAO servicio;
    private List<Empleado>empleados;
    private Empleado emp;
    private boolean isExecuted;
    /**
     * Creates a new instance of listarEmpleados
     */
    public listarEmpleados() {
    }
    @PostConstruct
    public void iniciar(){
        isExecuted=false;
        emp=new Empleado();
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        if(usu.getTipoUsuario().equals("Empleado")){
            emp.setDniEmpleado(Integer.parseInt(usu.getNombreUsuario()));
            emp=servicio.buscarporDNI(emp.getDniEmpleado()).get(0);
        }
    }
    public void verificarEmpleado() {
        this.setIsExecuted(true);
        List<Empleado> emple = this.getServicio().buscarporDNIyCodigo(emp);
        if (emple.size() != 0) {
            empleados=emple;
        } else {
            empleados=servicio.listarTodos();
        }
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
     * @return the empleados
     */
    public List<Empleado> Empleados() {
        return getEmpleados();
    }

    /**
     * @param empleados the empleados to set
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * @return the empleados
     */
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * @return the emp
     */
    public Empleado getEmp() {
        return emp;
    }

    /**
     * @param emp the emp to set
     */
    public void setEmp(Empleado emp) {
        this.emp = emp;
    }

    /**
     * @return the isExecuted
     */
    public boolean isIsExecuted() {
        return isExecuted;
    }

    /**
     * @param isExecuted the isExecuted to set
     */
    public void setIsExecuted(boolean isExecuted) {
        this.isExecuted = isExecuted;
    }
    
}
