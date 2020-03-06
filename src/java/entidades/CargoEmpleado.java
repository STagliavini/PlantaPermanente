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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santiagoat
 */
@Entity
@Table(name = "cargo_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargoEmpleado.findAll", query = "SELECT c FROM CargoEmpleado c")
    , @NamedQuery(name = "CargoEmpleado.findByIdEmpleado", query = "SELECT c FROM CargoEmpleado c WHERE c.cargoEmpleadoPK.idEmpleado = :idEmpleado")
    , @NamedQuery(name = "CargoEmpleado.findByIdOrganismo", query = "SELECT c FROM CargoEmpleado c WHERE c.cargoEmpleadoPK.idOrganismo = :idOrganismo")
    , @NamedQuery(name = "CargoEmpleado.findByIdCargo", query = "SELECT c FROM CargoEmpleado c WHERE c.cargoEmpleadoPK.idCargo = :idCargo")
    , @NamedQuery(name = "CargoEmpleado.findByIdCategoria", query = "SELECT c FROM CargoEmpleado c WHERE c.cargoEmpleadoPK.idCategoria = :idCategoria")
    , @NamedQuery(name = "CargoEmpleado.findByFechaIngresoOrganismo", query = "SELECT c FROM CargoEmpleado c WHERE c.fechaIngresoOrganismo = :fechaIngresoOrganismo")
    , @NamedQuery(name = "CargoEmpleado.findByEstado", query = "SELECT c FROM CargoEmpleado c WHERE c.estado = :estado")
    , @NamedQuery(name = "CargoEmpleado.findByPK", query = "SELECT c FROM "
            + "CargoEmpleado c WHERE c.cargoEmpleadoPK.idEmpleado = :idEmpleado and "
            + "c.cargoEmpleadoPK.idCargo=:idCargo and c.cargoEmpleadoPK.idOrganismo=:idOrganismo and "
            + "c.cargoEmpleadoPK.idCategoria=:idCategoria")
    , @NamedQuery(name = "CargoEmpleado.findActives", query = "SELECT c FROM CargoEmpleado c WHERE c.estado = true")
    , @NamedQuery(name = "CargoEmpleado.findByExiste", query = "SELECT c FROM "
            + "CargoEmpleado c WHERE c.cargoEmpleadoPK.idEmpleado = :idEmpleado and "
            + "c.cargoEmpleadoPK.idCargo=:idCargo and c.cargoEmpleadoPK.idOrganismo=:idOrganismo")
    , @NamedQuery(name = "CargoEmpleado.findByExisteOtro", query = "SELECT c FROM CargoEmpleado c where "
            + "c.cargoEmpleadoPK.idCargo=:idCargo and c.cargoEmpleadoPK.idOrganismo=:idOrganismo")})
public class CargoEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CargoEmpleadoPK cargoEmpleadoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso_organismo", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoOrganismo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado", nullable = false)
    private boolean estado;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "id_organismo", referencedColumnName = "id_organismo", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Organismo organismo;
    @JoinColumn(name = "id_empleado", referencedColumnName = "codigo_empleado", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;

    public CargoEmpleado() {
        this.cargoEmpleadoPK=new CargoEmpleadoPK();
        this.empleado=new Empleado();
        this.organismo=new Organismo();
    }

    public CargoEmpleado(CargoEmpleadoPK cargoEmpleadoPK) {
        this.cargoEmpleadoPK = cargoEmpleadoPK;
    }

    public CargoEmpleado(CargoEmpleadoPK cargoEmpleadoPK, Date fechaIngresoOrganismo, boolean estado) {
        this.cargoEmpleadoPK = cargoEmpleadoPK;
        this.fechaIngresoOrganismo = fechaIngresoOrganismo;
        this.estado = estado;
    }

    public CargoEmpleado(int idEmpleado, int idOrganismo, int idCargo, int idCategoria) {
        this.cargoEmpleadoPK = new CargoEmpleadoPK(idEmpleado, idOrganismo, idCargo, idCategoria);
    }

    public CargoEmpleadoPK getCargoEmpleadoPK() {
        return cargoEmpleadoPK;
    }

    public void setCargoEmpleadoPK(CargoEmpleadoPK cargoEmpleadoPK) {
        this.cargoEmpleadoPK = cargoEmpleadoPK;
    }

    public Date getFechaIngresoOrganismo() {
        return fechaIngresoOrganismo;
    }

    public void setFechaIngresoOrganismo(Date fechaIngresoOrganismo) {
        this.fechaIngresoOrganismo = fechaIngresoOrganismo;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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

    public Organismo getOrganismo() {
        return organismo;
    }

    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargoEmpleadoPK != null ? cargoEmpleadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargoEmpleado)) {
            return false;
        }
        CargoEmpleado other = (CargoEmpleado) object;
        if ((this.cargoEmpleadoPK == null && other.cargoEmpleadoPK != null) || (this.cargoEmpleadoPK != null && !this.cargoEmpleadoPK.equals(other.cargoEmpleadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CargoEmpleado[ cargoEmpleadoPK=" + cargoEmpleadoPK + " ]";
    }
    
}
