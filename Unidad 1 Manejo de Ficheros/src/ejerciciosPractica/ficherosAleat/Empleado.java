package ejerciciosPractica.ficherosAleat;
import java.io.Serializable;

public class Empleado implements Serializable {
	private int empno;        //numero de empleado
	private String apellido;  //apellido del empleado
	private String oficio;    //oficio del empleado
	private Float salario;    //salario del empleado
	private int deptno;       //n�mero de departamento
	private String nombreDepartamento; //nombre del departamento
	
	
	public Empleado(int empno, String apellido, String oficio, Float salario, int deptno, String nombreDepartamento) {
		super();
		this.empno = empno;
		this.apellido = apellido;
		this.oficio = oficio;
		this.salario = salario;
		this.deptno = deptno;
		this.nombreDepartamento = nombreDepartamento;
	}
	public Empleado() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getOficio() {
		return oficio;
	}
	public void setOficio(String oficio) {
		this.oficio = oficio;
	}
	public Float getSalario() {
		return salario;
	}
	public void setSalario(Float salario) {
		this.salario = salario;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	

}
