/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backbeans;

import ejb.CargoDAO;
import ejb.CargoEmpleadoDAO;
import ejb.CategoriaDAO;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import ejb.EmpleadoDAO;
import ejb.OrganismoDAO;
import entidades.Cargo;
import entidades.CargoEmpleado;
import entidades.CargoEmpleadoPK;
import entidades.Categoria;
import entidades.Empleado;
import entidades.Organismo;
import entidades.Usuario;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author Santiago Tagliavini
 */
@Named(value = "eliminarCargoEmpleado")
@ViewScoped
public class eliminarCargo_Empleado implements Serializable{

    @EJB
    private CargoEmpleadoDAO servicio;
    private List<CargoEmpleado> cel;
    @EJB
    private EmpleadoDAO servicio2;
    private List<Empleado>empleados;
    @EJB
    private CargoDAO servicio3;
    private List<Cargo>cargos;
    @EJB
    private CategoriaDAO servicio4;
    private List<Categoria>categorias;
    @EJB
    private OrganismoDAO servicio5;
    private List<Organismo>organismos;
    private CargoEmpleado cemp;
    private boolean isExcecuted;
    private boolean exito;
    private boolean existe;
    private boolean existeEmpleado;
    private boolean existeCargo;
    private boolean existeCategoria;
    private boolean existeOrganismo;
    private boolean isExecutedEliminar;
    public eliminarCargo_Empleado() {
    }
    @PostConstruct
    public void Iniciar() {
        this.setCemp(new CargoEmpleado());
        setCel(new ArrayList<>());
        setIsExcecuted(false);
        setExito(false);
        setExiste(false);
        setExisteEmpleado(false);
        setExisteCargo(false);
        setExisteCategoria(false);
        setExisteOrganismo(false);
        setIsExecutedEliminar(false);
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        if(usu.getTipoUsuario().equals("AOrganismo")){
            cemp.getOrganismo().setCodigoOrganismo(Integer.parseInt(usu.getNombreUsuario()));
        }
    }

    public String devolverMensaje(){
        String mensaje="";
        if(cemp.getEstado()==false){
            mensaje="El empleado ya esta dado de baja";
            existe=false;
        }
        else{
            mensaje="Confirme si desea dar de baja al cargo para empleado";
        }
        return mensaje;
    }

    public void verificarCargoEmpleado() {
        setIsExcecuted(true);
        List<Empleado> emp=getServicio2().buscarporDNI(cemp.getEmpleado().getDniEmpleado());
        if(emp.size()>0){
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
        CargoEmpleadoPK pk = new CargoEmpleadoPK(getCemp().getCargoEmpleadoPK().getIdEmpleado(),
                cemp.getCargoEmpleadoPK().getIdOrganismo(), getCemp().getCargoEmpleadoPK().getIdCargo(),
                getCemp().getCargoEmpleadoPK().getIdCategoria());
        List<CargoEmpleado> ce = getServicio().buscarPorPK(pk);
        cemp.setCargoEmpleadoPK(pk);
        setCel(servicio.listarFiltrada(cemp));
//        for(int i=0;i<cel.size();i++){
//            organismos=servicio5.buscarPorCodigo(cel.get(i).getOrganismo().getCodigoOrganismo());
//        cel.get(i).setOrganismo(organismos.get(0));
//        cargos=servicio3.buscarPorId(cel.get(i).getCargoEmpleadoPK().getIdCargo());
//        cel.get(i).setCargo(cargos.get(0));
//        empleados=servicio2.buscarPorId(cel.get(i).getCargoEmpleadoPK().getIdEmpleado());
//        cel.get(i).setEmpleado(empleados.get(0));
//        categorias=servicio4.buscarPorId(cel.get(i).getCargoEmpleadoPK().getIdCategoria());
//        cel.get(i).setCategoria(categorias.get(0));
//        }
    }
    public void baja(CargoEmpleado cem){
        cemp=cem;
        setIsExecutedEliminar(true);
    }
    public void confirmarBaja(){
        this.cemp.setEstado(false);
        this.getServicio().modificar(cemp);
        this.setExito(true);
        this.existe=false;
        this.setIsExcecuted(false);
        this.setIsExecutedEliminar(false);
    }
    public void cancelar(){
        this.existe=false;
        this.setIsExcecuted(false);
        this.setIsExecutedEliminar(false);
        limpiar();
    }
    private void limpiar(){
        setIsExcecuted(false);
        setExito(false);
        setExiste(false);
        setExisteEmpleado(false);
        setExisteCargo(false);
        setExisteCategoria(false);
        setExisteOrganismo(false);
    }
    public String convertFecha() {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String fecha = df.format(cemp.getFechaIngresoOrganismo());
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
    

    /**
     * @return the exito
     */
    public boolean isExito() {
        return exito;
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
     * @param servicio2 the servicio2 to set
     */
    public void setServicio2(EmpleadoDAO servicio2) {
        this.servicio2 = servicio2;
    }

    /**
     * @return the empleados
     */
   

    /**
     * @param empleados the empleados to set
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * @param servicio3 the servicio3 to set
     */
    public void setServicio3(CargoDAO servicio3) {
        this.servicio3 = servicio3;
    }

    /**
     * @return the cargos
     */
    /**
     * @param cargos the cargos to set
     */
    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    /**
     * @param servicio4 the servicio4 to set
     */
    public void setServicio4(CategoriaDAO servicio4) {
        this.servicio4 = servicio4;
    }

    /**
     * @return the categorias
     */
    

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    /**
     * @param servicio5 the servicio5 to set
     */
    public void setServicio5(OrganismoDAO servicio5) {
        this.servicio5 = servicio5;
    }

    /**
     * @return the organismos
     */
    

    /**
     * @param organismos the organismos to set
     */
    public void setOrganismos(List<Organismo> organismos) {
        this.organismos = organismos;
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
     * @param isExcecuted the isExcecuted to set
     */
    public void setIsExcecuted(boolean isExcecuted) {
        this.isExcecuted = isExcecuted;
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
    public List<Empleado> getEmpleados() {
        return getServicio2().listarTodos();
    }
    public List<Cargo> getCargos() {
        return getServicio3().listarTodos();
    }
    public List<Categoria> getCategorias() {
        return getServicio4().listarTodos();
    }
    public List<Organismo> getOrganismo() {
        return servicio5.listarTodos();
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
     * @return the servicio2
     */
    public EmpleadoDAO getServicio2() {
        return servicio2;
    }

    /**
     * @return the isExecutedEliminar
     */
    public boolean isIsExecutedEliminar() {
        return isExecutedEliminar;
    }

    /**
     * @param isExecutedEliminar the isExecutedEliminar to set
     */
    public void setIsExecutedEliminar(boolean isExecutedEliminar) {
        this.isExecutedEliminar = isExecutedEliminar;
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
}
