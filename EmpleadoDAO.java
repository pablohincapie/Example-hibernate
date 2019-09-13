package org.pruebasgwt.modelo;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pruebasgwt.hibernate.Empleado;
import org.pruebasgwt.hibernate.HibernateUtil;
/**
 *
 * @author Pablo
 */
public class EmpleadoDAO {
    private Session sesion; 
    private Transaction tx;  

    public long guardaEmpleado(Empleado empleado) throws HibernateException 
    { 
        long id = 0;  

        try 
        { 
            iniciaOperacion(); 
            id = (Long) sesion.save(empleado); 
            tx.commit(); 
        } catch (HibernateException he) 
        { 
            manejaExcepcion(he); 
            throw he; 
        } finally 
        { 
            sesion.close(); 
        }  

        return id; 
    }  

    public void actualizaEmpleado(Empleado empleado) throws HibernateException 
    { 
        try 
        { 
            iniciaOperacion(); 
            sesion.update(empleado); 
            tx.commit(); 
        } catch (HibernateException he) 
        { 
            manejaExcepcion(he); 
            throw he; 
        } finally 
        { 
            sesion.close(); 
        } 
    }  

    public void eliminaEmpleado(Empleado empleado) throws HibernateException 
    { 
        try 
        { 
            iniciaOperacion(); 
            sesion.delete(empleado); 
            tx.commit(); 
        } catch (HibernateException he) 
        { 
            manejaExcepcion(he); 
            throw he; 
        } finally 
        { 
            sesion.close(); 
        } 
    }  

    public Empleado obtenEmpleado(long idEmpleado) throws HibernateException 
    { 
        Empleado empleado = null;  
        try 
        { 
            iniciaOperacion(); 
            empleado = (Empleado) sesion.get(Empleado.class, idEmpleado); 
        } finally 
        { 
            sesion.close(); 
        }  

        return empleado; 
    }  

    public List<Empleado> obtenListaEmpleados() throws HibernateException 
    { 
        List<Empleado> listaEmpleados = null;  

        try 
        { 
            iniciaOperacion(); 
            listaEmpleados = sesion.createQuery("from Empleado").list(); 
        } finally 
        { 
            sesion.close(); 
        }  

        return listaEmpleados; 
    }  

    private void iniciaOperacion() throws HibernateException 
    { 
        sesion = HibernateUtil.getSessionFactory().openSession(); 
        tx = sesion.beginTransaction(); 
    }  

    private void manejaExcepcion(HibernateException he) throws HibernateException 
    { 
        tx.rollback(); 
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he); 
    } 
}