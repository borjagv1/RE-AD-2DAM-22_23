package impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import dao.CursosDAO;
import datos.Alumnos;
import datos.Cursos;
import factory.NeodatisDAOFactory;

public class NeodatisCursosImpl implements CursosDAO{



	static ODB bd = null;

    public NeodatisCursosImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    /*  Devuelven 0 => Si la operación se realizó correctamente.
        Devuelven 1 => Si el registro a modificar, eliminar, no existe
        Devuelven 2 => Si el registro no se puede eliminar porque tiene notas.
        Devuelven -1 => Si ocurre cualquier otro error no controlado, una excepción.
     */
    
    //añade un nuevo curso
    @Override
    public int InsertarCurso(Cursos cur) {
        int control = -1;
        try {
            IQuery queryA = new CriteriaQuery(Cursos.class, Where.equal("cod_curso", cur.getCod_curso()));
            Cursos c = (Cursos) bd.getObjects(queryA).getFirst();
            System.out.println("El curso con  cod_curso " + c.getCod_curso() + " ya existe");
            control = 1;
        } catch (IndexOutOfBoundsException e) {
            bd.store(cur);
            bd.commit();
            System.out.printf("Curso %s insertado%n", cur.getCod_curso());
            control = 0;
        }
        return control;
    }

    //elimina el curso usando el cod_curso
    @Override
    public int EliminarCurso(int codigo) {
        int control = -1;
        try {
            IQuery queryA = new CriteriaQuery(Cursos.class, Where.equal("cod_curso", codigo));
            Cursos c = (Cursos) bd.getObjects(queryA).getFirst();
            if (ComprobarAlumnos(codigo)) {
                System.out.println("El curso tiene alumnos");
                control = 2;
            } else {
                bd.delete(c);
                control = 0;
                System.out.printf("Curso %d eliminado%n", codigo);
                bd.commit();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El curso no existe");
            control = 1;
        }
        return control;
    }

    //modifica el curso usando el cod_curso y un objeto cursos
    @Override
    public int ModificarCurso(int codigo, Cursos nuevo) {
        int control = -1;
        try {
            IQuery queryA = new CriteriaQuery(Cursos.class, Where.equal("cod_curso", codigo));
            Cursos c = (Cursos) bd.getObjects(queryA).getFirst();
            c.setCod_curso(codigo);
            c.setDenominacion(nuevo.getDenominacion());
            c.setNum_alumnos(nuevo.getNum_alumnos());
            c.setNota_media(nuevo.getNota_media());
            bd.store(c);
            control = 0;
            bd.commit();
            System.out.printf("Curso %d actualizado%n", codigo);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El curso no existe");
            control = 1;
        }
        return control;
    }

    //actualiza los campos num_alumnos y nota_media de Cursos
    @Override
    public boolean ActualizarDatos() {
        boolean valor = false;
        if (actualizarNota_media() == true && actualizarNum_alumnos() == true) {
            System.out.println("Actualizacion terminada");
            valor = true;
        }
        /*actualizarNota_media();
        actualizarNum_alumnos();*/
        return valor;
    }

    //consulta un curso usando el cod_curso
    @Override
    public Cursos ConsultarCurso(int codigo) {
        Cursos cur = null;
        try {
            IQuery queryA = new CriteriaQuery(Cursos.class, Where.equal("cod_curso", codigo));
            cur = (Cursos) bd.getObjects(queryA).getFirst();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El curso no existe");
        }
        return cur;
    }

    //consulta todos los cursos
    @Override
    public ArrayList<Cursos> TodosLosCursos() {
        ArrayList<Cursos> curso = new ArrayList<>();
        IQuery query = new CriteriaQuery(Cursos.class).orderByAsc("cod_curso");
        Objects<Cursos> objects = bd.getObjects(query);
        for (Cursos c : objects) {
            curso.add(c);
        }
        return curso;
    }

    private boolean ComprobarAlumnos(int codigo) {
        boolean valor = false;
        IQuery query = new CriteriaQuery(Alumnos.class, Where.equal("cod_curso", codigo));
        Objects<Alumnos> alumnos = bd.getObjects(query);
        if (!alumnos.isEmpty()) {
            valor = true;
        }

        return valor;
    }

    private boolean actualizarNota_media() {
        boolean valor = false;
        Values val2 = bd.getValues(new ValuesCriteriaQuery(Alumnos.class).field("cod_curso").count("nota_media").sum("nota_media")
                .groupBy("cod_curso"));
        if (val2.size() == 0) {
            System.out.println("La consulta no devuelve datos.");
        } else {
            while (val2.hasNext()) {
                ObjectValues objetos = (ObjectValues) val2.next();
                IQuery queryA = new CriteriaQuery(Cursos.class, Where.equal("cod_curso", objetos.getByAlias("cod_curso")));
                Cursos c = (Cursos) bd.getObjects(queryA).getFirst();
                BigInteger numn = (BigInteger) objetos.getByIndex(1);
                BigDecimal sumn = (BigDecimal) objetos.getByIndex(2);
                double media = sumn.doubleValue()/numn.intValue();
                c.setNota_media(media);
                bd.store(c);
                valor = true;
                bd.commit();
                System.out.printf("Alumno %d actualizado%n", objetos.getByAlias("cod_curso"));
            }
        }
        return valor;
    }

    private boolean actualizarNum_alumnos() {
        boolean valor = false;
        Values val2 = bd.getValues(new ValuesCriteriaQuery(Alumnos.class).field("cod_curso").count("num_alumno")
                .groupBy("cod_curso"));
        if (val2.size() == 0) {
            System.out.println("La consulta no devuelve datos.");
        } else {
            while (val2.hasNext()) {
                ObjectValues objetos = (ObjectValues) val2.next();
                IQuery queryA = new CriteriaQuery(Cursos.class, Where.equal("cod_curso", objetos.getByAlias("cod_curso")));
                Cursos c = (Cursos) bd.getObjects(queryA).getFirst();
                BigInteger numn = (BigInteger) objetos.getByIndex(1);
                int num_alumnos = numn.intValue();
                c.setNum_alumnos(num_alumnos);
                bd.store(c);
                valor = true;
                bd.commit();
                System.out.printf("Alumno %d actualizado%n", objetos.getByAlias("cod_curso"));
            }
        }
        return valor;
    }
}
