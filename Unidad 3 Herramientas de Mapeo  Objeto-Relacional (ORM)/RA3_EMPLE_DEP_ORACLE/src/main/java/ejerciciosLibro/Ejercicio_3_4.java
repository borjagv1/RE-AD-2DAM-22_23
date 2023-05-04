package ejerciciosLibro;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.HibernateUtil;

public class Ejercicio_3_4 {

    // Obtengo las sesión
    private static SessionFactory sesion;

    public static void main(String[] args) {
            
            // Obtengo las sesión
            sesion = HibernateUtil.getSessionFactory();
    
            // Comienzo la sesión
            Session session = sesion.openSession();
    
            // Obtengo el objeto EMPLEADO CON empNo = 7369
            datos.Empleados emp = (datos.Empleados) session.get(datos.Empleados.class, (short) 7369);
        
            // Muestro los datos del objeto
            if (emp != null) {
                System.out.println("Apellido: " + emp.getApellido());
                System.out.println("Oficio: " + emp.getOficio());
                System.out.println("Salario: " + emp.getSalario());
                System.out.println("Fecha de alta: " + emp.getFechaAlt());
                System.out.println("Comisión: " + emp.getComision());
                System.out.println("Departamento: " + emp.getDepartamentos().getDnombre());
                
            } else {
                System.out.println("No existe un empleado con empNo = 7369");
            
            }
           
    
            // Cierro la sesión
            session.close();
            System.exit(0);
        
    }
}
