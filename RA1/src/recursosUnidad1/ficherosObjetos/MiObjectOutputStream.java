package recursosUnidad1.ficherosObjetos;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Redefinici�n de la clase ObjectOuputStream para que no escriba una cabecera
 * al inicio del Stream.
 * @author Javier Abell�n.
 * Para no tener problemas con las cabeceras de los objetos y evitar el error  
 * StreamCorruptedException, creamos una clase con nuestro propio 
 * ObjectOutputStream, heredando del original y redefiniendo el m�todo 
 * writeStreamHeader() vac�o, para que no haga nada.
 */
public class MiObjectOutputStream extends ObjectOutputStream
{
    /** Constructor que recibe OutputStream */
    public MiObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    /** Constructor sin par�metros */
    protected MiObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }

    /** Redefinici�n del m�todo de escribir la cabecera para que no haga nada. */
    protected void writeStreamHeader() throws IOException
    {
    }

}
