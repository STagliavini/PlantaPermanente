
package ejb;

import javax.ejb.Stateless;
import entidades.Cargo;
import entidades.CargoEmpleado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mayra
 */
@Stateless
public class CargoDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;
    //Agregar llama a persistencia
    public void agregar(Cargo x) {
        em.persist(x);
    }
    
    public Cargo modificar(Cargo x) {
        return em.merge(x);
    }

    public void borrar(Cargo x) {
        em.remove(em.merge(x));
    }
    
    public List<Cargo> listarTodos() {
        Query q = em.createNamedQuery("Cargo.findAll");
        return (List<Cargo>) q.getResultList();
    }

    public List<Cargo> buscarPorId(int id) {
        Query q = em.createNamedQuery("Cargo.findByIdCargo");
        q.setParameter("idCargo", id);
        return (List<Cargo>) q.getResultList();
    }
    
    public List<Cargo> buscarporCodigo(long cod){
        Query q= em.createNamedQuery("Cargo.findByCodigoCargo");
        q.setParameter("codigoCargo", cod);
        return (List<Cargo>) q.getResultList();
    }
    public List<CargoEmpleado> existeCargoEmpleado(int idCargo){
        Query q=em.createQuery("select ce from CargoEmpleado ce where ce.cargoEmpleadoPK.idCargo=:idCargo and ce.estado=true");
        q.setParameter("idCargo",idCargo);
        return q.getResultList();
    }
    
}
