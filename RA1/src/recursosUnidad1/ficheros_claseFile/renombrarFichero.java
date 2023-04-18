package recursosUnidad1.ficheros_claseFile;
import java.io.File;

public class renombrarFichero {

	public static void main(String[] args) {
		 File f = new File("D:\\ADAT\\UNI1\\VerInf.java");  
		 File f2=new File("D:\\ADAT\\VerInf2.java");
		 f.renameTo(f2);
	}

}
