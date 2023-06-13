package impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import dao.AlumnosDAO;
import datos.Alumnos;
import datos.Evaluaciones;
import factory.NeodatisDAOFactory;

public class NeodatisAlumnosImpl implements AlumnosDAO {

	static ODB bd = null;

	public NeodatisAlumnosImpl() {
		bd = NeodatisDAOFactory.crearConexion();
	}

	// añade un nuevo alumno
	@Override
	public int InsertarAlumno(Alumnos alum) {
		int resultado = 0;

		// Comprobar que no existe el alumno
		Objects<Alumnos> objects = bd
				.getObjects(new CriteriaQuery(Alumnos.class, Where.equal("num_alumno", alum.getNum_alumno())));
		if (objects.size() > 0) {
			resultado = 1;
			System.out.println("El alumno ya existe: " + alum.getNum_alumno());
		} else {
			// Si no existe, insertar el alumno
			bd.store(alum);
			bd.commit();
		}

		return resultado;
	}
	// Elimina al alumno con el código indicado
	@Override
	public int EliminarAlumno(int codigo) {
		int resultado = 0;

		// Comprobar que existe el alumno
		Objects<Alumnos> objects = bd.getObjects(new CriteriaQuery(Alumnos.class, Where.equal("num_alumno", codigo)));
		if (objects.size() > 0) {
			Alumnos alumno = objects.getFirst();

			// Si tiene notas, devolver 2
			if (alumno.getNota_media() != 0) {
				resultado = 2;
				System.out.println("El alumno tiene notas: " + codigo);
			} else {
				// Si no tiene notas, eliminar el alumno
				bd.delete(alumno);
				bd.commit();
			}
		} else {
			// Si no existe, devolver 1
			resultado = 1;
		}

		return resultado;
	}

	// Modifico el alumno con el código indicado
	@Override
	public int ModificarAlumno(int codigo, Alumnos nuevo) {
		int resultado = 0;

		// Comprobar que existe el alumno
		Objects<Alumnos> objects = bd.getObjects(new CriteriaQuery(Alumnos.class, Where.equal("num_alumno", codigo)));
		if (objects.size() > 0) {
			Alumnos alumno = objects.getFirst();

			// Modificar el alumno
			alumno.setCod_curso(nuevo.getCod_curso());
			alumno.setNombre(nuevo.getNombre());
			alumno.setDireccion(nuevo.getDireccion());
			alumno.setTlf(nuevo.getTlf());
			alumno.setNota_media(nuevo.getNota_media());

			bd.store(alumno);
			bd.commit();
		} else {
			// Si no existe, devolver 1
			resultado = 1;
			System.out.println("El alumno no existe: " + codigo);
		}

		return resultado;
	}
	
	 @Override
    public boolean ActualizarDatos() {
        boolean resultado = false;
        
        Objects<Evaluaciones> evaluaciones = bd.getObjects(Evaluaciones.class);
        
        // Calcular la nota_media para cada alumno
        Map<Integer, List<Double>> notasPorAlumno = new HashMap<>();
        while (evaluaciones.hasNext()) {
            Evaluaciones evaluacion = evaluaciones.next();
            int num_alumno = evaluacion.getNum_alumno();
            double nota = evaluacion.getNota();
            
            if (!notasPorAlumno.containsKey(num_alumno)) {
                notasPorAlumno.put(num_alumno, new ArrayList<>());
            }
            notasPorAlumno.get(num_alumno).add(nota);
        }
        
        // Actualizar la nota_media de cada alumno en la base de datos
        for (Map.Entry<Integer, List<Double>> entry : notasPorAlumno.entrySet()) {
            int num_alumno = entry.getKey();
            List<Double> notas = entry.getValue();
            
            double nota_media = calcularNotaMedia(notas);
            
            Objects<Alumnos> objects = bd.getObjects(new CriteriaQuery(Alumnos.class, Where.equal("num_alumno", num_alumno)));
            if (objects.size() > 0) {
                Alumnos alumno = objects.getFirst();
                alumno.setNota_media(nota_media);
                bd.store(alumno);
                resultado = true;
            }
        }
        
        bd.commit();
        return resultado;
    }
    
	private double calcularNotaMedia(List<Double> notas) {
		double suma = 0;
		for (Double nota : notas) {
			suma += nota;
		}
		return suma / notas.size();
	}
	
	    @Override
    public Alumnos ConsultarAlumno(int codigo) {
        Objects<Alumnos> objects = bd.getObjects(new CriteriaQuery(Alumnos.class, Where.equal("num_alumno", codigo)));
        
        if (objects.size() > 0) {
            return objects.getFirst();
        } else {
            System.out.println("No existe el alumno con código " + codigo);
            return null;
        }
    }
    
    @Override
    public ArrayList<Alumnos> TodosLosAlumnos() {
        Objects<Alumnos> objects = bd.getObjects(Alumnos.class);
        ArrayList<Alumnos> lista = new ArrayList<>();
        
        while (objects.hasNext()) {
            Alumnos alumno = objects.next();
            lista.add(alumno);
        }
        
        return lista;
    }
    

}
