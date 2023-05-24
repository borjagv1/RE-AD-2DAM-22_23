import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

public class CREABD_GUERRAVALENCIABORJA {

    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "CAFETERIA";
        String password = "CAFETERIA";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<Platos> platosList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("name");
                float precio = resultSet.getFloat("price");
                String nombrecategoria = obtenerCategoria(connection, resultSet.getString("category_id"));
                List<Alergenos> alergenosList = obtenerAlergenosPorPlato(connection, id);

                Platos plato = new Platos(id, nombre, precio, nombrecategoria, alergenosList);

                plato.setAlerg(alergenosList);

                platosList.add(plato);
            }

            ODB db = ODBFactory.open("Cafeteria.neo");
            for (Platos plato : platosList) {
                db.store(plato);
            }
            db.close();
            System.out.println("Base de datos creada correctamente");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String obtenerCategoria(Connection connection, String categoriaId) {
        String categoria = "";
        String query = "SELECT name FROM categories WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, categoriaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categoria = resultSet.getString("name");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

    private static List<Alergenos> obtenerAlergenosPorPlato(Connection connection, int platoId) throws SQLException {
        List<Alergenos> alergenosList = new ArrayList<>();

        String query = "SELECT a.id, a.name FROM allergens a INNER JOIN allergens_products ap ON a.id = ap.allergen_id WHERE ap.product_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, platoId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int alergenoId = resultSet.getInt("id");
            String nombre = resultSet.getString("name");

            Alergenos alergeno = new Alergenos(alergenoId, nombre);

            boolean existe = false;
            for (Alergenos existingAlergeno : alergenosList) {
                if (existingAlergeno.getId() == alergeno.getId()) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                alergenosList.add(alergeno);
            }
        }

        resultSet.close();
        statement.close();

        return alergenosList;
    }

}// class

// clase Alergenos
class Alergenos {

    private int id;
    private String nombre;

    public Alergenos(int id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public Alergenos() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

// clase Platos
class Platos {

    private int id;
    private String nombre;
    private float precio;
    private String nombrecategoria;
    private List<Alergenos> alerg;

    public Platos() {
        super();
    }

    public Platos(int id, String nombre, float precio, String nombrecategoria, List<Alergenos> alerg) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.nombrecategoria = nombrecategoria;
        this.alerg = alerg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public List<Alergenos> getAlerg() {
        return alerg;
    }

    public void setAlerg(List<Alergenos> alerg) {
        this.alerg = alerg;
    }

}
