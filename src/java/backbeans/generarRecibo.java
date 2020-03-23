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
import entidades.Categoria;
import entidades.Empleado;
import entidades.Organismo;
import entidades.Periodo;
import entidades.ReciboSueldo;
import entidades.ReciboSueldoPK;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author santiagoat
 */
@Named(value = "generarRecibo")
@ViewScoped
public class generarRecibo implements Serializable {

    @EJB
    private ReciboSueldoDAO servicio;
    private ReciboSueldo recibo;
    @EJB
    private CargoEmpleadoDAO serviciocargoxempleado;
    private List<CargoEmpleado> cemps;
    private int cantidad_recibos;
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
    private Periodo per;
    private boolean hayperActivo;
    private Calendar c;

    public generarRecibo() {

    }

    @PostConstruct
    public void iniciar() {
        setRecibo(new ReciboSueldo());
        setCemps(getServiciocargoxempleado().listarActivos());
        setCantidad_recibos(0);
        isExecuted = false;
        per = new Periodo();
        hayperActivo = false;
        Date factual = null;
        List<Periodo> peri = servper.traerActivo();
        if (peri.size() > 0) {
            per = peri.get(0);
            try {
                hayperActivo = true;
                Date fech = null;
                DateFormat formatoFecha = new SimpleDateFormat("MM/yyyy");
                fech = formatoFecha.parse(servper.extraerSimple(per.getPeriodo()));
                Date fech2 = null;
                DateFormat formatoFecha2 = new SimpleDateFormat("dd/MM/yyyy");
                fech2 = formatoFecha2.parse(servicio.getDate());
                int dif = servicio.datediff(per.getFechaInicio(), fech2);
                if (dif <= 0) {
                    recibo.getReciboSueldoPK().setFechaLiquidacion(fech);
                    c = Calendar.getInstance();
                    c.setTime(recibo.getReciboSueldoPK().getFechaLiquidacion());
                } else {
                    hayperActivo = false;
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void generar() throws ParseException {
        int dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int anio = c.get(Calendar.YEAR);
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        recibo.getReciboSueldoPK().setFechaLiquidacion(formatoFecha.parse(dia + "/" + mes + "/" + anio));
        cantidad_recibos = 0;
        isExecuted = true;
        for (int i = 0; i < cemps.size(); i++) {
            ReciboSueldo rse = new ReciboSueldo();
            int id_empleado = cemps.get(i).getCargoEmpleadoPK().getIdEmpleado();
            int id_cargo = cemps.get(i).getCargoEmpleadoPK().getIdCargo();
            int id_categoria = cemps.get(i).getCargoEmpleadoPK().getIdCategoria();
            int id_organismo = cemps.get(i).getCargoEmpleadoPK().getIdOrganismo();
            int antiguedad = servicio.datediff(recibo.getReciboSueldoPK().getFechaLiquidacion(), cemps.get(i).getFechaIngresoOrganismo()) / 365;
            int porcentaje_antiguedad = antiguedad * 2;
            BigDecimal sueldo_basico = cemps.get(i).getCategoria().getSueldoCategoria();
            BigDecimal anti = new BigDecimal(porcentaje_antiguedad);
            BigDecimal tres = new BigDecimal(3);
            BigDecimal cien = new BigDecimal(100);
            BigDecimal once = new BigDecimal(11);
            BigDecimal monto_antiguedad = (sueldo_basico.multiply(anti)).divide(cien);
            BigDecimal jubilacion = (sueldo_basico.multiply(once)).divide(cien);
            BigDecimal obra_social = (sueldo_basico.multiply(tres)).divide(cien);
            BigDecimal total = sueldo_basico.add(monto_antiguedad).subtract(jubilacion).subtract(obra_social);
            ReciboSueldoPK pk = new ReciboSueldoPK(id_empleado, id_cargo, id_categoria, id_organismo, recibo.getReciboSueldoPK().getFechaLiquidacion());
            List<ReciboSueldo> rs = servicio.buscarPorSueldo(pk);
            if (rs.size() == 0) {
                rse.getReciboSueldoPK().setIdCargo(id_cargo);
                rse.getReciboSueldoPK().setIdCategoria(id_categoria);
                rse.getReciboSueldoPK().setIdOrganismo(id_organismo);
                rse.getReciboSueldoPK().setIdEmpleado(id_empleado);
                rse.getReciboSueldoPK().setFechaLiquidacion(recibo.getReciboSueldoPK().getFechaLiquidacion());
                rse.setEmpleado(cemps.get(i).getEmpleado());
                rse.setCargo(cemps.get(i).getCargo());
                rse.setCategoria(cemps.get(i).getCategoria());
                rse.setOrganismo(cemps.get(i).getOrganismo());
                rse.setAntiguedad(antiguedad);
                rse.setMontoAntiguedad(monto_antiguedad);
                rse.setJubilacion(jubilacion);
                rse.setObraSocial(obra_social);
                rse.setSueldoBasico(sueldo_basico);
                rse.setTotalSueldo(total);
                servicio.agregar(rse);
                cantidad_recibos++;
            } else {
                rse.getReciboSueldoPK().setIdCargo(id_cargo);
                rse.getReciboSueldoPK().setIdCategoria(id_categoria);
                rse.getReciboSueldoPK().setIdOrganismo(id_organismo);
                rse.getReciboSueldoPK().setIdEmpleado(id_empleado);
                rse.getReciboSueldoPK().setFechaLiquidacion(recibo.getReciboSueldoPK().getFechaLiquidacion());
                rse.setEmpleado(cemps.get(i).getEmpleado());
                rse.setCargo(cemps.get(i).getCargo());
                rse.setCategoria(cemps.get(i).getCategoria());
                rse.setOrganismo(cemps.get(i).getOrganismo());
                rse.setAntiguedad(antiguedad);
                rse.setMontoAntiguedad(monto_antiguedad);
                rse.setJubilacion(jubilacion);
                rse.setObraSocial(obra_social);
                rse.setSueldoBasico(sueldo_basico);
                rse.setTotalSueldo(total);
                servicio.borrarRecibos(id_cargo, id_organismo, id_empleado, recibo.getReciboSueldoPK().getFechaLiquidacion());
                servicio.agregar(rse);
                cantidad_recibos++;
            }
        }
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
     * @return the recibo
     */
    public ReciboSueldo getRecibo() {
        return recibo;
    }

    /**
     * @param recibo the recibo to set
     */
    public void setRecibo(ReciboSueldo recibo) {
        this.recibo = recibo;
    }

    /**
     * @return the serviciocargoxempleado
     */
    public CargoEmpleadoDAO getServiciocargoxempleado() {
        return serviciocargoxempleado;
    }

    /**
     * @param serviciocargoxempleado the serviciocargoxempleado to set
     */
    public void setServiciocargoxempleado(CargoEmpleadoDAO serviciocargoxempleado) {
        this.serviciocargoxempleado = serviciocargoxempleado;
    }

    /**
     * @return the cemps
     */
    public List<CargoEmpleado> getCemps() {
        return cemps;
    }

    /**
     * @param cemps the cemps to set
     */
    public void setCemps(List<CargoEmpleado> cemps) {
        this.cemps = cemps;
    }

    /**
     * @return the cantidad_recibos
     */
    public int getCantidad_recibos() {
        return cantidad_recibos;
    }

    /**
     * @param cantidad_recibos the cantidad_recibos to set
     */
    public void setCantidad_recibos(int cantidad_recibos) {
        this.cantidad_recibos = cantidad_recibos;
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
     * @return the per
     */
    public Periodo getPer() {
        return per;
    }

    /**
     * @param per the per to set
     */
    public void setPer(Periodo per) {
        this.per = per;
    }

    /**
     * @return the hayperActivo
     */
    public boolean isHayperActivo() {
        return hayperActivo;
    }

    /**
     * @param hayperActivo the hayperActivo to set
     */
    public void setHayperActivo(boolean hayperActivo) {
        this.hayperActivo = hayperActivo;
    }
}
