package impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AlumnosDAO;
import datos.Alumnos;
import factory.SqlDbDAOFactory;

public class SqlDbAlumnosImpl implements AlumnosDAO {
	/*
	 * private int num_alumno;
	 * private int cod_curso ;
	 * private String nombrecurso;
	 * private String nombre ;
	 * private String direccion;
	 * private String tlf ;
	 * private double nota_media;
	 */
	/*
	 * Devuelven 0 => Si la operación se realizó correctamente.
	 * Devuelven 1 => Si el registro a modificar, eliminar, no existe
	 * Devuelven 2 => Si el registro no se puede eliminar porque tiene notas.
	 * Devuelven -1 => Si ocurre cualquier otro error no controlado, una excepción.
	 */
	/*
	 * En Alumnos:
	 * • No se pueden insertar alumnos con el mismo número de alumno.
	 * • No se puede eliminar un alumno si tiene notas.
	 * • No se pueden modificar un alumno si no existe.
	 * • Un alumno puede cambiar de curso.
	 */
	// Crear la conexión
	Connection conexion;

	public SqlDbAlumnosImpl() {
		conexion = SqlDbDAOFactory.crearConexion();
	}

	@Override
	public int InsertarAlumno(Alumnos alum) {
		int resultado = 0;
		// Comprobar que no existe el alumno
		String sql = "SELECT * FROM alumnos WHERE num_alumno = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, alum.getNum_alumno());
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el alumno, devolver 1
			if (rs.next()) {
				resultado = 1;
			} else {
				// Si no existe, insertar el alumno (implícitamente si no existe no es igual a
				// otro alumno)
				sql = "INSERT INTO alumnos (num_alumno, cod_curso, nombrecurso, nombre, direccion, tlf, nota_media) VALUES (?, ?, ?, ?, ?, ?, ?)";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, alum.getNum_alumno());
				sentencia.setInt(2, alum.getCod_curso());
				sentencia.setString(3, alum.getNombrecurso());
				sentencia.setString(4, alum.getNombre());
				sentencia.setString(5, alum.getDireccion());
				sentencia.setString(6, alum.getTlf());
				sentencia.setDouble(7, alum.getNota_media());
				int filas = sentencia.executeUpdate();
				if (filas > 0) {
					resultado = 0;
				} else {
					resultado = -1;
				}
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
			resultado = -1;
		}
		return resultado;
	}

	@Override
	public int EliminarAlumno(int codigo) {
		int resultado = 0;
		// Comprobar que existe el alumno
		String sql = "SELECT * FROM alumnos WHERE num_alumno = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el alumno, comprobar que no tiene notas
			if (rs.next()) {

				// Si tiene notas, devolver 2
				if (rs.getDouble("nota_media") > 0) {
					resultado = 2;
				} else {
					// Si no tiene notas, eliminar el alumno
					sql = "DELETE FROM alumnos WHERE num_alumno = ?";
					sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, codigo);
					int filas = sentencia.executeUpdate();
					if (filas > 0) {
						resultado = 0;
					} else {
						resultado = -1;
					}
				}
			} else {
				// Si no existe, devolver 1
				resultado = 1;
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
			resultado = -1;
		}
		return resultado;
	}

	@Override
	public int ModificarAlumno(int codigo, Alumnos nuevo) {
		int resultado = 0;
		// Comprobar que existe el alumno
		String sql = "SELECT * FROM alumnos WHERE num_alumno = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el alumno, modificarlo
			if (rs.next()) {
				sql = "UPDATE alumnos SET cod_curso = ?, nombrecurso = ?, nombre = ?, direccion = ?, tlf = ?, nota_media = ? WHERE num_alumno = ?";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, nuevo.getCod_curso());
				sentencia.setString(2, nuevo.getNombrecurso());
				sentencia.setString(3, nuevo.getNombre());
				sentencia.setString(4, nuevo.getDireccion());
				sentencia.setString(5, nuevo.getTlf());
				sentencia.setDouble(6, nuevo.getNota_media());
				sentencia.setInt(7, codigo);
				int filas = sentencia.executeUpdate();
				if (filas > 0) {
					resultado = 0;
				} else {
					resultado = -1;
				}
			} else {
				// Si no existe, devolver 1
				resultado = 1;
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
			resultado = -1;
		}

		return resultado;
	}

	@Override
	public boolean ActualizarDatos() {
		// Calcula el campo nota_media para cada alumno a partir de la tabla
		// evaluaciones
		boolean resultado = false;
		String sql = "SELECT num_alumno, AVG(nota) AS nota_media FROM evaluaciones GROUP BY num_alumno";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				sql = "UPDATE alumnos SET nota_media = ? WHERE num_alumno = ?";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setDouble(1, rs.getDouble("nota_media"));
				sentencia.setInt(2, rs.getInt("num_alumno"));
				int filas = sentencia.executeUpdate();
				if (filas > 0) {
					resultado = true;
				} else {
					resultado = false;
				}
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
			resultado = false;
		}

		return resultado;
	}

	@Override
	public Alumnos ConsultarAlumno(int codigo) {
		Alumnos alumno = new Alumnos();
		String sql = "SELECT * FROM alumnos WHERE num_alumno = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				alumno.setNum_alumno(rs.getInt("num_alumno"));
				alumno.setCod_curso(rs.getInt("cod_curso"));
				alumno.setNombrecurso(rs.getString("nombrecurso"));
				alumno.setNombre(rs.getString("nombre"));
				alumno.setDireccion(rs.getString("direccion"));
				alumno.setTlf(rs.getString("tlf"));
				alumno.setNota_media(rs.getDouble("nota_media"));
			} else {
				// si no existe, devolver un mensaje
				System.out.println("No existe el alumno con código " + codigo);
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
		}
		return alumno;
	}

	@Override
	public ArrayList<Alumnos> TodosLosAlumnos() {
		ArrayList<Alumnos> lista = new ArrayList<Alumnos>();
		String sql = "SELECT * FROM alumnos";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				Alumnos alumno = new Alumnos();
				alumno.setNum_alumno(rs.getInt("num_alumno"));
				alumno.setCod_curso(rs.getInt("cod_curso"));
				alumno.setNombrecurso(rs.getString("nombrecurso"));
				alumno.setNombre(rs.getString("nombre"));
				alumno.setDireccion(rs.getString("direccion"));
				alumno.setTlf(rs.getString("tlf"));
				alumno.setNota_media(rs.getDouble("nota_media"));
				lista.add(alumno);
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
		}
		return lista;
	}

	private void MensajeExcepcion(SQLException e) {
		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje   : %s %n", e.getMessage());
		System.out.printf("SQL estado: %s %n", e.getSQLState());
		System.out.printf("Cód error : %s %n", e.getErrorCode());
	}

}
