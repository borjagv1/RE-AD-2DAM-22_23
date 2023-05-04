import java.util.Iterator;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.HibernateUtil;
import datos.Totales;

public class EjemploUsoClaseTotales {
	public static void main(String[] args) {		
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		
	String hql="select new primero.Totales(" +
	    		"de.deptNo,  count(em.empNo), " +
	    		"coalesce(avg(em.salario),0), "
	    		+ "de.dnombre" +
	    		")" +
	    		" from Departamentos as de, Empleados as em" +
	    		" where de.deptNo=em.departamentos.deptNo" +
	    		" group by de.deptNo,de.dnombre" ;
		
	hql=("select new primero.Totales(" +
	    		"em.departamentos.deptNo, "
	    		+ "count(em.empNo)," +
	    		"coalesce(avg(em.salario),0), "
	    		+ "em.departamentos.dnombre" +
	    		")" +
	    		" from Empleados as em " +
	    		" group by em.departamentos.deptNo,"
	    		+ "em.departamentos.dnombre" );
       

	hql= "select new primero.Totales(" +
		     " d.deptNo,  count(e.empNo), coalesce(avg(e.salario),0) , "+ 
		     " d.dnombre ) "+ 
		     " from Empleados as e right join  e.departamentos as d "+
		     " group by  d.deptNo, d.dnombre ";	
				
		Query cons = session.createQuery(hql);
		Iterator q = cons.iterate();
		while (q.hasNext()) {	    
		   Totales tot =(Totales) q.next();
		   System.out.printf( 
			  "Numero Dep: %d, Nombre: %s, Salario medio: %.2f, N� emple: %d%n",
			  tot.getNumero(), tot.getNombre(),tot.getMedia(),tot.getCuenta()); 	
		}
	 
	session.close();
	System.exit(0);
	}

}
