/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.CargoDAO;
import ejb.CargoEmpleadoDAO;
import ejb.CategoriaDAO;
import ejb.EmpleadoDAO;
import ejb.OrganismoDAO;
import entidades.Cargo;
import entidades.CargoEmpleado;
import entidades.CargoEmpleadoPK;
import entidades.Categoria;
import entidades.Empleado;
import entidades.Organismo;
import entidades.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@Named(value = "agregarCargoEmpleado")
@RequestScoped
public class agregarCargo_Empleado {

    @EJB
    private CargoEmpleadoDAO servicio;
    @EJB
    private EmpleadoDAO servicio2;
    private List<Empleado> empleados;
    @EJB
    private CargoDAO servicio3;
    private List<Cargo> cargos;
    @EJB
    private CategoriaDAO servicio4;
    private List<Categoria> categorias;
    @EJB
    private OrganismoDAO servicio5;
    private List<Organismo> organismos;
    private CargoEmpleado cemp;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    private boolean otroEmpleado;
    private boolean existeEmpleado;
    private boolean existeCargo;
    private boolean existeCategoria;
    private boolean existeOrganismo;
    private CargoEmpleado most;
    public agregarCargo_Empleado() {
    }

    @PostConstruct
    public void Iniciar() {
        this.cemp = new CargoEmpleado();
        isExcecuted = false;
        exito = false;
        existe = false;
        existeEmpleado = false;
        existeCargo = false;
        existeCategoria = false;
        existeOrganismo = false;
        setOtroEmpleado(false);
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        if(usu.getTipoUsuario().equals("AOrganismo")){
            cemp.getOrganismo().setCodigoOrganismo(Integer.parseInt(usu.getNombreUsuario()));
        }
    }

    public String devolverMensaje() {
        String mensaje = "";
        if (cemp.getEstado() == false) {
            cemp.setEstado(true);
            servicio.modificar(cemp);
            mensaje = "El registro existia dado de baja. Estado modificado a activo, si desea modificar los campos dirijase a la pagina de Modificar";
        } else {
            mensaje = "El cargo ya existe para ese empleado";
        }
        return mensaje;
    }

    public void guardarCargoEmpleado() {
        List<Empleado> emp = servicio2.buscarporDNI(cemp.getEmpleado().getDniEmpleado());
        List<Cargo> cargo = servicio3.buscarPorId(cemp.getCargoEmpleadoPK().getIdCargo());
        List<Categoria> cat = servicio4.buscarPorId(cemp.getCargoEmpleadoPK().getIdCategoria());
        List<Organismo> org = servicio5.buscarPorCodigo(cemp.getOrganismo().getCodigoOrganismo());
        boolean permitir_ingreso = true;
        most=new CargoEmpleado();
        isExcecuted = true;
        if (emp.size() == 0) {
            permitir_ingreso = false;
        } else {
            cemp.setEmpleado(emp.get(0));
            cemp.getCargoEmpleadoPK().setIdEmpleado(cemp.getEmpleado().getCodigoEmpleado());
            if (emp.get(0).getEstadoEmpleado()) {
                existeEmpleado = true;
            } else {
                permitir_ingreso = false;
            }
        }
        if (cargo.size() == 0) {
            permitir_ingreso = false;
        } else {
            if (cargo.get(0).getEstadoCargo()) {
                existeCargo = true;
            } else {
                permitir_ingreso = false;
            }
        }
        if (cat.size() == 0) {
            permitir_ingreso = false;
        } else {
            if (cat.get(0).getEstadoCategoria()) {
                existeCategoria = true;
            } else {
                permitir_ingreso = false;
            }
        }
        if (org.size() == 0) {
            permitir_ingreso = false;
        } else {
            if (org.get(0).getEstadoOrganismo()) {
                existeOrganismo = true;
            } else {
                permitir_ingreso = false;
            }
        }
        if (permitir_ingreso) {
            CargoEmpleadoPK pk = new CargoEmpleadoPK(cemp.getCargoEmpleadoPK().getIdEmpleado(),
                    org.get(0).getIdOrganismo(), cemp.getCargoEmpleadoPK().getIdCargo(),
                    cemp.getCargoEmpleadoPK().getIdCategoria());
            List<CargoEmpleado> ce = servicio.buscarExiste(pk);
            if (ce.size() == 0) {
                List<CargoEmpleado> ex = servicio.buscarPorPK(pk);
                if (ex.size() == 0) {
//                    cemp.setEstado(true);
//                    cemp.setCargo(cargo.get(0));
//                    cemp.setCategoria(cat.get(0));
//                    cemp.setEmpleado(emp.get(0));
//                    cemp.setOrganismo(org.get(0));
                    cemp.setCargoEmpleadoPK(pk);
                    most.setCargo(cargo.get(0));
                    most.setCargoEmpleadoPK(cemp.getCargoEmpleadoPK());
                    most.setCategoria(cat.get(0));
                    most.setEmpleado(emp.get(0));
                    most.setOrganismo(org.get(0));
                    most.setEstado(true);
                    most.setFechaIngresoOrganismo(cemp.getFechaIngresoOrganismo());
                    this.servicio.agregar(most);
                    exito = true;
                    limpiar();
                }
                else{
                    otroEmpleado=true;
                }
            } else {
                cemp = ce.get(0);
                existe = true;
            }
        }
    }

