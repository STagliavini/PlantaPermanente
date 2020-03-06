package ejb;

import entidades.CargoEmpleado;
import javax.ejb.Stateless;
import entidades.Empleado;
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
public class EmpleadoDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(Empleado x) {
        em.persist(x);
    }

    //
    public Empleado modificar(Empleado x) {
        return em.merge(x);
    }

    public void borrar(Empleado x) {
        em.remove(em.merge(x));
    }

    public List<Empleado> listarTodos() {
        Query q = em.createNamedQuery("Empleado.findAll");
        return (List<Empleado>) q.getResultList();
    }

    public List<Empleado> buscarPorId(int id) {
        Query q = em.createNamedQuery("Empleado.findByCodigoEmpleado");
        q.setParameter("codigoEmpleado", id);
        return (List<Empleado>) q.getResultList();
    }
    public List<Empleado> buscarporDNI(long dni){
        Query q= em.createNamedQuery("Empleado.findByDniEmpleado");
        q.setParameter("dniEmpleado", dni);
        return (List<Empleado>) q.getResultList();
    }
    public List<Empleado> buscarporDNIyCodigo(Empleado emp){
        String cadena="select e from Empleado e where";
        List<Empleado> p=new ArrayList<>();
        if((emp.getCodigoEmpleado()==null||emp.getCodigoEmpleado()==0)){
            cadena=cadena+" e.codigoEmpleado!=0";
        }
        else{
            cadena=cadena+" e.codigoEmpleado="+emp.getCodigoEmpleado();
        }
        if(emp.getDniEmpleado()==0){
            cadena=cadena+" and e.dniEmpleado!=0";
        }
        else{
            cadena=cadena+" and e.dniEmpleado="+emp.getDniEmpleado();
        }
        if((emp.getCodigoEmpleado()==null||emp.getCodigoEmpleado()==0)&&emp.getDniEmpleado()==0){
            return p;
        }
        Query q = em.createQuery(cadena);
        return (List<Empleado>) q.getResultList();
    }
    public List<CargoEmpleado> existeCargoEmpleado(int idEmpleado){
        Query q=em.createQuery("select ce from CargoEmpleado ce where ce.cargoEmpleadoPK.idEmpleado=:idEmpleado and ce.estado=true");
        q.setParameter("idEmpleado",idEmpleado);
        return q.getResultList();
    }
}
