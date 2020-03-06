/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santiagoat
 */
@Entity
@Table(name = "cargo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo_cargo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c where c.estadoCargo=true")
    , @NamedQuery(name = "Cargo.findByCodigoCargo", query = "SELECT c FROM Cargo c WHERE c.codigoCargo = :codigoCargo")
    , @NamedQuery(name = "Cargo.findByNombreCargo", query = "SELECT c FROM Cargo c WHERE c.nombreCargo = :nombreCargo")
    , @NamedQuery(name = "Cargo.findByDescripcionCargo", query = "SELECT c FROM Cargo c WHERE c.descripcionCargo = :descripcionCargo")
    , @NamedQuery(name = "Cargo.findByIdCargo", query = "SELECT c FROM Cargo c WHERE c.idCargo = :idCargo")
    , @NamedQuery(name = "Cargo.findByEstadoCargo", query = "SELECT c FROM Cargo c WHERE c.estadoCargo = :estadoCargo")})
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_cargo", nullable = false)
    private int codigoCargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre_cargo", nullable = false, length = 30)
    private String nombreCargo;
    @Size(max = 100)
    @Column(name = "descripcion_cargo", length = 100)
    private String descripcionCargo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cargo", nullable = false)
    private Integer idCargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_cargo", nullable = false)
    private boolean estadoCargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<CargoEmpleado> cargoEmpleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<ReciboSueldo> reciboSueldoList;

    public Cargo() {
    }

    public Cargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Cargo(Integer idCargo, int codigoCargo, String nombreCargo, boolean estadoCargo) {
        this.idCargo = idCargo;
        this.codigoCargo = codigoCargo;
        this.nombreCargo = nombreCargo;
        this.estadoCargo = estadoCargo;
    }

    public int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public boolean getEstadoCargo() {
        return estadoCargo;
    }

    public void setEstadoCargo(boolean estadoCargo) {
        this.estadoCargo = estadoCargo;
    }

    @XmlTransient
    public List<CargoEmpleado> getCargoEmpleadoList() {
        return cargoEmpleadoList;
    }

    public void setCargoEmpleadoList(List<CargoEmpleado> cargoEmpleadoList) {
        this.cargoEmpleadoList = cargoEmpleadoList;
    }

    @XmlTransient
    public List<ReciboSueldo> getReciboSueldoList() {
        return reciboSueldoList;
    }

    public void setReciboSueldoList(List<ReciboSueldo> reciboSueldoList) {
        this.reciboSueldoList = reciboSueldoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cargo[ idCargo=" + idCargo + " ]";
    }
    
}
