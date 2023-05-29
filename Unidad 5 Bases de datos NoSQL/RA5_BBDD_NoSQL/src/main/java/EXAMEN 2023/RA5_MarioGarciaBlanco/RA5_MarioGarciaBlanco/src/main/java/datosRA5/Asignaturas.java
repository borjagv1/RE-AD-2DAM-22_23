
package datosRA5;

/**
 *
 * @author mjrm2
 */
public class Asignaturas {
	private int cod_asig;
	private String nombre;

	public Asignaturas(int cod_asig, String nombre) {
		this.cod_asig = cod_asig;
		this.nombre = nombre;

	}

	public Asignaturas() {
	}

	public int getCod_asig() {
		return cod_asig;
	}

	public void setCod_asig(int cod_asig) {
		this.cod_asig = cod_asig;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Asignaturas[" + cod_asig + ", " + nombre + ']';
	}

}
