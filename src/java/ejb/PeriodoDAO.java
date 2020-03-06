package ejb;

import javax.ejb.Stateless;
import entidades.Empleado;
import entidades.Periodo;
import entidades.Usuario;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class PeriodoDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(Periodo x) {
        em.persist(x);
    }

    //
    public Periodo modificar(Periodo x) {
        return em.merge(x);
    }

    public void borrar(Periodo x) {
        em.remove(em.merge(x));
    }

    public List<Periodo> listarTodos() {
        Query q = em.createNamedQuery("Periodo.findAll");
        return (List<Periodo>) q.getResultList();
    }
    public List<Periodo>traerActivo(){
        Query q=em.createQuery("select p from Periodo p where p.estado=true");
        return (List<Periodo>) q.getResultList();
    }
    public List<Periodo>existe(Date periodo){
        String cadena="select p from Periodo p where p.periodo=:periodo";
        Query q=em.createQuery(cadena);
        q.setParameter("periodo",periodo);
        return (List<Periodo>) q.getResultList();
    }
    public String extraer(Date fecha){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String fech=df.format(fecha);
        Query q=em.createNativeQuery("select month('"+fech+"') from periodo");
        Query r=em.createNativeQuery("select year('"+fech+"') from periodo");
        Query s=em.createNativeQuery("select last_day('"+fech+"') from periodo");
        String mes=q.getResultList().get(0).toString();
        String anio=r.getResultList().get(0).toString();
        String dias=s.getResultList().get(0).toString();
        Query t=em.createNativeQuery("select day('"+dias+"') from periodo");
        String dia=t.getResultList().get(0).toString();
        String cadena=dia+"/"+mes+"/"+anio;
        return cadena;
    }
    public String extraerSimple(Date fecha){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String fech=df.format(fecha);
        Query q=em.createNativeQuery("select month('"+fech+"')");
        Query r=em.createNativeQuery("select year('"+fech+"')");
        Query s=em.createNativeQuery("select last_day('"+fech+"')");
        String mes=q.getResultList().get(0).toString();
        String anio=r.getResultList().get(0).toString();
        String cadena=mes+"/"+anio;
        return cadena;
    }
    public String[] extraerMA(Date fecha){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String fech=df.format(fecha);
        Query q=em.createNativeQuery("select month('"+fech+"')");
        Query r=em.createNativeQuery("select year('"+fech+"')");
        Query s=em.createNativeQuery("select last_day('"+fech+"')");
        String mes=q.getResultList().get(0).toString();
        String anio=r.getResultList().get(0).toString();
        String[] arre={mes,anio};
        return arre;
    }
    public int datediff(Date fecha_liquidacion,Date fecha_ingreso){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String fechaing=df.format(fecha_ingreso);
        String fechaliq=df.format(fecha_liquidacion);
        Query q= em.createNativeQuery("select datediff('"+fechaliq+"','"+fechaing+"')");
        return (Integer) q.getSingleResult();
    }
    public String getDate() throws ParseException{
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        DateFormat dfe = new SimpleDateFormat("dd/MM/yyyy");
        Query q= em.createNativeQuery("select curdate()");
        String fecha_actual=q.getSingleResult().toString();
        Date f=df.parse(fecha_actual);
        String devolver=dfe.format(f);
        return devolver;
    }
}
