package ejb;

import entidades.CargoEmpleado;
import entidades.Categoria;
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
public class CategoriaDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(Categoria x) {
        em.persist(x);
    }

    //
    public Categoria modificar(Categoria x) {
        return em.merge(x);
    }

    public void borrar(Categoria x) {
        em.remove(em.merge(x));
    }

    public List<Categoria> listarTodos() {
        Query q = em.createNamedQuery("Categoria.findAll");
        return (List<Categoria>) q.getResultList();
    }

    public List<Categoria> buscarPorId(int id) {
        Query q = em.createNamedQuery("Categoria.findByIdCategoria");
        q.setParameter("idCategoria", id);
        return (List<Categoria>) q.getResultList();
    }
    public List<Categoria> buscarPorCodigo(int codigo) {
        Query q = em.createNamedQuery("Categoria.findByCodigoCategoria");
        q.setParameter("codigoCategoria", codigo);
        return (List<Categoria>) q.getResultList();
    }
    public List<CargoEmpleado> existeCargoEmpleado(int idCategoria){
        Query q=em.createQuery("select ce from CargoEmpleado ce where ce.cargoEmpleadoPK.idCategoria=:idCategoria and ce.estado=true");
        q.setParameter("idCategoria",idCategoria);
        return q.getResultList();
    }
}
