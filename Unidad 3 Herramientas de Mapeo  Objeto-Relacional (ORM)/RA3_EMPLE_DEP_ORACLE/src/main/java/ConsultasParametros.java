
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.query.*;

//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Empleados;
import datos.HibernateUtil;

public class ConsultasParametros {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		String hql = "from Empleados where empNo = :numemple";
						
		System.out.println("======================================================");
		Query  q = session.createQuery(hql);
		q.setParameter("numemple", (short) 7369);
		Empleados emple = (Empleados) q.uniqueResult();
		System.out.printf("%s, %s %n", emple.getApellido(), emple.getOficio());

		// Empleados cuyo número de departamento sea 10 y el oficio DIRECTOR
		Query q2 = session
				.createQuery("from Empleados emp where emp.departamentos.deptNo = :ndep and emp.oficio = :ofi");
		
		q2.setParameter("ndep", (byte) 10);
		q2.setParameter("ofi", "DIRECTOR");
		
		List<Empleados> lista = q2.list();
		Iterator<Empleados> iter = lista.iterator();
		while (iter.hasNext()) {
			// extraer el objeto
			Empleados emp = (Empleados) iter.next();
			System.out.printf("%d, %s%n", emp.getEmpNo(), emp.getApellido());
		}

		System.out.println("======================================================");
	
		String hql4 = "from Empleados emp where emp.departamentos.deptNo = :ndep and emp.oficio = :ofi";
		Query q4 = session.createQuery(hql4);
		q4.setParameter("ndep", (byte) 10);
		q4.setParameter("ofi", "DIRECTOR");
		
		List<Empleados> lista3 = q4.list();
		Iterator<Empleados> iter3 = lista3.iterator();
		while (iter3.hasNext()) {
			// extraer el objeto
			Empleados emp = (Empleados) iter3.next();
			System.out.printf("%d, %s%n", emp.getEmpNo(), emp.getApellido());
		}

		System.out.println("======================================================");
		// from Empleados emp where emp.departamentos.deptNo in (10,20)
		System.out.println("Empleados con departamento 10, 20 ");
		List<Byte> numeros = new ArrayList<Byte>();
		numeros.add((byte) 10);
		numeros.add((byte) 20);
		
		String hql5 = "from Empleados emp where emp.departamentos.deptNo in (:listadep) "
				+ "order by emp.departamentos.deptNo ";
		Query q5 = session.createQuery(hql5);
		q5.setParameterList("listadep", numeros);

		List<Empleados> lista4 = q5.list();
		Iterator<Empleados> iter4 = lista4.iterator();
		while (iter4.hasNext()) {
			// extraer el objeto
			Empleados emp = (Empleados) iter4.next();
			System.out.printf("%d, %d, %s%n", emp.getDepartamentos().getDeptNo(), emp.getEmpNo(), emp.getApellido());
		}
		
		//MOSTRAR EL APELLIDO Y OFICIO DE LOS EMPLEADOS CUYO OFICIO 
		//SEA DIRECTOR, ANALISTA, VENDEDOR
		System.out.println("======================================================");
		System.out.println("Empleados con oficio: DIRECTOR, ANALISTA, VENDEDOR");
		List<String> oficios = new ArrayList<String>();
		oficios.add("DIRECTOR");
		oficios.add("ANALISTA");
		oficios.add("VENDEDOR");
		
		String hql6 = "from Empleados emp where emp.oficio in (:listaoficios) "
				+ "order by emp.apellido ";
		Query q6 = session.createQuery(hql6);
		q6.setParameterList("listaoficios", oficios);

		List<Empleados> lista6 = q6.list();
		Iterator<Empleados> iter6 = lista6.iterator();
		while (iter6.hasNext()) {
			// extraer el objeto
			Empleados emp = (Empleados) iter6.next();
			System.out.printf("%s, %s %n", 
					emp.getApellido(), emp.getOficio());
		}
		

		// Parámetro de tipo Date
		// Empleados cuya fecha de alta es 1991-12-03

		System.out.println("======================================================");
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha = "1991-12-03";
		Date fecha = null;
		try {
			fecha = formatoDelTexto.parse(strFecha);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		String hql66 = "from Empleados where fechaAlt = :fechalta ";

		Query q66 = session.createQuery(hql66);
		q66.setParameter("fechalta", fecha);
		
		System.out.println("Empleados cuya fecha de alta es 1991-12-03");
		List<Empleados> lista5 = q66.list();
		Iterator<Empleados> iter5 = lista5.iterator();
		while (iter5.hasNext()) {
			// extraer el objeto
			Empleados emp = (Empleados) iter5.next();
			System.out.printf("%d, %d, %s%n", emp.getDepartamentos().getDeptNo(), emp.getEmpNo(), emp.getApellido());
		}

		session.close();
		System.exit(0);

	}

}
