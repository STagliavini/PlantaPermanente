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
import ejb.PeriodoDAO;
import ejb.ReciboSueldoDAO;
import entidades.Cargo;
import entidades.CargoEmpleado;
import entidades.CargoEmpleadoPK;
import entidades.Categoria;
import entidades.Empleado;
import entidades.Organismo;
import entidades.Periodo;
import entidades.ReciboSueldo;
import entidades.ReciboSueldoPK;
import entidades.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author santiagoat
 */
@Named(value = "imprimirRecibo")
@ViewScoped
public class imprimirRecibo implements Serializable{

    @EJB
    private ReciboSueldoDAO servicio;
    private List<ReciboSueldo> lrs;
    private ReciboSueldo rs;
    private boolean isExecuted;
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
    @EJB
    private CargoEmpleadoDAO ced;
    @EJB
    private PeriodoDAO servper;
    private Date inicio;
    private Date fin;
    public imprimirRecibo() {
    }
    @PostConstruct
    public void iniciar(){
        setInicio(new Date());
        setFin(new Date());
        rs=new ReciboSueldo();
        setIsExecuted(false);
        lrs=new ArrayList<>();
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        Usuario usu=(Usuario) req.getSession().getAttribute("usuarioActual");
        if(usu.getTipoUsuario().equals("Empleado")){
            rs.getEmpleado().setDniEmpleado(Long.parseLong(usu.getNombreUsuario()));
        }
        else{
            if(usu.getTipoUsuario().equals("AOrganismo")){
                rs.getOrganismo().setCodigoOrganismo(Integer.parseInt(usu.getNombreUsuario()));
            }
        }
    }
    public void traerRecibos() throws ParseException{
        setIsExecuted(true);
        List<Empleado> emp = servicio2.buscarporDNI(rs.getEmpleado().getDniEmpleado());
        if (emp.size() > 0) {
            rs.setEmpleado(emp.get(0));
            rs.getReciboSueldoPK().setIdEmpleado(rs.getEmpleado().getCodigoEmpleado());
        }
        organismos=servicio5.buscarPorCodigo(rs.getOrganismo().getCodigoOrganismo());
        if(!organismos.isEmpty()){
            rs.getReciboSueldoPK().setIdOrganismo(organismos.get(0).getIdOrganismo());
        }
        else{
            rs.getReciboSueldoPK().setIdOrganismo(0);
        }
        ReciboSueldoPK pk=new ReciboSueldoPK(rs.getReciboSueldoPK().getIdEmpleado(), 
                rs.getReciboSueldoPK().getIdCargo(), rs.getReciboSueldoPK().getIdCategoria(), 
                rs.getReciboSueldoPK().getIdOrganismo(), null);
        rs.setReciboSueldoPK(pk);
        if(inicio!=null){
            inicio=new SimpleDateFormat("yyyy-MM-dd").parse(servicio.extraerSig(inicio));
        }
        if(fin!=null){
            fin=new SimpleDateFormat("yyyy-MM-dd").parse(servicio.extraerSig(fin));
        }
        lrs=servicio.listarFiltrada(rs/*,servper.traerActivo()*/, getInicio(), getFin());
    }
    public boolean puedeImprimirse(Date fecha_liquidacion){
        List<Periodo> peri=servper.traerActivo();
        if(peri.isEmpty()){
            return true;
        }
        else{
            String periodo;
            String fecha_liq;
            DateFormat df=new SimpleDateFormat("MM/yyyy");
            periodo=df.format(peri.get(0).getPeriodo());
            fecha_liq=df.format(fecha_liquidacion);
            if(fecha_liq.equals(periodo)){
                return false;
            }
            else{
                return true;
            }
        }
    }
    public void verPDF(ReciboSueldo re) throws JRException, IOException, ParseException{
        Map<String,Object>parametros=new HashMap<>();
        parametros.put("nombre_cargo",re.getCargo().getNombreCargo());
        parametros.put("sueldo_basico", re.getSueldoBasico());
        parametros.put("nombre_organismo",re.getOrganismo().getNombreOrganismo().toUpperCase());
        parametros.put("direccion_organismo",re.getOrganismo().getDireccionOrganismo().toUpperCase());
        parametros.put("codigo_organismo",re.getOrganismo().getCodigoOrganismo());
        DateFormat formatoFecha = new SimpleDateFormat("MM");
        String mes=formatoFecha.format(re.getReciboSueldoPK().getFechaLiquidacion());
        DateFormat formatoFecha2 = new SimpleDateFormat("yyyy");
        String anio=formatoFecha2.format(re.getReciboSueldoPK().getFechaLiquidacion());
        parametros.put("periodo_liquidacion",
        mes+"/"+anio);
        parametros.put("descripcion_pago",devolverNombreMes(mes, anio));
        parametros.put("fecha_pago",servicio.getDate());
        parametros.put("codigo_empleado",re.getReciboSueldoPK().getIdEmpleado());
        parametros.put("apellido_nombre_empleado",re.getEmpleado().getApellidoEmpleado()+", "+
                re.getEmpleado().getNombreEmpleado());
        parametros.put("dni_empleado",re.getEmpleado().getDniEmpleado());
        parametros.put("codigo_categoria",re.getCategoria().getCodigoCategoria());
        DateFormat formatoFechaing = new SimpleDateFormat("dd/MM/yyyy");
        CargoEmpleadoPK pke=new CargoEmpleadoPK(re.getEmpleado().getCodigoEmpleado(),re.getOrganismo().getIdOrganismo(),
                re.getCargo().getIdCargo(),re.getCategoria().getIdCategoria());
        List<CargoEmpleado>ce=ced.buscarPorPK(pke);
        String fechaing=formatoFechaing.format(ce.get(0).getFechaIngresoOrganismo());
        parametros.put("fecha_ingreso",fechaing);
        parametros.put("porcentaje_antiguedad","%"+re.getAntiguedad()*2);
        parametros.put("monto_antiguedad",re.getMontoAntiguedad());
        parametros.put("porcentaje_jubilacion","%11");
        parametros.put("monto_jubilacion",re.getJubilacion());
        parametros.put("porcentaje_obra_social","%3");
        parametros.put("monto_obra_social",re.getObraSocial());
        parametros.put("total_sueldo",re.getTotalSueldo());
        parametros.put("total_recargo",re.getSueldoBasico().add(re.getMontoAntiguedad()));
        parametros.put("total_descuento",re.getJubilacion().add(re.getObraSocial()));
        File jasper=new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/recibo.jasper"));
        byte[] bytes=JasperRunManager.runReportToPdf(jasper.getPath(), parametros);
        HttpServletResponse response=(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outStream=response.getOutputStream();
        outStream.write(bytes,0,bytes.length);
        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    public void exportarPDF(ReciboSueldo re) throws JRException, IOException, ParseException{

        Map<String,Object>parametros=new HashMap<>();
        parametros.put("nombre_cargo",re.getCargo().getNombreCargo());
        parametros.put("sueldo_basico", re.getSueldoBasico());
        parametros.put("nombre_organismo",re.getOrganismo().getNombreOrganismo().toUpperCase());
        parametros.put("direccion_organismo",re.getOrganismo().getDireccionOrganismo().toUpperCase());
        parametros.put("codigo_organismo",re.getOrganismo().getCodigoOrganismo());
        DateFormat formatoFecha = new SimpleDateFormat("MM");
        String mes=formatoFecha.format(re.getReciboSueldoPK().getFechaLiquidacion());
        DateFormat formatoFecha2 = new SimpleDateFormat("yyyy");
        String anio=formatoFecha2.format(re.getReciboSueldoPK().getFechaLiquidacion());
        parametros.put("periodo_liquidacion",
        mes+"/"+anio);
        parametros.put("descripcion_pago",devolverNombreMes(mes, anio));
        parametros.put("fecha_pago",servicio.getDate());
        parametros.put("codigo_empleado",re.getReciboSueldoPK().getIdEmpleado());
        parametros.put("apellido_nombre_empleado",re.getEmpleado().getApellidoEmpleado()+", "+
                re.getEmpleado().getNombreEmpleado());
        parametros.put("dni_empleado",re.getEmpleado().getDniEmpleado());
        parametros.put("codigo_categoria",re.getCategoria().getCodigoCategoria());
        DateFormat formatoFechaing = new SimpleDateFormat("dd/MM/yyyy");
        CargoEmpleadoPK pke=new CargoEmpleadoPK(re.getEmpleado().getCodigoEmpleado(),re.getOrganismo().getIdOrganismo(),
                re.getCargo().getIdCargo(),re.getCategoria().getIdCategoria());
        List<CargoEmpleado>ce=ced.buscarPorPK(pke);
        String fechaing=formatoFechaing.format(ce.get(0).getFechaIngresoOrganismo());
        parametros.put("fecha_ingreso",fechaing);
        parametros.put("porcentaje_antiguedad","%"+re.getAntiguedad()*2);
        parametros.put("monto_antiguedad",re.getMontoAntiguedad());
        parametros.put("porcentaje_jubilacion","%11");
        parametros.put("monto_jubilacion",re.getJubilacion());
        parametros.put("porcentaje_obra_social","%3");
        parametros.put("monto_obra_social",re.getObraSocial());
        parametros.put("total_sueldo",re.getTotalSueldo());
        parametros.put("total_recargo",re.getSueldoBasico().add(re.getMontoAntiguedad()));
        parametros.put("total_descuento",re.getJubilacion().add(re.getObraSocial()));
        File jasper=new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/recibo.jasper"));
        JasperPrint jasperPrint=JasperFillManager.fillReport(jasper.getPath(),parametros);
        HttpServletResponse response=(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;filename="+re.getEmpleado().getCodigoEmpleado()+re.getOrganismo().getIdOrganismo()
                +re.getCargo().getIdCargo()+re.getCategoria().getIdCategoria()+mes+"_"+anio+".pdf");
        ServletOutputStream stream=response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    public String devolverNombreMes(String mes,String anio){
        int mess=Integer.parseInt(mes);
        String cadena="";
        switch(mess){
            case 1:
                cadena="Enero";
                break;
            case 2:
                cadena="Febrero";
                break;
            case 3:
                cadena="Marzo";
                break;
            case 4:
                cadena="Abril";
                break;
            case 5:
                cadena="Mayo";
                break;
            case 6:
                cadena="Junio";
                break;
            case 7:
                cadena="Julio";
                break;
            case 8:
                cadena="Agosto";
                break;
            case 9:
                cadena="Septiembre";
                break;
            case 10:
                cadena="Octubre";
                break;
            case 11:
                cadena="Noviembre";
                break;
            case 12:
                cadena="Diciembre";
                break;
        }
        cadena=cadena+" "+anio;
        return cadena;
    }
    /**
     * @return the servicio
     */
    public ReciboSueldoDAO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(ReciboSueldoDAO servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the rs
     */
    public ReciboSueldo getRs() {
        return rs;
    }

    /**
     * @param rs the rs to set
     */
    public void setRs(ReciboSueldo rs) {
        this.rs = rs;
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

    /**
     * @return the lrs
     */
    public List<ReciboSueldo> getLrs() {
        return lrs;
    }

    /**
     * @param lrs the lrs to set
     */
    public void setLrs(List<ReciboSueldo> lrs) {
        this.lrs = lrs;
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
     * @return the ced
     */
    public CargoEmpleadoDAO getCed() {
        return ced;
    }

    /**
     * @param ced the ced to set
     */
    public void setCed(CargoEmpleadoDAO ced) {
        this.ced = ced;
    }

    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public Date getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(Date fin) {
        this.fin = fin;
    }
}