    public void cancelar() {
        this.isExcecuted = false;
        this.exito = false;
        this.existe = false;
        otroEmpleado=false;
        limpiar();
    }

    public String convertFecha() {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String fecha = df.format(most.getFechaIngresoOrganismo());
        return fecha;
    }

    private String limpiar() {
        cemp.getEmpleado().setDniEmpleado(0);
        cemp.getCargoEmpleadoPK().setIdCargo(0);
        cemp.getCargoEmpleadoPK().setIdCategoria(0);
        cemp.getCargoEmpleadoPK().setIdEmpleado(0);
        cemp.getCargoEmpleadoPK().setIdOrganismo(0);
        cemp.setFechaIngresoOrganismo(null);
        return "agregarCargo_Empleado.html";
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
     * @return the servicio
     */
    public CargoEmpleadoDAO getServicio() {
        return servicio;
    }

    /**
     * @return the cemp
     */
    public CargoEmpleado getCemp() {
        return cemp;
    }

    /**
     * @return the servicio2
     */
    public EmpleadoDAO getServicio2() {
        return servicio2;
    }

    /**
     * @return the servicio3
     */
    public CargoDAO getServicio3() {
        return servicio3;
    }

    /**
     * @return the servicio4
     */
    public CategoriaDAO getServicio4() {
        return servicio4;
    }

    /**
     * @return the servicio5
     */
    public OrganismoDAO getServicio5() {
        return servicio5;
    }

    /**
     * @return the existeEmpleado
     */
    public boolean isExisteEmpleado() {
        return existeEmpleado;
    }

    /**
     * @return the existeCargo
     */
    public boolean isExisteCargo() {
        return existeCargo;
    }

    /**
     * @return the existeCategoria
     */
    public boolean isExisteCategoria() {
        return existeCategoria;
    }

    /**
     * @return the existeOrganismo
     */
    public boolean isExisteOrganismo() {
        return existeOrganismo;
    }

    /**
     * @return the empleados
     */
    public List<Empleado> getEmpleados() {
        return servicio2.listarTodos();
    }

    /**
     * @param empleados the empleados to set
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * @return the cargos
     */
    public List<Cargo> getCargos() {
        return servicio3.listarTodos();
    }

    /**
     * @param cargos the cargos to set
     */
    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    /**
     * @return the categorias
     */
    public List<Categoria> getCategorias() {
        return servicio4.listarTodos();
    }

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    /**
     * @return the organismos
     */
    public List<Organismo> getOrganismos() {
        return servicio5.listarTodos();
    }

    /**
     * @param organismos the organismos to set
     */
    public void setOrganismos(List<Organismo> organismos) {
        this.organismos = organismos;
    }

    /**
     * @return the otroEmpleado
     */
    public boolean isOtroEmpleado() {
        return otroEmpleado;
    }

    /**
     * @param otroEmpleado the otroEmpleado to set
     */
    public void setOtroEmpleado(boolean otroEmpleado) {
        this.otroEmpleado = otroEmpleado;
    }

    /**
     * @return the most
     */
    public CargoEmpleado getMost() {
        return most;
    }

    /**
     * @param most the most to set
     */
    public void setMost(CargoEmpleado most) {
        this.most = most;
    }

}
