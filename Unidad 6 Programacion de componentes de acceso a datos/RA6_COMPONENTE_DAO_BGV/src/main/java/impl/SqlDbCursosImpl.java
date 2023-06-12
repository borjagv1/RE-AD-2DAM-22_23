package impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.CursosDAO;
import datos.Cursos;
import factory.SqlDbDAOFactory;

public class SqlDbCursosImpl implements CursosDAO {
	/*
	 * private int cod_curso;
	 * private String denominacion;
	 * private int num_alumnos;
	 * private double nota_media;
	 */
	/*
	 * Devuelven 0 => Si la operación se realizó correctamente.
	 * Devuelven 1 => Si el registro a modificar, eliminar, no existe
	 * Devuelven 2 => Si el registro no se puede eliminar porque tiene notas.
	 * Devuelven -1 => Si ocurre cualquier otro error no controlado, una excepción.
	 */
	/*
	 * En Cursos:
	 * • No se pueden insertar cursos con el mismo código.
	 * • No se puede eliminar un curso si tiene alumnos.
	 * • No se pueden modificar un curso si no existe.
	 */
	Connection conexion;

	public SqlDbCursosImpl() {
		conexion = SqlDbDAOFactory.crearConexion();
	}

	@Override
	public int InsertarCurso(Cursos cur) {
		int resultado = 0;
		// Comprobar que no existe el curso
		String sql = "SELECT * FROM cursos WHERE cod_curso = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, cur.getCod_curso());
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el curso, devolver 1
			if (rs.next()) {
				resultado = 1;
			} else {
				// Si no existe, insertar el curso (implícitamente si no existe no es igual a
				// otro curso)
				sql = "INSERT INTO cursos (cod_curso, denominacion, num_alumnos, nota_media) VALUES (?, ?, ?, ?)";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, cur.getCod_curso());
				sentencia.setString(2, cur.getDenominacion());
				sentencia.setInt(3, cur.getNum_alumnos());
				sentencia.setDouble(4, cur.getNota_media());
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
	public int EliminarCurso(int codigo) {
		int resultado = 0;
		// Comprobar que existe el curso
		String sql = "SELECT * FROM cursos WHERE cod_curso = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el curso, comprobar que no tiene notas
			if (rs.next()) {
				// Si tiene alumnos, devolver 2
				if (rs.getInt("num_alumnos") > 0) {
					resultado = 2;
				} else {
					// Si no tiene alumnos, eliminar el curso
					sql = "DELETE FROM cursos WHERE cod_curso = ?";
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
	public int ModificarCurso(int codigo, Cursos nuevo) {
		int resultado = 0;
		// Comprobar que existe el curso
		String sql = "SELECT * FROM cursos WHERE cod_curso = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el curso, modificarlo
			if (rs.next()) {
				sql = "UPDATE cursos SET denominacion = ?, num_alumnos = ?, nota_media = ? WHERE cod_curso = ?";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setString(1, nuevo.getDenominacion());
				sentencia.setInt(2, nuevo.getNum_alumnos());
				sentencia.setDouble(3, nuevo.getNota_media());
				sentencia.setInt(4, codigo);
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
		// COMPROBAR CONSULTAS
		/*
		 * private int num_alumno;
		 * private int cod_curso ;
		 * private String nombrecurso;
		 * private String nombre ;
		 * private String direccion;
		 * private String tlf ;
		 * private double nota_media // inicialmente 0;
		 */
		/*
		 * private int cod_evaluacion;
		 * private int num_alumno;
		 * private int cod_asig;
		 * private double nota;
		 * private String nombreasig;
		 */
		// actualiza los campos num_alumnos y nota_media de Cursos inicialmente 0.
		// coger los datos de las tablas Alumnos y Evaluaciones
		boolean resultado = false;
		// Obtengo los datos de num_alumnos en los cursos a partir de la tabla alumnos
		String sql = "SELECT cod_curso, count(*) as num_alumnos FROM alumnos GROUP BY cod_curso";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();
			// Recorrer el resultado
			while (rs.next()) {
				// Actualizar el curso
				sql = "UPDATE cursos SET num_alumnos = ? WHERE cod_curso = ?";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, rs.getInt("num_alumnos"));
				sentencia.setInt(2, rs.getInt("cod_curso"));
				int filas = sentencia.executeUpdate();
				if (filas > 0) {
					resultado = true;
				} else {
					resultado = false;
				}
			}
			// Obtengo los datos de nota_media en los cursos a partir de la tabla evaluaciones
			sql = "SELECT num_alumno, avg(nota) as nota_media FROM evaluaciones GROUP BY num_alumno";
			sentencia = conexion.prepareStatement(sql);
			// Ejecutar la consulta
			rs = sentencia.executeQuery();
			// Recorrer el resultado
			while (rs.next()) {
				// Actualizar el curso
				sql = "UPDATE cursos SET nota_media = ? WHERE num_alumno = ?";
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
	public Cursos ConsultarCurso(int codigo) {
		String sql = "SELECT * FROM cursos WHERE cod_curso = ?";
		Cursos curso = new Cursos();
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();
			// Si existe el curso, devolverlo
			if (rs.next()) {
				curso.setCod_curso(rs.getInt("cod_curso"));
				curso.setDenominacion(rs.getString("denominacion"));
				curso.setNum_alumnos(rs.getInt("num_alumnos"));
				curso.setNota_media(rs.getDouble("nota_media"));
			} else {
				// Si no existe, devolver null
				curso = null;
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
			curso = null;
		}
		return curso;
	}

	@Override
	public ArrayList<Cursos> TodosLosCursos() {
		ArrayList<Cursos> cursos = new ArrayList<Cursos>();
		String sql = "SELECT * FROM cursos";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();
			// Recorrer el resultado
			while (rs.next()) {
				Cursos curso = new Cursos();
				curso.setCod_curso(rs.getInt("cod_curso"));
				curso.setDenominacion(rs.getString("denominacion"));
				curso.setNum_alumnos(rs.getInt("num_alumnos"));
				curso.setNota_media(rs.getDouble("nota_media"));
				cursos.add(curso);
			}
			sentencia.close();
		} catch (java.sql.SQLException e) {
			MensajeExcepcion(e);
			cursos = null;
		}
		return cursos;
	}

	private void MensajeExcepcion(SQLException e) {
		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje   : %s %n", e.getMessage());
		System.out.printf("SQL estado: %s %n", e.getSQLState());
		System.out.printf("Cód error : %s %n", e.getErrorCode());
	}

}
