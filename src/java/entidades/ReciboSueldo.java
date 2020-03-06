/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santiagoat
 */
@Entity
@Table(name = "recibo_sueldo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReciboSueldo.findAll", query = "SELECT r FROM ReciboSueldo r")
    , @NamedQuery(name = "ReciboSueldo.findByIdEmpleado", query = "SELECT r FROM ReciboSueldo r WHERE r.reciboSueldoPK.idEmpleado = :idEmpleado")
    , @NamedQuery(name = "ReciboSueldo.findByIdCargo", query = "SELECT r FROM ReciboSueldo r WHERE r.reciboSueldoPK.idCargo = :idCargo")
    , @NamedQuery(name = "ReciboSueldo.findByIdCategoria", query = "SELECT r FROM ReciboSueldo r WHERE r.reciboSueldoPK.idCategoria = :idCategoria")
    , @NamedQuery(name = "ReciboSueldo.findByIdOrganismo", query = "SELECT r FROM ReciboSueldo r WHERE r.reciboSueldoPK.idOrganismo = :idOrganismo")
    , @NamedQuery(name = "ReciboSueldo.findByFechaLiquidacion", query = "SELECT r FROM ReciboSueldo r WHERE r.reciboSueldoPK.fechaLiquidacion = :fechaLiquidacion")
    , @NamedQuery(name = "ReciboSueldo.findBySueldoBasico", query = "SELECT r FROM ReciboSueldo r WHERE r.sueldoBasico = :sueldoBasico")
    , @NamedQuery(name = "ReciboSueldo.findByMontoAntiguedad", query = "SELECT r FROM ReciboSueldo r WHERE r.montoAntiguedad = :montoAntiguedad")
    , @NamedQuery(name = "ReciboSueldo.findByJubilacion", query = "SELECT r FROM ReciboSueldo r WHERE r.jubilacion = :jubilacion")
    , @NamedQuery(name = "ReciboSueldo.findByObraSocial", query = "SELECT r FROM ReciboSueldo r WHERE r.obraSocial = :obraSocial")
    , @NamedQuery(name = "ReciboSueldo.findByTotalSueldo", query = "SELECT r FROM ReciboSueldo r WHERE r.totalSueldo = :totalSueldo")
    , @NamedQuery(name = "ReciboSueldo.findByAntiguedad", query = "SELECT r FROM ReciboSueldo r WHERE r.antiguedad = :antiguedad")
    , @NamedQuery(name = "ReciboSueldo.findByPK", query = "SELECT r FROM "
            + "ReciboSueldo r WHERE r.reciboSueldoPK.idEmpleado = :idEmpleado and "
            + "r.reciboSueldoPK.idCargo=:idCargo and r.reciboSueldoPK.idOrganismo=:idOrganismo and "
            + "r.reciboSueldoPK.idCategoria=:idCategoria and r.reciboSueldoPK.fechaLiquidacion=:fechaLiquidacion")
    , @NamedQuery(name="ReciboSueldo.findByIds",query="select r from ReciboSueldo r where r.reciboSueldoPK.idEmpleado=:idEmpleado"
                + " and r.reciboSueldoPK.idOrganismo=:idOrganismo and r.reciboSueldoPK.idCargo=:idCargo and "
                + "r.reciboSueldoPK.idCategoria=:idCategoria")
    , @NamedQuery(name = "ReciboSueldo.findBySueldo", query = "SELECT r FROM "
            + "ReciboSueldo r WHERE r.reciboSueldoPK.idEmpleado = :idEmpleado and "
            + "r.reciboSueldoPK.idCargo=:idCargo and r.reciboSueldoPK.idOrganismo=:idOrganismo and r.reciboSueldoPK.fechaLiquidacion=:fechaLiquidacion")})
public class ReciboSueldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReciboSueldoPK reciboSueldoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "sueldo_basico", nullable = false, precision = 15, scale = 2)
    private BigDecimal sueldoBasico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_antiguedad", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoAntiguedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "jubilacion", nullable = false, precision = 15, scale = 2)
    private BigDecimal jubilacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obra_social", nullable = false, precision = 15, scale = 2)
    private BigDecimal obraSocial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_sueldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalSueldo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "antiguedad", nullable = false)
    private int antiguedad;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "id_empleado", referencedColumnName = "codigo_empleado", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "id_organismo", referencedColumnName = "id_organismo", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Organismo organismo;

    public ReciboSueldo() {
         this.reciboSueldoPK =new ReciboSueldoPK();
         this.empleado=new Empleado();
         this.organismo=new Organismo();
    }

    public ReciboSueldo(ReciboSueldoPK reciboSueldoPK) {
        this.reciboSueldoPK = reciboSueldoPK;
    }

    public ReciboSueldo(ReciboSueldoPK reciboSueldoPK, BigDecimal sueldoBasico, BigDecimal montoAntiguedad, BigDecimal jubilacion, BigDecimal obraSocial, BigDecimal totalSueldo, int antiguedad) {
        this.reciboSueldoPK = reciboSueldoPK;
        this.sueldoBasico = sueldoBasico;
        this.montoAntiguedad = montoAntiguedad;
        this.jubilacion = jubilacion;
        this.obraSocial = obraSocial;
        this.totalSueldo = totalSueldo;
        this.antiguedad = antiguedad;
    }

    public ReciboSueldo(int idEmpleado, int idCargo, int idCategoria, int idOrganismo, Date fechaLiquidacion) {
        this.reciboSueldoPK = new ReciboSueldoPK(idEmpleado, idCargo, idCategoria, idOrganismo, fechaLiquidacion);
    }

    public ReciboSueldoPK getReciboSueldoPK() {
        return reciboSueldoPK;
    }

    public void setReciboSueldoPK(ReciboSueldoPK reciboSueldoPK) {
        this.reciboSueldoPK = reciboSueldoPK;
    }

    public BigDecimal getSueldoBasico() {
        return sueldoBasico;
    }

    public void setSueldoBasico(BigDecimal sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    public BigDecimal getMontoAntiguedad() {
        return montoAntiguedad;
    }

    public void setMontoAntiguedad(BigDecimal montoAntiguedad) {
        this.montoAntiguedad = montoAntiguedad;
    }

    public BigDecimal getJubilacion() {
        return jubilacion;
    }

    public void setJubilacion(BigDecimal jubilacion) {
        this.jubilacion = jubilacion;
    }

    public BigDecimal getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(BigDecimal obraSocial) {
        this.obraSocial = obraSocial;
    }

    public BigDecimal getTotalSueldo() {
        return totalSueldo;
    }

    public void setTotalSueldo(BigDecimal totalSueldo) {
        this.totalSueldo = totalSueldo;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Organismo getOrganismo() {
        return organismo;
    }

    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reciboSueldoPK != null ? reciboSueldoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReciboSueldo)) {
            return false;
        }
        ReciboSueldo other = (ReciboSueldo) object;
        if ((this.reciboSueldoPK == null && other.reciboSueldoPK != null) || (this.reciboSueldoPK != null && !this.reciboSueldoPK.equals(other.reciboSueldoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ReciboSueldo[ reciboSueldoPK=" + reciboSueldoPK + " ]";
    }
    
}
