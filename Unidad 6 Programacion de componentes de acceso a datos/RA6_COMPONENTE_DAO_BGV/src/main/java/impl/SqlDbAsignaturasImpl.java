package impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AsignaturasDAO;
import datos.Asignaturas;
import factory.SqlDbDAOFactory;

public class SqlDbAsignaturasImpl implements AsignaturasDAO {

	/*
	 * private int cod_asig;
	 * private String nombre;
	 */
	/*
	 * Devuelven 0 => Si la operación se realizó correctamente.
	 * Devuelven 1 => Si el registro a modificar, eliminar, no existe
	 * Devuelven 2 => Si el registro no se puede eliminar porque tiene notas.
	 * Devuelven -1 => Si ocurre cualquier otro error no controlado, una excepción.
	 */
	/*
	 * En Asignaturas:
	 * • No se pueden insertar asignaturas con el mismo código.
	 * • No se puede eliminar una asignatura si tiene notas.
	 * • No se pueden modificar una asignatura si no existe.
	 */
	// Creo la conexión
	Connection conexion;

	public SqlDbAsignaturasImpl() {
		conexion = SqlDbDAOFactory.crearConexion();
	}

	@Override
	public int InsertarAsignatura(Asignaturas asig) {
		int resultado = 0;
		// Comprobar que no existe la asignatura
		String sql = "SELECT * FROM asignaturas WHERE cod_asig = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, asig.getCod_asig());
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe la asignatura, devolver 1
			if (rs.next()) {
				resultado = 1;
			} else {
				// Si no existe, insertar la asignatura (implícitamente si no existe no es igual
				// a
				// otra asignatura)
				sql = "INSERT INTO asignaturas (cod_asig, nombre) VALUES (?, ?)";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, asig.getCod_asig());
				sentencia.setString(2, asig.getNombre());
				int filas = sentencia.executeUpdate();
				if (filas > 0) {
					resultado = 0;
				} else {
					resultado = -1;
				}
			}
			sentencia.close();
		} catch (SQLException e) {
			MensajeExcepcion(e);
			resultado = -1;
		}
		return resultado;
	}

	@Override
	public int EliminarAsignatura(int asig) {
		int resultado = 0;
		// Comprobar que existe la asignatura
		String sql = "SELECT * FROM asignaturas WHERE cod_asig = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, asig);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe la asignatura, comprobar que no tiene notas
			if (rs.next()) {
				// Si tiene notas, devolver 2
				sql = "SELECT * FROM Evaluaciones WHERE cod_asig = ?";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, asig);
				// Ejecutar la consulta
				rs = sentencia.executeQuery();
				if (rs.next()) {
					resultado = 2;
				} else {
					// Si no tiene notas, eliminar la asignatura
					sql = "DELETE FROM asignaturas WHERE cod_asig = ?";
					sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, asig);
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
		} catch (SQLException e) {
			MensajeExcepcion(e);
			resultado = -1;
		}
		return resultado;
	}

	@Override
	public int ModificarAsignatura(int asig, Asignaturas nuevo) {
		int resultado = 0;
		// Comprobar que existe la asignatura
		String sql = "SELECT * FROM asignaturas WHERE cod_asig = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, asig);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe el curso, modificarlo
			if (rs.next()) {
				sql = "UPDATE asignaturas SET nombre = ? WHERE cod_asig = ?";
				sentencia = conexion.prepareStatement(sql);
				sentencia.setString(1, nuevo.getNombre());
				sentencia.setInt(2, asig);
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
		} catch (SQLException e) {
			MensajeExcepcion(e);
			resultado = -1;
		}
		return resultado;
	}

	@Override
	public boolean ActualizarAsignatura() {
		boolean resultado = false;

		return resultado;
	}

	@Override
	public Asignaturas ConsultarAsignatura(int codigo) {
		Asignaturas asig = new Asignaturas();
		String sql = "SELECT * FROM asignaturas WHERE cod_asig = ?";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Si existe la asignatura, devolverla
			if (rs.next()) {
				asig.setCod_asig(rs.getInt("cod_asig"));
				asig.setNombre(rs.getString("nombre"));
			}else {
				System.out.println("No existe la asignatura" + codigo);
			}
			sentencia.close();
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}
		return asig;
	}

	@Override
	public ArrayList<Asignaturas> TodasLasAsignaturas() {
		ArrayList<Asignaturas> lista = new ArrayList<Asignaturas>();
		String sql = "SELECT * FROM asignaturas";
		try {
			java.sql.PreparedStatement sentencia = conexion.prepareStatement(sql);
			// Ejecutar la consulta
			java.sql.ResultSet rs = sentencia.executeQuery();

			// Recorrer el resultado
			while (rs.next()) {
				Asignaturas asig = new Asignaturas();
				asig.setCod_asig(rs.getInt("cod_asig"));
				asig.setNombre(rs.getString("nombre"));
				lista.add(asig);
			}
			sentencia.close();
		} catch (SQLException e) {
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
