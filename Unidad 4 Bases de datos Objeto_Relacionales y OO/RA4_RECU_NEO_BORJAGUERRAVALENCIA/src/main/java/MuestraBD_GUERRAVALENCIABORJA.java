import java.text.DecimalFormat;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;

public class MuestraBD_GUERRAVALENCIABORJA {
    public static void main(String[] args) {
        ODB db = ODBFactory.open("Cafeteria.neo");

        // Obtener todos los platos
        IQuery query = db.criteriaQuery(Platos.class);
        query.orderByAsc("id");

        Objects<Platos> platos = db.getObjects(Platos.class);

        System.out.println("Listado de platos:");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        // Creo cabecera para mostrar los datos
        System.out.printf("%-8s %-18s %-9s %-10s\n", "ID", "Nombre", "Precio", "Categoria");
        System.out.println("=======  =================  ========  =========");
        while (platos.hasNext()) {
            Platos plato = platos.next();
            System.out.printf("%-8d %-18s %-9s %-10s\n", plato.getId(), plato.getNombre(),
                    decimalFormat.format(plato.getPrecio()), plato.getNombrecategoria());

            // Verificar si el plato tiene alérgenos
            if (plato.getAlerg().isEmpty()) {
                System.out.println("-------------------");
                System.out.println("Este plato no tiene alérgenos.");
            } else {
                System.out.println("-------------------");
                System.out.println("\t Alérgenos:");
                System.out.println("=====================================================");
                for (Alergenos alergeno : plato.getAlerg()) {
                    System.out.printf("\t%-2d, %s\n", alergeno.getId(), alergeno.getNombre());
                }
            }

            System.out.println();
        }

        db.close();
    }
}
