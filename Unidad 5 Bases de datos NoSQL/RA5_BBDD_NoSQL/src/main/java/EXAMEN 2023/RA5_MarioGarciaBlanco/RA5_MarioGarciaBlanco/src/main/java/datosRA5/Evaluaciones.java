
package datosRA5;

/**
 *
 * @author mjrm2
 */
public class Evaluaciones {

    private int cod_evaluacion;
    private int num_alumno;
    private int cod_asig;
    private double nota;
    private String nombreasig;

    public Evaluaciones() {
    }

    public Evaluaciones(int cod_evaluacion, int num_alumno, int cod_asig, double nota, String nombreasig) {
        this.cod_evaluacion = cod_evaluacion;
        this.num_alumno = num_alumno;
        this.cod_asig = cod_asig;
        this.nota = nota;
        this.nombreasig = nombreasig;
    }

    public Evaluaciones(int cod_evaluacion, int num_alumno, int cod_asig, double nota) {
        this.cod_evaluacion = cod_evaluacion;
        this.num_alumno = num_alumno;
        this.cod_asig = cod_asig;
        this.nota = nota;
    }

    public int getCod_evaluacion() {
        return cod_evaluacion;
    }

    public void setCod_evaluacion(int cod_evaluacion) {
        this.cod_evaluacion = cod_evaluacion;
    }

    public int getNum_alumno() {
        return num_alumno;
    }

    public void setNum_alumno(int num_alumno) {
        this.num_alumno = num_alumno;
    }

    public int getCod_asig() {
        return cod_asig;
    }

    public void setCod_asig(int cod_asig) {
        this.cod_asig = cod_asig;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getNombreasig() {
        return nombreasig;
    }

    public void setNombreasig(String nombreasig) {
        this.nombreasig = nombreasig;
    }

    @Override
    public String toString() {
        return "Evaluaciones[" + cod_evaluacion + ", " + num_alumno + ", " + cod_asig + ", " + nota + ", " + nombreasig + ']';
    }
    
    
    
}
