import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.Alumnos;
import datos.Asignaturas;
import datos.Centros;
import datos.Cursos;
import datos.Departamentos;
import datos.Evaluaciones;
import datos.EvaluacionesId;
import datos.HibernateUtil;

public class Main_Colegio_BGV {

    private static SessionFactory sesion;
    private static java.util.Scanner sc = new java.util.Scanner(System.in);

    public static void main(String[] args) {

        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        sesion = HibernateUtil.getSessionFactory();

        Ejercicio_1();

        Ejercicio_2();

    }// MAIN

    private static void Ejercicio_2() {
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        try {
            System.out.println("Ejercicio 2");
            System.out.println("************");
            BigInteger numEvaluacion = BigInteger
                    .valueOf(leerEntero("Código Evaluación (1 y 3, 0 para terminar): ", 0, 3));
            BigInteger numAlumno = BigInteger.ZERO;
            BigInteger numAsignatura = BigInteger.valueOf(0);
            BigDecimal nota = BigDecimal.ZERO;
            // Proceso repetitivo hasta que num sea 0
            while (numEvaluacion != BigInteger.ZERO) {
                System.out.println(numEvaluacion);
                // Convierto a BigInteger el numero de alumno dado
                numAlumno = BigInteger.valueOf(leerEntero("Código Alumno ( > 0): ", 0, Integer.MAX_VALUE));
                // Compruebo si existe el alumno
                Alumnos alumno = session.get(Alumnos.class, numAlumno);
                if (alumno == null) {
                    System.out.println("ERROR - El alumno no existe");

                } else {
                    // HQL para consultar las asignaturas del alumno
                    String hql = "select a.codAsig from Asignaturas a join a.cursos c join c.alumnoses al where al.numAlumno = :numAlumno";
                    // Meto en una lista los resultados de la consulta
                    List<BigInteger> lista = session.createQuery(hql, BigInteger.class)
                            .setParameter("numAlumno", numAlumno).list();
                    // Recorro la lista y muestro los resultados
                    System.out.println("Asignaturas del alumno: ");
                    for (BigInteger resultados : lista) {
                        System.out.print(resultados + " ");

                    }
                    numAsignatura = BigInteger.valueOf(leerEntero(": ", 0, Integer.MAX_VALUE));
                    // Compruebo si existe la asignatura para el alumno dado
                    Asignaturas asignatura = session.get(Asignaturas.class, numAsignatura);
                    if (asignatura == null) {
                        System.out.println("ERROR - La asignatura no existe");
                    } else {
                        String hql2 = "select a from Asignaturas a join a.cursos c join c.alumnoses al where al.numAlumno = :numAlumno and a.codAsig = :numAsignatura";
                        // Meto en una lista los resultados de la consulta
                        Asignaturas asignatura2 = session.createQuery(hql2, Asignaturas.class)
                                .setParameter("numAlumno", numAlumno).setParameter("numAsignatura", numAsignatura)
                                .uniqueResult();
                        if (asignatura2 == null) {
                            System.out.println("ERROR - La asignatura no es del curso del alumno");
                        } else {
                            // Introducir la nota
                            nota = BigDecimal.valueOf(leerEntero("Nota (0 y 10): ", 0, 10));
                            // Consulta hql para ver si ya existe esa nota para ese alumno, esa asignatura y
                            // esa evaluación
                            String hql3 = "select e.nota from Evaluaciones e where e.id.numAlumno = :numAlumno and e.id.codAsig = :numAsignatura and e.id.codEvaluacion = :numEvaluacion";
                            // Meto en una lista los resultados de la consulta
                            BigDecimal notax = session.createQuery(hql3, BigDecimal.class)
                                    .setParameter("numAlumno", numAlumno)
                                    .setParameter("numAsignatura", numAsignatura)
                                    .setParameter("numEvaluacion", numEvaluacion)
                                    .uniqueResult();
                            if (notax == null) {

                                // Inserto todos los datos comprobados
                                EvaluacionesId id = new EvaluacionesId(numEvaluacion, numAlumno, numAsignatura);
                                Evaluaciones evaluacion = new Evaluaciones(id, alumno, asignatura, nota);
                                session.save(evaluacion);

                                System.out.println("Evaluación insertada");
                            } else {
                                System.out.println(
                                        "ERROR - Ya hay nota para ese alumno en esa asignatura de esa evaluación");

                            }

                        }
                    }
                }
                // pido otra vez el codigo de evaluacion
                numEvaluacion = BigInteger.valueOf(leerEntero("Código Evaluación (1 y 3, 0 para terminar): ", 0, 3));

            } // WHILE
              // Actualizo todos los centros en una sola transacción
            tx.commit();

        } catch (TransientPropertyValueException e) {
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + e.getMessage() + " NO EXISTE");
            tx.rollback();
        } catch (ConstraintViolationException cve) {
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + cve.getMessage() + " DUPLICADO");
            tx.rollback();
        } catch (HibernateException he) {
            // Manejar cualquier otra excepción de Hibernate
            System.err.println("Error al actualizar los objetos: " + he.getMessage());
            tx.rollback();
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            System.err.println("ERROR NO CONTROLADO al actualizar los objetos : " + e.getMessage());
            tx.rollback();
        } finally {
            // Cerrar la sesión
            session.close();
        }
    }

    private static void Ejercicio_1() {
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Actividad1_Apartado_A(session);
            Actividad1_Apartado_B(session);
            Actividad1_Apartado_Ab_numCursos(session);
            Actividad1_Apartado_C(session);

            // Actualizo todos los centros en una sola transacción
            tx.commit();

        } catch (TransientPropertyValueException e) {
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + e.getMessage() + " NO EXISTE");
            tx.rollback();
        } catch (ConstraintViolationException cve) {
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + cve.getMessage() + " DUPLICADO");
            tx.rollback();
        } catch (HibernateException he) {
            // Manejar cualquier otra excepción de Hibernate
            System.err.println("Error al actualizar los objetos: " + he.getMessage());
            tx.rollback();
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            System.err.println("ERROR NO CONTROLADO al actualizar los objetos : " + e.getMessage());
            tx.rollback();
        } finally {
            // Cerrar la sesión
            session.close();
        }
    }

    private static void Actividad1_Apartado_C(Session session) {
        System.out.println("Actividad 1 Apartado C");
        System.out.println("***********************");
        // Consulta HQL para obtener la nota media de los alumnos
        String hql = "select e.alumnos.numAlumno, round(avg(e.nota), 2) from Evaluaciones e join e.alumnos a group by e.alumnos.numAlumno order by 1";
        // Meto en una lista los resultados de la consulta
        List<Object[]> lista = session.createQuery(hql, Object[].class).list();
        // Recorro la lista y muestro los resultados
        for (Object[] resultados : lista) {
            System.out.println("Alumno: " + resultados[0] + " Nota media: " + resultados[1]);
            // cargo el objeto alumno para guardar los cambios
            Alumnos alumno = session.get(Alumnos.class, (BigInteger) resultados[0]);
            // Convierto el resultado de la nota media en BigDecimal
            BigDecimal notaMedia = BigDecimal.valueOf((Double) resultados[1]);
            // Actualizo la nota media
            alumno.setNotaMedia((BigDecimal) notaMedia);
            // Actualizo el alumno
            session.update(alumno);
        }
    }

    private static void Actividad1_Apartado_Ab_numCursos(Session session) {
        System.out.println("Actividad 1 Apartado Ab");
        System.out.println("***********************");
        // Consulta HQL para obtener el numero de alumnos por curso
        String hql = "select al.cursos.codCurso, count(al.numAlumno) from Alumnos al join al.cursos c group by al.cursos.codCurso order by 1";
        // Meto en una lista los resultados de la consulta
        List<Object[]> lista = session.createQuery(hql, Object[].class).list();
        // Recorro la lista y muestro los resultados
        for (Object[] resultados : lista) {
            System.out.println("Curso: " + resultados[0] + " Alumnos: " + resultados[1]);
            // cargo el objeto curso para guardar los cambios
            Cursos curso = session.get(Cursos.class, (BigInteger) resultados[0]);
            // Actualizo el numero de alumnos
            // Convierto el resultado del count en BigInteger
            BigInteger numAlumnos = BigInteger.valueOf((Long) resultados[1]);
            curso.setNumAlumnos((BigInteger) numAlumnos);
            // Actualizo el curso
            session.update(curso);
        }
    }

    private static void Actividad1_Apartado_B(Session session) {
        System.out.println("Actividad 1 Apartado B");
        System.out.println("***********************");
        // Consulta HQL para saber el nº de Asignaturas por Departamento
        String hql = "select d.codDepar, count(a.codAsig) from Asignaturas a, Departamentos d where a.departamentos.codDepar = d.codDepar group by d.codDepar";
        // Meto en una lista los resultados de la consulta
        List<Object[]> lista = session.createQuery(hql, Object[].class).list();
        // Recorro la lista y muestro los resultados
        for (Object[] resultados : lista) {
            System.out.println("Departamento: " + resultados[0] + " Asignaturas: " + resultados[1]);
            // cargo el objeto departamento para guardar los cambios
            Departamentos departamento = session.get(Departamentos.class, (BigInteger) resultados[0]);
            // Actualizo el numero de asignaturas
            // Convierto el resultado del count en BigInteger
            BigInteger numAsignaturas = BigInteger.valueOf((Long) resultados[1]);
            departamento.setNumAsig((BigInteger) numAsignaturas);
            // Actualizo el departamento
            session.update(departamento);
        }
    }

    private static void Actividad1_Apartado_A(Session session) {
        System.out.println("Actividad 1 Apartado A");
        System.out.println("***********************");
        // Consulta HQL para obtener el numero de cursos por centro
        String hql = "SELECT cur.centros.codCentro, COUNT(cur.codCurso), (SELECT COUNT(DISTINCT a.numAlumno) FROM Alumnos a, Cursos c, Centros ce WHERE a.cursos.codCurso = c.codCurso AND c.centros.codCentro = ce.codCentro AND ce.codCentro = cur.centros.codCentro) FROM Cursos cur GROUP BY cur.centros.codCentro";
        // Meto en una lista los resultados de la consulta
        List<Object[]> lista = session.createQuery(hql, Object[].class).list();
        // Recorro la lista y muestro los resultados
        for (Object[] resultados : lista) {
            System.out.println(
                    "Centro: " + resultados[0] + " Cursos: " + resultados[1] + " Alumnos: " + resultados[2]);
            // cargo el objeto centro para guardar los cambios
            Centros centro = session.get(Centros.class, (BigInteger) resultados[0]);
            // Actualizo el numero de cursos
            // Convierto el resultado del count en BigInteger
            BigInteger numCursos = BigInteger.valueOf((Long) resultados[1]);
            BigInteger numAlumnos = BigInteger.valueOf((Long) resultados[2]);
            centro.setNumCursos((BigInteger) numCursos);
            centro.setNumAlumnos((BigInteger) numAlumnos);

            // Actualizo el centro
            session.update(centro);
        }
    }

    private static int leerEntero(String mensaje, int min, int max) {
        boolean salir = false;
        int numero = 0;

        do {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                sc.nextLine();
                while (numero < min || numero > max) {
                    System.out.print("\tSuperado límite (> " + min + " y < " + max + ")");
                    System.out.print("\n\tOtra vez: ");

                    numero = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                }
                salir = true;

            } catch (InputMismatchException exc) {
                sc.nextLine();
                System.out.print("\n\tIncorrecto, escribe de nuevo: ");
            }
        } while (!salir);

        return numero;
    }// LecturaEntero

}// Class
