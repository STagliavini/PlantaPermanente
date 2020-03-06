/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.EmpleadoDAO;
import ejb.UsuarioDAO;
import entidades.Empleado;
import entidades.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Santiago Tagliavini
 */
@Named(value = "agregarEmpleado")
@RequestScoped
public class agregarEmpleado {

    @EJB
    private EmpleadoDAO servicio;
    private Empleado emp;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    @EJB
    private UsuarioDAO servicio2;
    private Usuario usuario;

    public agregarEmpleado() {
    }

    @PostConstruct
    public void Iniciar() {
        this.emp = new Empleado();
        isExcecuted = false;
        exito = false;
        existe = false;
        setUsuario(new Usuario());
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
            this.emp.setEstadoEmpleado(true);
            this.servicio.modificar(emp);
            mensaje = "El registro existia dado de baja. Estado modificado a activo, si desea modificar los campos dirijase a la pagina de Modificar";
        } else {
            mensaje = "El empleado ya existe";
        }
        return mensaje;
    }

    public void guardarEmpleado() {
        List<Empleado> emple = this.servicio.buscarporDNI(this.emp.getDniEmpleado());
        if (emple.size() == 0) {
            emp.setEstadoEmpleado(true);
            this.servicio.agregar(emp);
            this.isExcecuted = true;
            this.setExito(true);
            getUsuario().setNombreUsuario(Long.toString(emp.getDniEmpleado()));
            getUsuario().setContraseniaUsuario(emp.getApellidoEmpleado().trim().toLowerCase() + "" + emp.getNacimientoEmpleado().getYear());
            getUsuario().setTipoUsuario("Empleado");
            servicio2.agregar(getUsuario());
        } else {
            emp = emple.get(0);
            existe = true;
            this.isExcecuted = true;
        }
    }

    public String cancelar() {
        this.isExcecuted = false;
        this.exito = false;
        limpiar();
        return "/vistas/agregarEmpleado.xhtml";
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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
