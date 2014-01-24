package practica4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		/*HashTableMap<Integer,String> tabla = new HashTableMap<Integer,String>(5);
		tabla.put(1, "hola");
		tabla.put(2, "adios");
		*/
		/*BufferedReader in;
		try {
			//C:\Users\Aitor\Dropbox\workspace\practicaEDA\src\practica4\eess_GOA_30102013.csv
			File archivo = new File("eess_GOA_30102013.csv");
			if(archivo.exists()){
				in = new BufferedReader(
			        //new InputStreamReader(oracle.openStream()));
					new FileReader(archivo));
				in.readLine();
				in.readLine();
				String linea = in.readLine();
				float lo;
				float la;
				while(linea!=null){
					String[] a =linea.split(";");
					//System.out.println(a[0]+"  "+a[1]);
					lo=Float.parseFloat(a[0]);
					la=Float.parseFloat(a[1]);
					System.out.println(lo+"  "+la);
					//System.out.println(linea);
					linea=in.readLine();
				}
			}else
				System.out.println("NO VEO");
		} catch (FileNotFoundException e) {
			System.err.println("NO TIRA");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		Rectangle a = new Rectangle();
	}
	
}
