
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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mayra
 */
@Named(value = "listarCargoEmpleado")
@RequestScoped
public class listarCargoEmpleado {

    @EJB
    private CargoEmpleadoDAO servicio;
    private List<CargoEmpleado> cel;
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
    private CargoEmpleado viejo;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    private boolean existeEmpleado;
    private boolean existeCargo;
    private boolean existeCategoria;
    private boolean existeOrganismo;
    private boolean isExecutedModificar;

    public listarCargoEmpleado() {
    }
    
    @PostConstruct
    public void Iniciar() {
        this.setCemp(new CargoEmpleado());
        viejo = new CargoEmpleado();
        cel = new ArrayList<>();
        setIsExcecuted(false);
        setExito(false);
        setExiste(false);
        setExisteEmpleado(false);
        setExisteCargo(false);
        setExisteCategoria(false);
        setExisteOrganismo(false);
        isExecutedModificar = false;
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        if(usu.getTipoUsuario().equals("AOrganismo")){
            cemp.getOrganismo().setCodigoOrganismo(Integer.parseInt(usu.getNombreUsuario()));
        }
        else{
            if(usu.getTipoUsuario().equals("Empleado")){
                cemp.getEmpleado().setDniEmpleado(Long.parseLong(usu.getNombreUsuario()));
            }
        }
    }
    
    public void verificarCargoEmpleado() {
        isExcecuted = true;
        List<Empleado> emp = servicio2.buscarporDNI(cemp.getEmpleado().getDniEmpleado());
        if (emp.size() > 0) {
            cemp.setEmpleado(emp.get(0));
            cemp.getCargoEmpleadoPK().setIdEmpleado(cemp.getEmpleado().getCodigoEmpleado());
        }
        organismos=servicio5.buscarPorCodigo(cemp.getOrganismo().getCodigoOrganismo());
        if(!organismos.isEmpty()){
            cemp.getCargoEmpleadoPK().setIdOrganismo(organismos.get(0).getIdOrganismo());
        }
        else{
            cemp.getCargoEmpleadoPK().setIdOrganismo(0);
        }
        CargoEmpleadoPK pk = new CargoEmpleadoPK(cemp.getCargoEmpleadoPK().getIdEmpleado(),
                cemp.getCargoEmpleadoPK().getIdOrganismo(), cemp.getCargoEmpleadoPK().getIdCargo(),
                cemp.getCargoEmpleadoPK().getIdCategoria());
        //List<CargoEmpleado> ce = servicio.buscarPorPK(pk);
        cemp.setCargoEmpleadoPK(pk);
        cel = servicio.listarFiltrada(cemp);
    }

    /**
     * @return the servicio
     */
    public CargoEmpleadoDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(CargoEmpleadoDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the servicio2
     */
    public EmpleadoDAO getServicio2() {
        return servicio2;
    }

    /**
     * @param servicio2 the servicio2 to set
     */
    public void setServicio2(EmpleadoDAO servicio2) {
        this.servicio2 = servicio2;
    }

    /**
     * @return the servicio3
     */
    public CargoDAO getServicio3() {
        return servicio3;
    }

    /**
     * @param servicio3 the servicio3 to set
     */
    public void setServicio3(CargoDAO servicio3) {
        this.servicio3 = servicio3;
    }

    /**
     * @return the servicio4
     */
    public CategoriaDAO getServicio4() {
        return servicio4;
    }

    /**
     * @param servicio4 the servicio4 to set
     */
    public void setServicio4(CategoriaDAO servicio4) {
        this.servicio4 = servicio4;
    }

    /**
     * @return the servicio5
     */
    public OrganismoDAO getServicio5() {
        return servicio5;
    }

    /**
     * @param servicio5 the servicio5 to set
     */
    public void setServicio5(OrganismoDAO servicio5) {
        this.servicio5 = servicio5;
    }

    /**
     * @return the cemp
     */
    public CargoEmpleado getCemp() {
        return cemp;
    }

    /**
     * @param cemp the cemp to set
     */
    public void setCemp(CargoEmpleado cemp) {
        this.cemp = cemp;
    }

    /**
     * @return the isExcecuted
     */
    public boolean isIsExcecuted() {
        return isExcecuted;
    }

    /**
     * @param isExcecuted the isExcecuted to set
     */
    public void setIsExcecuted(boolean isExcecuted) {
        this.isExcecuted = isExcecuted;
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
     * @param existe the existe to set
     */
    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    /**
     * @return the existeEmpleado
     */
    public boolean isExisteEmpleado() {
        return existeEmpleado;
    }

    /**
     * @param existeEmpleado the existeEmpleado to set
     */
    public void setExisteEmpleado(boolean existeEmpleado) {
        this.existeEmpleado = existeEmpleado;
    }

    /**
     * @return the existeCargo
     */
    public boolean isExisteCargo() {
        return existeCargo;
    }

    /**
     * @param existeCargo the existeCargo to set
     */
    public void setExisteCargo(boolean existeCargo) {
        this.existeCargo = existeCargo;
    }

    /**
     * @return the existeCategoria
     */
    public boolean isExisteCategoria() {
        return existeCategoria;
    }

    /**
     * @param existeCategoria the existeCategoria to set
     */
    public void setExisteCategoria(boolean existeCategoria) {
        this.existeCategoria = existeCategoria;
    }

    /**
     * @return the existeOrganismo
     */
    public boolean isExisteOrganismo() {
        return existeOrganismo;
    }

    /**
     * @param existeOrganismo the existeOrganismo to set
     */
    public void setExisteOrganismo(boolean existeOrganismo) {
        this.existeOrganismo = existeOrganismo;
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
     * @return the organismo
     */
    public List<Organismo> getOrganismo() {
        return servicio5.listarTodos();
    }

    /**
     * @param organismos
     */
    public void setOrganismo(List<Organismo> organismos) {
        this.organismos = organismos;
    }

    /**
     * @return the cel
     */
    public List<CargoEmpleado> getCel() {
        return cel;
    }

    /**
     * @param cel the cel to set
     */
    public void setCel(List<CargoEmpleado> cel) {
        this.cel = cel;
    }

    /**
     * @return the isExecutedModificar
     */
    public boolean isIsExecutedModificar() {
        return isExecutedModificar;
    }

    /**
     * @param isExecutedModificar the isExecutedModificar to set
     */
    public void setIsExecutedModificar(boolean isExecutedModificar) {
        this.isExecutedModificar = isExecutedModificar;
    }

    /**
     * @return the viejo
     */
    public CargoEmpleado getViejo() {
        return viejo;
    }

    /**
     * @param viejo the viejo to set
     */
    public void setViejo(CargoEmpleado viejo) {
        this.viejo = viejo;
    }
}
