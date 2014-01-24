package practica4;

public class GasStation {
	//coordenadas reales
			private double latitud;
			private double longitud;
			
			//coordenadas adaptadas
			private double altura;
			private double anchura;
			
			private String descripcion;
			
			
			public GasStation(double lon, double lat, String desc, double virtualLon, double virtualLat){
				latitud=lat;
				longitud=lon;
				descripcion=desc;
				altura=latitud-Math.abs(virtualLat);
				anchura=longitud+Math.abs(virtualLon);
			}
			public String getDescription(){
				return descripcion;
			}
			public void setDescription(String d){
				descripcion=d;
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
