package ejb;

import javax.ejb.Stateless;
import entidades.Empleado;
import entidades.Usuario;
import java.util.ArrayList;
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
public class UsuarioDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(Usuario x) {
        em.persist(x);
    }

    //
    public Usuario modificar(Usuario x) {
        return em.merge(x);
    }

    public void borrar(Usuario x) {
        em.remove(em.merge(x));
    }

    public List<Usuario> listarTodos() {
        Query q = em.createNamedQuery("Usuario.findAll");
        return (List<Usuario>) q.getResultList();
    }
    public List<Usuario> traerUsuario(String nombreUsuario,String contraseniaUsuario){
        Query q = em.createNamedQuery("Usuario.findByUsuario");
        q.setParameter("nombreUsuario", nombreUsuario);
        q.setParameter("contraseniaUsuario", contraseniaUsuario);
        return (List<Usuario>) q.getResultList();
    }
}
