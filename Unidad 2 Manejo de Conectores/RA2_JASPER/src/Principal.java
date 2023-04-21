import java.util.Map;

import javax.naming.CommunicationException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Principal {

	public static void main(String[] args) {
		String reportSource = "./plantilla/plantilla.jrxml";
		String reportHTML = "./informes/Informe.html";
		String reportPDF = "./informes/Informe.pdf";
		String reportXML = "./informes/Informe.xml";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("titulo", "RESUMEN DATOS DE DEPARTAMENTOS.");
		params.put("autor", "ARM");
		params.put("fecha", (new java.util.Date()).toString());

		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");
			JasperPrint MiInforme = JasperFillManager.fillReport(jasperReport, params, conn);
			// Visualizar en pantalla
			JasperViewer.viewReport(MiInforme);
			// Convertir a HTML
			JasperExportManager.exportReportToHtmlFile(MiInforme, reportHTML);
			// Convertir a PDF
			JasperExportManager.exportReportToPdfFile(MiInforme, reportPDF);
			// Convertir a XML, false es para indicar que no hay im�genes
			// (isEmbeddingImages)
			JasperExportManager.exportReportToXmlFile(MiInforme, reportXML, false);
			System.out.println("ARCHIVOS CREADOS");
		} catch (CommunicationsException c) {
			System.out.println(" Error de comunicación con la BD. No está arrancada.");
		} catch (ClassNotFoundException e) {
			System.out.println(" Error driver. ");
		} catch (SQLException e) {
			System.out.println(" Error al ejecutar sentencia SQL ");
		} catch (JRException ex) {
			System.out.println(" Error Jasper.");
			ex.printStackTrace();
		}
	}

}
