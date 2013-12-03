package practica4;

import java.util.ArrayList;
import java.util.List;

public class Rectangle {
		//coordenadas reales
		private double latitud;
		private double longitud;
		//coordenadas adaptadas
		private double altura;
		private double anchura;
		
		private List<GasStation> listaGasolineras;
		
		public Rectangle(float latitud,float longitud){
			this.listaGasolineras=new ArrayList<GasStation>();
			this.latitud=latitud;
			this.longitud=longitud;
			this.altura=latitud-27.70523;
			this.anchura=longitud+18.01194;
		}
		
		public double getLatitud() {
			return latitud;
		}
		public void setLatitud(double latitud) {
			this.latitud = latitud;
		}
		public double getLongitud() {
			return longitud;
		}
		public void setLongitud(double longitud) {
			this.longitud = longitud;
		}
		public double getAltura() {
			return altura;
		}
		public void setAltura(double altura) {
			this.altura = altura;
		}
		public double getAnchura() {
			return anchura;
		}
		public void setAnchura(double anchura) {
			this.anchura = anchura;
		}
		
		
}
