
package datos;


public class Cursos {

    private int cod_curso;
    private String denominacion;
    private int num_alumnos;
    private double nota_media;

    public Cursos(int cod_curso, String denominacion, int num_alumnos, double nota_media) {
        this.cod_curso = cod_curso;
        this.denominacion = denominacion;
        this.num_alumnos = num_alumnos;
        this.nota_media = nota_media;
    }

    public Cursos() {
    }

    public int getCod_curso() {
        return cod_curso;
    }

    public void setCod_curso(int cod_curso) {
        this.cod_curso = cod_curso;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public int getNum_alumnos() {
        return num_alumnos;
    }

    public void setNum_alumnos(int num_alumnos) {
        this.num_alumnos = num_alumnos;
    }

    public double getNota_media() {
        return nota_media;
    }

    public void setNota_media(double nota_media) {
        this.nota_media = nota_media;
    }

    @Override
    public String toString() {
        return "Cursos[" + cod_curso + ", " + denominacion + ", " + num_alumnos + ", " + nota_media + ']';
    }

}
