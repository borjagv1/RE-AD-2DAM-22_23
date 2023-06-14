package impl;

import java.util.ArrayList;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.CategoriasDAO;
import datos.Categorias;
import datos.Productos;
import factory.NeodatisDAOFactory;

public class NeodatisCategoriasImpl implements CategoriasDAO {
    static ODB bd = null;

    public NeodatisCategoriasImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public int InsertarCategoria(Categorias c) {
        int resultado = 0;

        // Comprobar si existe la categoría
        IQuery consulta = new CriteriaQuery(Categorias.class, Where.equal("_id", c.getId()));
        Objects<Categorias> resultados = bd.getObjects(consulta);
        if (resultados.hasNext()) {
            System.out.println("Ya existe una categoría con ese id: " + c.getId() + ". No se insertará...");
            resultado = 1;
        } else {
            try {
                bd.store(c);
                bd.commit();
                System.out.println("Categoría: " + c.getId() + " insertada correctamente");
            } catch (Exception e) {
                System.out.println("Error al insertar la categoría con id: " + c.getId());
                resultado = 1;
            }
        }

        return resultado;
    }

    @Override
    public int EliminarCategoria(int id) {
        int resultado = 0;

        // Compruebo si existe la categoría
        IQuery consultaCategoria = new CriteriaQuery(Categorias.class, Where.equal("_id", id));
        Objects<Categorias> resultadosCategoria = bd.getObjects(consultaCategoria);
        if (resultadosCategoria.hasNext()) {
            // Compruebo si tiene productos asociados
            IQuery consultaProductos = new CriteriaQuery(Productos.class, Where.equal("idcategoria", id));
            Objects<Productos> resultadosProductos = bd.getObjects(consultaProductos);
            if (resultadosProductos.hasNext()) {
                System.out.println("La categoría con id: " + id + " tiene productos asociados. No se eliminará...");
                resultado = 1;
            } else {
                // Elimino la categoría
                IQuery eliminacionCategoria = new CriteriaQuery(Categorias.class, Where.equal("_id", id));
                bd.delete(eliminacionCategoria);
                bd.commit();
                System.out.println("Categoría: " + id + " eliminada correctamente");
            }
        } else {
            System.out.println("No existe una categoría con ese id: " + id + ". No se eliminará...");
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        boolean resultado = false;

        // Actualiza el campo numproductos de la tabla categorias
        IQuery consultaCategorias = new CriteriaQuery(Categorias.class);
        Objects<Categorias> resultadosCategorias = bd.getObjects(consultaCategorias);
        while (resultadosCategorias.hasNext()) {
            Categorias categoria = resultadosCategorias.next();
            int id = categoria.getId();

            IQuery consultaProductos = new CriteriaQuery(Productos.class, Where.equal("idcategoria", id));
            Objects<Productos> resultadosProductos = bd.getObjects(consultaProductos);
            int numproductos = resultadosProductos.size();

            categoria.setNumproductos(numproductos);

            bd.store(categoria);
            bd.commit();

            System.out.println("Categoría: " + id + " actualizada correctamente");
            resultado = true;
        }

        return resultado;
    }

    @Override
    public Categorias ConsultarCategoria(int id) {
        Categorias categoria = null;

        IQuery consulta = new CriteriaQuery(Categorias.class, Where.equal("_id", id));
        Objects<Categorias> resultados = bd.getObjects(consulta);

        if (resultados.hasNext()) {
            categoria = resultados.next();
        } else {
            System.out.println("No existe una categoría con ese id: " + id);
        }

        return categoria;
    }

    @Override
    public ArrayList<Categorias> TodasLasCategorias() {
        ArrayList<Categorias> categorias = new ArrayList<>();

        IQuery consulta = new CriteriaQuery(Categorias.class);
        Objects<Categorias> resultados = bd.getObjects(consulta);

        while (resultados.hasNext()) {
            categorias.add(resultados.next());
        }

        return categorias;
    }

}
