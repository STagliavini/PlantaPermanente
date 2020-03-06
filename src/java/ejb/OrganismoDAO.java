package ejb;

import entidades.CargoEmpleado;
import entidades.Organismo;
import javax.ejb.Stateless;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alumnos
 */
@Stateless
public class OrganismoDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(Organismo x) {
        em.persist(x);
    }

    //
    public Organismo modificar(Organismo x) {
        return em.merge(x);
    }

    public void borrar(Organismo x) {
        em.remove(em.merge(x));
    }

    public List<Organismo> listarTodos() {
        Query q = em.createNamedQuery("Organismo.findAll");
        return (List<Organismo>) q.getResultList();
    }

    public List<Organismo> buscarPorId(int id) {
        Query q = em.createNamedQuery("Organismo.findByIdOrganismo");
        q.setParameter("idOrganismo", id);
        return (List<Organismo>) q.getResultList();
    }
    public List<Organismo> buscarPorCodigo(int codigo) {
        Query q = em.createNamedQuery("Organismo.findByCodigoOrganismo");
        q.setParameter("codigoOrganismo", codigo);
        return (List<Organismo>) q.getResultList();
    }
    public List<CargoEmpleado> existeCargoEmpleado(int idOrganismo){
        Query q=em.createQuery("select ce from CargoEmpleado ce where ce.cargoEmpleadoPK.idOrganismo=:idOrganismo and ce.estado=true");
        q.setParameter("idOrganismo",idOrganismo);
        return q.getResultList();
    }
}
