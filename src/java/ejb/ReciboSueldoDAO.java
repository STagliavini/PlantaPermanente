package ejb;

import entidades.CargoEmpleado;
import entidades.CargoEmpleadoPK;
import entidades.Periodo;
import entidades.ReciboSueldo;
import entidades.ReciboSueldoPK;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ReciboSueldoDAO {

    @PersistenceContext(unitName = "Planta_PermanentePU")
    EntityManager em;

    //Agregar llama a persistencia
    public void agregar(ReciboSueldo x) {
        em.persist(x);
    }

    //
    public ReciboSueldo modificar(ReciboSueldo x) {
        return em.merge(x);
    }

    public void borrar(ReciboSueldo x) {
        em.remove(em.merge(x));
    }

    public List<ReciboSueldo> listarTodos() {
        Query q = em.createNamedQuery("ReciboSueldo.findAll");
        return (List<ReciboSueldo>) q.getResultList();
    }
    public List<ReciboSueldo> listarFiltrada(ReciboSueldo rs/*,List<Periodo> per*/,Date inicio,Date fin){
        String cadena="select r from ReciboSueldo r where ";
        String periodo="";
        List<ReciboSueldo> c=new ArrayList();
        if(rs.getEmpleado().getDniEmpleado()!=0){
            cadena=cadena+"r.reciboSueldoPK.idEmpleado=:idEmpleado and ";
        }
        else{
            cadena=cadena+"r.reciboSueldoPK.idEmpleado!=0 and ";
        }
        if(rs.getReciboSueldoPK().getIdCargo()!=0){
            cadena=cadena+"r.reciboSueldoPK.idCargo=:idCargo and ";
        }
        else{
            cadena=cadena+"r.reciboSueldoPK.idCargo!=0 and ";
        }
        if(rs.getReciboSueldoPK().getIdCategoria()!=0){
            cadena=cadena+"r.reciboSueldoPK.idCategoria=:idCategoria and ";
        }
        else{
            cadena=cadena+"r.reciboSueldoPK.idCategoria!=0 and ";
        }
        if(rs.getReciboSueldoPK().getIdOrganismo()!=0){
            cadena=cadena+"r.reciboSueldoPK.idOrganismo=:idOrganismo and ";
        }
        else{
            cadena=cadena+"r.reciboSueldoPK.idOrganismo!=0 and ";
        }
        if(inicio!=null){
            cadena=cadena+"r.reciboSueldoPK.fechaLiquidacion>=:fechaLiquidacion and ";
        }
        else{
            cadena=cadena+"r.reciboSueldoPK.fechaLiquidacion!='0-0-0' and ";
        }
        if(fin!=null){
            cadena=cadena+"r.reciboSueldoPK.fechaLiquidacion<=:fechaLiquidacionn";
        }
        else{
            cadena=cadena+"r.reciboSueldoPK.fechaLiquidacion!='0-0-0'";
        }
//        if(!per.isEmpty()){
//            cadena=cadena+" and r.reciboSueldoPK.fechaLiquidacion!=:fechaLiquidacion";
//        }
        Query q=em.createQuery(cadena);
        if(rs.getEmpleado().getDniEmpleado()!=0){
            q.setParameter("idEmpleado", rs.getEmpleado().getCodigoEmpleado());
        }
        if(rs.getReciboSueldoPK().getIdCargo()!=0){
            q.setParameter("idCargo", rs.getReciboSueldoPK().getIdCargo());
        }
        if(rs.getReciboSueldoPK().getIdCategoria()!=0){
            q.setParameter("idCategoria", rs.getReciboSueldoPK().getIdCategoria());
        }
        if(rs.getReciboSueldoPK().getIdOrganismo()!=0){
            q.setParameter("idOrganismo", rs.getReciboSueldoPK().getIdOrganismo());
        }
        if(inicio!=null){
            q.setParameter("fechaLiquidacion",inicio);
        }
        if(fin!=null){
            q.setParameter("fechaLiquidacionn",fin);
        }
        if(rs.getEmpleado().getDniEmpleado()==0&&rs.getReciboSueldoPK().getIdCargo()==0&&
                rs.getReciboSueldoPK().getIdCategoria()==0&&rs.getReciboSueldoPK().getIdOrganismo()==0 && inicio==null&&fin==null){
            return c;
        }
        /*if(!per.isEmpty()){
            periodo=extraer(per.get(0).getFechaInicio());
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Date peri = null;
            try {
                peri=df.parse(periodo);
            } catch (ParseException e) {
            }
            q.setParameter("fechaLiquidacion",peri);
        }*/
        c=(List<ReciboSueldo>) q.getResultList();
        return c;
    }
    public List<ReciboSueldo> buscarPorPK(ReciboSueldoPK pk) {
        Query q = em.createNamedQuery("ReciboSueldo.findByPK");
        q.setParameter("idEmpleado", pk.getIdEmpleado());
        q.setParameter("idOrganismo", pk.getIdOrganismo());
        q.setParameter("idCargo", pk.getIdCargo());
        q.setParameter("idCategoria", pk.getIdCategoria());
        q.setParameter("fechaLiquidacion", pk.getFechaLiquidacion());
        return (List<ReciboSueldo>) q.getResultList();
    }
    public List<ReciboSueldo> buscarPorIds(ReciboSueldoPK pk) {
        Query q = em.createNamedQuery("ReciboSueldo.findByIds");
        q.setParameter("idEmpleado", pk.getIdEmpleado());
        q.setParameter("idOrganismo", pk.getIdOrganismo());
        q.setParameter("idCargo", pk.getIdCargo());
        q.setParameter("idCategoria", pk.getIdCategoria());
        return (List<ReciboSueldo>) q.getResultList();
    }
    public List<ReciboSueldo> buscarPorSueldo(ReciboSueldoPK pk) {
        Query q = em.createNamedQuery("ReciboSueldo.findBySueldo");
        q.setParameter("idEmpleado", pk.getIdEmpleado());
        q.setParameter("idOrganismo", pk.getIdOrganismo());
        q.setParameter("idCargo", pk.getIdCargo());
        q.setParameter("fechaLiquidacion", pk.getFechaLiquidacion());
        return (List<ReciboSueldo>) q.getResultList();
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
    public String extraer(Date fecha){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String fech=df.format(fecha);
        Query q=em.createNativeQuery("select month('"+fech+"') from recibo_sueldo");
        Query r=em.createNativeQuery("select year('"+fech+"') from recibo_sueldo");
        Query s=em.createNativeQuery("select last_day('"+fech+"') from recibo_sueldo");
        String mes=q.getResultList().get(0).toString();
        String anio=r.getResultList().get(0).toString();
        String dias=s.getResultList().get(0).toString();
        Query t=em.createNativeQuery("select day('"+dias+"') from periodo");
        String dia=t.getResultList().get(0).toString();
        String cadena=anio+"-"+mes+"-"+dia;
        return cadena;
    }
    public String extraerSig(Date fecha){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String fech=df.format(fecha);
        Query q=em.createNativeQuery("select month('"+fech+"') from recibo_sueldo");
        Query r=em.createNativeQuery("select year('"+fech+"') from recibo_sueldo");
        String mes=q.getResultList().get(0).toString();
        String anio=r.getResultList().get(0).toString();
        int mess=Integer.parseInt(mes);
        int an=Integer.parseInt(anio);
        if(mess==12){
            mess=1;
            an++;
        }
        else{
            mess++;
        }
        fech=an+"-"+mess+"-1";
        Query s=em.createNativeQuery("select last_day('"+fech+"') from recibo_sueldo");
        String cadena=s.getResultList().get(0).toString();
        return cadena;
    }
    public void borrarRecibos(int id_cargo,int id_organismo,int id_empleado,Date fecha){
        Query q=em.createNativeQuery("delete from recibo_sueldo where "
                + "id_empleado=?idEmpleado and "
                + "id_cargo=?idCargo and "
                + "id_organismo=?idOrganismo and "
                + "fecha_liquidacion=?fechaLiquidacion");
        q.setParameter("idEmpleado", id_empleado);
        q.setParameter("idOrganismo", id_organismo);
        q.setParameter("idCargo", id_cargo);
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String fe=df.format(fecha);
        q.setParameter("fechaLiquidacion", fecha);
        q.executeUpdate();
    }
}