package impl;

import java.util.ArrayList;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.ProductoDAO;
import datos.Alergenos;
import datos.AlergenosProductos;
import datos.Categorias;
import datos.PlatosMenus;
import datos.Productos;
import factory.NeodatisDAOFactory;
//query
import org.neodatis.odb.core.query.IQuery.*;

public class NeodatisProductosImpl implements ProductoDAO {
    static ODB bd = null;

    public NeodatisProductosImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public int InsertarProducto(Productos p) {
        int resultado = 0;

        // Comprobar si existe el producto en NEODATIS
        IQuery query = new CriteriaQuery(Productos.class, Where.equal("id", p.getId()));
        Objects<Productos> productos = bd.getObjects(query);
        if (!productos.isEmpty()) {
            System.out.println("Ya existe un producto con ese id: " + p.getId() + " No se insertará...");
            resultado = 1;
        } else {
            // Comprobar si existe la categoría
            query = new CriteriaQuery(Categorias.class, Where.equal("id", p.getIdcategoria()));
            Objects<Categorias> categorias = bd.getObjects(query);
            if (categorias.isEmpty()) {
                System.out.println("No existe una categoría con ese id: " + p.getIdcategoria() + " No se insertará...");
                resultado = 1;
            } else {
                bd.store(p);
                bd.commit();
                System.out.println("Producto: " + p.getId() + " insertado correctamente");
                resultado = 0;
            }
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {

        boolean resultado = false;

        // Actualizar de la tabla productos el campo numalergenos con el número de
        // alérgenos que tiene el producto.
        IQuery query = new CriteriaQuery(Productos.class);
        Objects<Productos> productos = bd.getObjects(query);
        for (Productos producto : productos) {
            int id = producto.getId();
            String nombrealergenos = producto.getNombrealergenos();
            int numalergenos = 0;
            if (nombrealergenos != null && !nombrealergenos.isEmpty()) {
                String[] alergenos = nombrealergenos.split(",");
                numalergenos = alergenos.length;
            }
            producto.setNumalergenos(numalergenos);
            bd.store(producto);
            bd.commit();
            System.out.println("Producto: " + id + " actualizado correctamente");
            resultado = true;
        }
        // ACTUALIZAR EL CAMPO NOMBREALÉRGENOS CON EL NOMBRE DE LOS ALÉRGENOS QUE TIENE
        // EL PRODUCTO.
        IQuery query2 = new CriteriaQuery(Productos.class);
        Objects<Productos> productos2 = bd.getObjects(query2);
        for (Productos producto : productos2) {
            int id = producto.getId();
            int numalergenos = producto.getNumalergenos();
            String nombrealergenos = "";
            if (numalergenos > 0) {
                IQuery alergenosQuery = new CriteriaQuery(AlergenosProductos.class, Where.equal("idproduct", id));
                Objects<AlergenosProductos> alergenosProductos = bd.getObjects(alergenosQuery);
                for (AlergenosProductos alergenoProducto : alergenosProductos) {
                    int idalergeno = alergenoProducto.getIdalergeno();
                    IQuery alergenoQuery = new CriteriaQuery(Alergenos.class, Where.equal("_id", idalergeno));
                    Objects<Alergenos> alergenos = bd.getObjects(alergenoQuery);
                    if (!alergenos.isEmpty()) {
                        String nombre = alergenos.getFirst().getNombre();
                        nombrealergenos += nombre + ",";
                    }
                }
                if (nombrealergenos.length() > 1) {
                    nombrealergenos = nombrealergenos.substring(0, nombrealergenos.length() - 1);
                }
            }
            producto.setNombrealergenos(nombrealergenos);
            bd.store(producto);
            System.out.println("Producto: " + id + " actualizado correctamente");
            resultado = true;
        }
        // actualizar pvp de cada producto del tipo que coincida con "menu" compruebo
        // que existe
        IQuery query3 = new CriteriaQuery(Productos.class, Where.equal("tipo", "menu"));
        Objects<Productos> productos3 = bd.getObjects(query3);
        for (Productos producto : productos3) {
            int id = producto.getId();
            double pvp = 0;

            IQuery platosMenusQuery = new CriteriaQuery(PlatosMenus.class, Where.equal("idmenu", id));
            Objects<PlatosMenus> platosMenus = bd.getObjects(platosMenusQuery);
            for (PlatosMenus platoMenu : platosMenus) {
                int idplato = platoMenu.getIdplato();

                IQuery platoQuery = new CriteriaQuery(Productos.class, Where.equal("id", idplato));
                Objects<Productos> platos = bd.getObjects(platoQuery);
                if (!platos.isEmpty()) {
                    double pvpplato = platos.getFirst().getPvp();
                    pvp += pvpplato;
                }
            }

            producto.setPvp(pvp);
            bd.store(producto);
            System.out.println("Producto: " + id + " actualizado correctamente");
            resultado = true;
        }

        return resultado;
    }

    @Override
    public boolean InsertarMenu(int id_menu, int id_plato) {
        /*
         * //Insertar menus, id_menu + id_plato no se deben repetir
         * //el orden debe empieza en 1 y se suma 1 para los platos de un menú
         */
        boolean resultado = false;

        // Comprobar si existe el menú
        IQuery menuQuery = new CriteriaQuery(Productos.class, Where.equal("id", id_menu));
        Objects<Productos> menuResult = bd.getObjects(menuQuery);
        if (!menuResult.isEmpty()) {
            // Comprobar si existe el plato
            IQuery platoQuery = new CriteriaQuery(Productos.class, Where.equal("id", id_plato));
            Objects<Productos> platoResult = bd.getObjects(platoQuery);
            if (!platoResult.isEmpty()) {
                // Comprobar si existe el plato en el menú haciendo dos consultas
                // Una para comprobar si existe el menú y otra para comprobar si existe el plato
                IQuery platosMenusQuery = new CriteriaQuery(PlatosMenus.class, Where.equal("idmenu", id_menu));
                IQuery platosMenusQuery2 = new CriteriaQuery(PlatosMenus.class, Where.equal("idplato", id_plato));

                Objects<PlatosMenus> platosMenusResult = bd.getObjects(platosMenusQuery);
                Objects<PlatosMenus> platosMenusResult2 = bd.getObjects(platosMenusQuery2);
                if (platosMenusResult.isEmpty() && platosMenusResult2.isEmpty()) {
                    // Insertar el plato en el menú
                    // el orden debe empieza en 1 y se suma 1 para los platos de un menú
                    IQuery ordenQuery = new CriteriaQuery(PlatosMenus.class, Where.equal("idmenu", id_menu));
                    int orden = bd.getObjects(ordenQuery).size() + 1;

                    PlatosMenus nuevoPlatoMenu = new PlatosMenus();
                    nuevoPlatoMenu.setIdmenu(id_menu);
                    nuevoPlatoMenu.setIdplato(id_plato);
                    nuevoPlatoMenu.setOrden(orden);

                    bd.store(nuevoPlatoMenu);
                    bd.commit();

                    System.out.println("Plato: " + id_plato + " insertado correctamente en el menú: " + id_menu);
                    resultado = true;
                } else {
                    System.out.println("El plato: " + id_plato + " ya existe en el menú: " + id_menu);
                    resultado = false;
                }
            } else {
                System.out.println("El plato: " + id_plato + " no existe");
                resultado = false;
            }
        } else {
            System.out.println("El menú: " + id_menu + " no existe");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public ArrayList<PlatosMenus> ConsultarMenu(int id_menu) {
        ArrayList<PlatosMenus> platos = new ArrayList<PlatosMenus>();
        IQuery consulta = new CriteriaQuery(PlatosMenus.class, Where.equal("idmenu", id_menu));
        Objects<PlatosMenus> resultados = bd.getObjects(consulta);
        while (resultados.hasNext()) {
            PlatosMenus plato = resultados.next();
            platos.add(plato);
        }
        return platos;
    }

    @Override
    public Productos ConsultarProducto(int id) {
        Productos producto = null;
        IQuery consulta = new CriteriaQuery(Productos.class, Where.equal("id", id));
        Objects<Productos> resultados = bd.getObjects(consulta);
        if (resultados.hasNext()) {
            producto = resultados.next();
        } else {
            System.out.println("El producto: " + id + " no existe");
        }
        return producto;
    }

    @Override
    public ArrayList<Productos> TodosLosProductos() {
        ArrayList<Productos> productos = new ArrayList<Productos>();
        IQuery consulta = new CriteriaQuery(Productos.class);
        Objects<Productos> resultados = bd.getObjects(consulta);
        while (resultados.hasNext()) {
            Productos producto = resultados.next();
            productos.add(producto);
        }
        if (productos.isEmpty()) {
            System.out.println("No hay productos en la base de datos");
        }
        return productos;
    }

	@Override
	public boolean EliminarPlatoMenu(int id_menu, int id_plato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean EliminarProductoCascada(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
