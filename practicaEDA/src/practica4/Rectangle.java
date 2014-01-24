package practica4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rectangle{
		private static final double virtual_Long=22.28358;
		private static final double virtual_Lat=16.02647;
		private static final double LIM_MIN_LON = -18.01194;
		private static final double LIM_MAX_LON = 4.27164;
		private static final double LIM_MIN_LAT = 27.70533;
		private static final double LIM_MAX_LAT = 43.7318;
		
		private HashTableMapSC<Double,GasStation> mapa;
		
		public Rectangle(){
			mapa= new HashTableMapSC<Double,GasStation>();
			
			BufferedReader in;
			try {
				
				File archivo = new File("eess_GOA_30102013.csv");
				if(archivo.exists()){
					in = new BufferedReader(
				       
						new FileReader(archivo));
					in.readLine();
					in.readLine();
					String linea = in.readLine();
					while(linea!=null){
						String[] a =linea.split(";");
						GasStation gasolinera = new GasStation(Double.parseDouble(a[0]),Double.parseDouble(a[1]),a[2],LIM_MIN_LON,LIM_MIN_LAT);
						//System.out.println(gasolinera.getLongitud()+"  "+gasolinera.getLatitud());
						mapa.put(gasolinera.getAnchura()*gasolinera.getAltura(), gasolinera);
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
		}
		
		public	Iterable<GasStation>serviceQuery(double	x,double y){
			/*
			 * Una vez claro que tengas la tabla hash, hay que acceder con las coordenadas y coger esa lista y devolverla.
			 */
			double cordX = x/100;
			double cordY = y/100;
			
			return null;
		}
		public	Iterable<GasStation>	serviceQuery(double	x,	double	y,	double rad){
			
			return null;
		}
		public	double	serviceQuery(double	x,	double	y,	String	gas){
			return -1;
		}
}
