/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santiagoat
 */
@Entity
@Table(name = "categoria", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo_categoria"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c where c.estadoCategoria=true")
    , @NamedQuery(name = "Categoria.findByCodigoCategoria", query = "SELECT c FROM Categoria c WHERE c.codigoCategoria = :codigoCategoria")
    , @NamedQuery(name = "Categoria.findBySueldoCategoria", query = "SELECT c FROM Categoria c WHERE c.sueldoCategoria = :sueldoCategoria")
    , @NamedQuery(name = "Categoria.findByIdCategoria", query = "SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria")
    , @NamedQuery(name = "Categoria.findByEstadoCategoria", query = "SELECT c FROM Categoria c WHERE c.estadoCategoria = :estadoCategoria")})
public class Categoria implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<ReciboSueldo> reciboSueldoList;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_categoria", nullable = false)
    private int codigoCategoria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "sueldo_categoria", nullable = false, precision = 15, scale = 2)
    private BigDecimal sueldoCategoria;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_categoria", nullable = false)
    private boolean estadoCategoria;

    public Categoria() {
    }

    public Categoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Categoria(Integer idCategoria, int codigoCategoria, BigDecimal sueldoCategoria, boolean estadoCategoria) {
        this.idCategoria = idCategoria;
        this.codigoCategoria = codigoCategoria;
        this.sueldoCategoria = sueldoCategoria;
        this.estadoCategoria = estadoCategoria;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public BigDecimal getSueldoCategoria() {
        return sueldoCategoria;
    }

    public void setSueldoCategoria(BigDecimal sueldoCategoria) {
        this.sueldoCategoria = sueldoCategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public boolean getEstadoCategoria() {
        return estadoCategoria;
    }

    public void setEstadoCategoria(boolean estadoCategoria) {
        this.estadoCategoria = estadoCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Categoria[ idCategoria=" + idCategoria + " ]";
    }

    @XmlTransient
    public List<ReciboSueldo> getReciboSueldoList() {
        return reciboSueldoList;
    }

    public void setReciboSueldoList(List<ReciboSueldo> reciboSueldoList) {
        this.reciboSueldoList = reciboSueldoList;
    }
    
}
