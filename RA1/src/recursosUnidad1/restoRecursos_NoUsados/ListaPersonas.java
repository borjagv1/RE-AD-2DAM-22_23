package recursosUnidad1.restoRecursos_NoUsados;


import java.util.ArrayList;
import java.util.List;

import recursosUnidad1.ficherosObjetos.Persona;
public class ListaPersonas {
	
    private List<Persona> lista = new ArrayList<Persona>();
    
    public ListaPersonas(){    	
    }

    public void add(Persona per) {
            lista.add(per);
    }
   
   public List<Persona> getListaPersonas() {
            return lista;
    }
}

