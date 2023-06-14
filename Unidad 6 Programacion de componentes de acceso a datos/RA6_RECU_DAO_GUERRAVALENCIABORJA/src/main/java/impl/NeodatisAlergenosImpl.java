package impl;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.*;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;

import dao.AlergenosDAO;
import datos.Alergenos;
import datos.AlergenosProductos;
import datos.Productos;
import factory.NeodatisDAOFactory;

public class NeodatisAlergenosImpl implements AlergenosDAO {
    static ODB bd = null;

    public NeodatisAlergenosImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public int InsertarAlergeno(Alergenos c) {
        int resultado = 0;

        // Comprueba si el alérgeno ya existe en la base de datos
        IQuery query = new CriteriaQuery(Alergenos.class, Where.equal("id", c.getId()));
        Objects<Alergenos> alergenosExistentes = bd.getObjects(query);

        if (!alergenosExistentes.isEmpty()) {
            System.out.println("Ya existe el alérgeno con id: " + c.getId() + ". No se insertará.");
            resultado = 1;
        } else {
            // Inserta el alérgeno en la base de datos.
            try {
                bd.store(c);
                System.out.println("Se ha insertado el alérgeno con id: " + c.getId());
                resultado = 0;
            } catch (Exception e) {
                System.out.println("No se ha podido insertar el alérgeno con id: " + c.getId());
                resultado = 1;
            }
        }

        return resultado;
    }

    @Override
    public int EliminarAlergeno(int id) {
        int resultado = 0;
        // Comprueba si el alérgeno existe en la TABLA ALERGENOS
        Objects<Alergenos> objects = bd.getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                Alergenos.class, org.neodatis.odb.core.query.criteria.Where.equal("id", id)));
        // Comprobamos si el alérgeno existe.
        // Si no existe, resultado = 1.
        // Si existe, comprobamos si tiene productos.
        // Si tiene productos, resultado = 1.
        // Si no tiene productos, eliminamos el alérgeno.
        if (objects.size() > 0) {
            Alergenos alergeno = objects.getFirst();
            if (alergeno.getNumproductos() > 0) {
                System.out.println("El alérgeno tiene productos. No se eliminará.");
                resultado = 1;
            } else {
                bd.delete(alergeno);
                bd.commit();
                System.out.println("Alérgeno " + id + " eliminado correctamente.");
                resultado = 0;
            }
        } else {
            System.out.println("No existe el alérgeno con id: " + id + ". No se eliminará.");
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        // actualiza el campo numproductos y nombreproductos
        // de la colección alergenos con los datos de la colección productos
        // Devuelve true si se ha actualizado correctamente.
        // Devuelve false si no se ha podido actualizar.
        boolean resultado = false;
        Objects<Alergenos> objects = bd
                .getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(Alergenos.class));
        if (objects.size() > 0) {
            for (Alergenos alergeno : objects) {
                // actualiza el campo numproductos
                // cuento los id_products que coincidan con el id_alergeno en la TABLA
                // ALERGENOSPRODUCTOS
                int alergenoid = alergeno.getId();
                Objects<AlergenosProductos> objects2 = bd
                        .getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                                AlergenosProductos.class,
                                org.neodatis.odb.core.query.criteria.Where.equal("idalergeno", alergenoid)));

                alergeno.setNumproductos(objects2.size());

                // actualiza el campo nombreproductos
                String nombreproductos = "";
                StringBuilder sb = new StringBuilder();
                for (AlergenosProductos alergenoproducto : objects2) {
                    // añado el nombre del producto a la cadena separado por comas
                    int idproducto = alergenoproducto.getIdproduct();
                    Objects<Productos> objects3 = bd
                            .getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                                    Productos.class,
                                    org.neodatis.odb.core.query.criteria.Where.equal("id", idproducto)));
                    Productos producto = objects3.getFirst();
                    String nombreproducto = producto.getNombre();
                    sb.append(nombreproducto).append(",");
                }
                // for (Productos producto : objects2) {
                // // añado el nombre del producto a la cadena separado por comas
                // String nombreproducto = producto.getNombre();
                // sb.append(nombreproducto).append(",");
                // }
                nombreproductos = sb.toString();
                if (nombreproductos.length() > 0) {
                    nombreproductos = nombreproductos.substring(0, nombreproductos.length() - 1);
                    alergeno.setNombreproductos(nombreproductos);

                }

                bd.store(alergeno);
                bd.commit();
            }
            resultado = true;
        } else {
            System.out.println("No existen alérgenos. No se actualizarán los datos.");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public boolean InsertarAlergenoProducto(int idalergeno, int idproducto) {
        boolean resultado = false;
        // Comprueba si el alérgeno existe en la TABLA ALERGENOS
        Objects<Alergenos> objects = bd.getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                Alergenos.class, org.neodatis.odb.core.query.criteria.Where.equal("id", idalergeno)));
        // Comprobamos si el producto existe en la TABLA PRODUCTOS
        Objects<Productos> objects2 = bd.getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                Productos.class, org.neodatis.odb.core.query.criteria.Where.equal("id", idproducto)));
        // Comprobamos si existe el registro alergeno en la TABLA ALERGENOSPRODUCTOS
        Objects<AlergenosProductos> objects3 = bd
                .getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                        AlergenosProductos.class, org.neodatis.odb.core.query.criteria.Where.and()
                                .add(org.neodatis.odb.core.query.criteria.Where.equal("idalergeno", idalergeno))
                                .add(org.neodatis.odb.core.query.criteria.Where.equal("idproduct", idproducto))));

        // si todo es correcto, insertamos el registro en la TABLA ALERGENOSPRODUCTOS
        if (objects.size() > 0 && objects2.size() > 0 && objects3.size() == 0) {
            AlergenosProductos alergeno = new AlergenosProductos(idalergeno, idproducto);
            bd.store(alergeno);
            bd.commit();
            System.out.println("Registro insertado correctamente.");
            resultado = true;
        } else {
            System.out.println("No se ha podido insertar el registro.");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public Alergenos ConsultarAlergeno(int id) {
        Objects<Alergenos> objects = bd.getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(
                Alergenos.class, org.neodatis.odb.core.query.criteria.Where.equal("id", id)));
        if (objects.size() > 0) {
            return objects.getFirst();
        } else {
            System.out.println("No existe el alérgeno con id: " + id + ".");
            return null;
        }
    }

    @Override
    public ArrayList<Alergenos> TodosLosAlergenos() {
        Objects<Alergenos> objects = bd
                .getObjects(new org.neodatis.odb.impl.core.query.criteria.CriteriaQuery(Alergenos.class));
        ArrayList<Alergenos> alergenos = new ArrayList<Alergenos>();
        while (objects.hasNext()) {
            Alergenos alergeno = objects.next();
            alergenos.add(alergeno);
        }
        return alergenos;
    }

}
