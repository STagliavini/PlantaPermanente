package ejb;

import entidades.CargoEmpleado;
import entidades.CargoEmpleadoPK;
import java.util.ArrayList;
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
public class CargoEmpleadoDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(CargoEmpleado x) {
        em.persist(x);
    }

    //
    public CargoEmpleado modificar(CargoEmpleado x) {
        return em.merge(x);
    }

    public void borrar(CargoEmpleado x) {
        em.remove(em.merge(x));
    }

    public List<CargoEmpleado> listarTodos() {
        Query q = em.createNamedQuery("CargoEmpleado.findAll");
        return (List<CargoEmpleado>) q.getResultList();
    }
    public List<CargoEmpleado> listarActivos() {
        Query q = em.createNamedQuery("CargoEmpleado.findActives");
        return (List<CargoEmpleado>) q.getResultList();
    }
    public List<CargoEmpleado> listarFiltrada(CargoEmpleado ce){
        String cadena="select c from CargoEmpleado c where ";
        List<CargoEmpleado> c=new ArrayList<>();
        if(ce.getEmpleado().getDniEmpleado()!=0){
            cadena=cadena+"c.cargoEmpleadoPK.idEmpleado=:idEmpleado and ";
        }
        else{
            cadena=cadena+"c.cargoEmpleadoPK.idEmpleado!=0 and ";
        }
        if(ce.getCargoEmpleadoPK().getIdCargo()!=0){
            cadena=cadena+"c.cargoEmpleadoPK.idCargo=:idCargo and ";
        }
        else{
            cadena=cadena+"c.cargoEmpleadoPK.idCargo!=0 and ";
        }
        if(ce.getCargoEmpleadoPK().getIdCategoria()!=0){
            cadena=cadena+"c.cargoEmpleadoPK.idCategoria=:idCategoria and ";
        }
        else{
            cadena=cadena+"c.cargoEmpleadoPK.idCategoria!=0 and ";
        }
        if(ce.getCargoEmpleadoPK().getIdOrganismo()!=0){
            cadena=cadena+"c.cargoEmpleadoPK.idOrganismo=:idOrganismo";
        }
        else{
            cadena=cadena+"c.cargoEmpleadoPK.idOrganismo!=0";
        }
        cadena=cadena+" and c.estado=1";
        Query q=em.createQuery(cadena);
        if(ce.getEmpleado().getDniEmpleado()!=0){
            q.setParameter("idEmpleado", ce.getEmpleado().getCodigoEmpleado());
        }
        if(ce.getCargoEmpleadoPK().getIdCargo()!=0){
            q.setParameter("idCargo", ce.getCargoEmpleadoPK().getIdCargo());
        }
        if(ce.getCargoEmpleadoPK().getIdCategoria()!=0){
            q.setParameter("idCategoria", ce.getCargoEmpleadoPK().getIdCategoria());
        }
        if(ce.getCargoEmpleadoPK().getIdOrganismo()!=0){
            q.setParameter("idOrganismo", ce.getCargoEmpleadoPK().getIdOrganismo());
        }
        if(ce.getEmpleado().getDniEmpleado()==0&&ce.getCargoEmpleadoPK().getIdCargo()==0&&
                ce.getCargoEmpleadoPK().getIdCategoria()==0&&ce.getCargoEmpleadoPK().getIdOrganismo()==0){
            return c;
        }
        return (List<CargoEmpleado>) q.getResultList();
    }
    public List<CargoEmpleado> buscarPorPK(CargoEmpleadoPK pk) {
        Query q = em.createNamedQuery("CargoEmpleado.findByPK");
        q.setParameter("idEmpleado", pk.getIdEmpleado());
        q.setParameter("idOrganismo", pk.getIdOrganismo());
        q.setParameter("idCargo", pk.getIdCargo());
        q.setParameter("idCategoria", pk.getIdCategoria());
        return (List<CargoEmpleado>) q.getResultList();
    }
    public List<CargoEmpleado> buscarExiste(CargoEmpleadoPK pk){
        Query q = em.createNamedQuery("CargoEmpleado.findByExiste");
        q.setParameter("idEmpleado", pk.getIdEmpleado());
        q.setParameter("idOrganismo", pk.getIdOrganismo());
        q.setParameter("idCargo", pk.getIdCargo());
        return (List<CargoEmpleado>) q.getResultList();
    }
    public List<CargoEmpleado> buscarExisteOtro(CargoEmpleadoPK pk){
        Query q = em.createNamedQuery("CargoEmpleado.findByExisteOtro");
        q.setParameter("idOrganismo", pk.getIdOrganismo());
        q.setParameter("idCargo", pk.getIdCargo());
        return (List<CargoEmpleado>) q.getResultList();
    }
}
