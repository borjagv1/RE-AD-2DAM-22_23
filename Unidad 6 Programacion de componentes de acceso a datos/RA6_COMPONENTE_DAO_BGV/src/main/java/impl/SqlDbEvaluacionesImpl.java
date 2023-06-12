package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.EvaluacionesDAO;
import datos.Evaluaciones;
import factory.SqlDbDAOFactory;

public class SqlDbEvaluacionesImpl implements EvaluacionesDAO {
	Connection conexion;

	public SqlDbEvaluacionesImpl() {
		conexion = SqlDbDAOFactory.crearConexion();
	}

	@Override
	public ArrayList<Evaluaciones> DatosTodasLasEvaluaciones() {
		// Método que devuelve un ArrayList con todas las evaluaciones
		ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();
		// Recorrer en MYSQL la tabla de evaluaciones y añadir cada evaluación al
		// ArrayList
		String sql = "SELECT * FROM evaluaciones";
		try {
			java.sql.Statement sentencia = conexion.createStatement();
			// Ejecutar la consulta
			ResultSet rs = sentencia.executeQuery(sql);
			// Recorrer el ResultSet y añadir cada evaluación al ArrayList
			// private int cod_evaluacion;
			// private int num_alumno;
			// private int cod_asig;
			// private double nota;
			// private String nombreasig;
			while (rs.next()) {
				Evaluaciones eva = new Evaluaciones(rs.getInt("cod_evaluacion"), rs.getInt("num_alumno"),
						rs.getInt("cod_asig"), rs.getDouble("nota"), rs.getString("nombreasig"));
				evaluaciones.add(eva);
			}
			sentencia.close();
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}

		return evaluaciones;
	}

	private void MensajeExcepcion(SQLException e) {
		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje   : %s %n", e.getMessage());
		System.out.printf("SQL estado: %s %n", e.getSQLState());
		System.out.printf("Cód error : %s %n", e.getErrorCode());
	}

	@Override
	public ArrayList<Evaluaciones> DatosUnaEvaluacion(int eva) {
		// Método que devuelve un ArrayList con una evaluación dada
		ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();
		// CONSULTA SQL
		String sql = "SELECT * FROM evaluaciones WHERE cod_evaluacion = ?";
		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, eva);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				Evaluaciones ev = new Evaluaciones(rs.getInt("cod_evaluacion"), rs.getInt("num_alumno"),
						rs.getInt("cod_asig"), rs.getDouble("nota"), rs.getString("nombreasig"));
				evaluaciones.add(ev);
			} else
				System.out.printf("Evaluación: %d No existe%n", eva);
			rs.close();// liberar recursos
			sentencia.close();
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}

		return evaluaciones;
	}

	@Override
	public ArrayList<Evaluaciones> EvaluacionesAlumno(int eva, int codigo) {
		//Datos de un alumno en una evaluacion concreta, recibe una evaluación y un 
		//un número de alumno
		ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();
		// CONSULTA SQL
		String sql = "SELECT * FROM evaluaciones WHERE cod_evaluacion = ? AND num_alumno = ?";
		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, eva);
			sentencia.setInt(2, codigo);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				Evaluaciones ev = new Evaluaciones(rs.getInt("cod_evaluacion"), rs.getInt("num_alumno"),
						rs.getInt("cod_asig"), rs.getDouble("nota"), rs.getString("nombreasig"));
				evaluaciones.add(ev);
			} else
				System.out.printf("Evaluación: %d No existe%n", eva);
			rs.close();// liberar recursos
			sentencia.close();
		} catch (SQLException e) {
			MensajeExcepcion(e);
		}
		return evaluaciones;
	}

}
