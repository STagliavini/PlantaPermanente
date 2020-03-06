/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author santiagoat
 */
@Embeddable
public class ReciboSueldoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empleado", nullable = false)
    private int idEmpleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cargo", nullable = false)
    private int idCargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_categoria", nullable = false)
    private int idCategoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_organismo", nullable = false)
    private int idOrganismo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_liquidacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaLiquidacion;

    public ReciboSueldoPK() {
    }

    public ReciboSueldoPK(int idEmpleado, int idCargo, int idCategoria, int idOrganismo, Date fechaLiquidacion) {
        this.idEmpleado = idEmpleado;
        this.idCargo = idCargo;
        this.idCategoria = idCategoria;
        this.idOrganismo = idOrganismo;
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdOrganismo() {
        return idOrganismo;
    }

    public void setIdOrganismo(int idOrganismo) {
        this.idOrganismo = idOrganismo;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEmpleado;
        hash += (int) idCargo;
        hash += (int) idCategoria;
        hash += (int) idOrganismo;
        hash += (fechaLiquidacion != null ? fechaLiquidacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReciboSueldoPK)) {
            return false;
        }
        ReciboSueldoPK other = (ReciboSueldoPK) object;
        if (this.idEmpleado != other.idEmpleado) {
            return false;
        }
        if (this.idCargo != other.idCargo) {
            return false;
        }
        if (this.idCategoria != other.idCategoria) {
            return false;
        }
        if (this.idOrganismo != other.idOrganismo) {
            return false;
        }
        if ((this.fechaLiquidacion == null && other.fechaLiquidacion != null) || (this.fechaLiquidacion != null && !this.fechaLiquidacion.equals(other.fechaLiquidacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ReciboSueldoPK[ idEmpleado=" + idEmpleado + ", idCargo=" + idCargo + ", idCategoria=" + idCategoria + ", idOrganismo=" + idOrganismo + ", fechaLiquidacion=" + fechaLiquidacion + " ]";
    }
    
}
