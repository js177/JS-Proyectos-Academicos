//~author: JONATAN SEGURA
import java.util.*;
import java.text.*;

public class PLoc implements Comparable<PLoc>{
	private String continente;
	private String pais;
	private String ciudad;
	private Coordenada latitud;
	private Coordenada longitud;
	private ArrayList<Double> gps;
	
	/*Constructor que inicializa las variables a su valor por defecto (null).*/
	public PLoc(){
		gps=new ArrayList<>(2);
		continente="";
		pais="";
		ciudad="";
	}

	/*Constructor que inicializa las variables con los valores pasados por parametro.*/
	public PLoc(String c, String p, String l){
		gps=new ArrayList<>(2);
		continente=c;
		pais=p;
		ciudad=l;
	}

	/*Metodo encargado de actualizar el objeto "latitud", de tipo Coordenada, de la clase.
	Solo se produce en caso de que no exista previamente y que de los datos del objeto pasado por parámetro sean correctos.*/
	public boolean setLatitud(Coordenada p){
		boolean latitudActualizada=false;
		if(latitud==null){
			if((p.getGrados()>=0 && p.getGrados()<=90) && (p.getMinutos()>=0 && p.getMinutos()<=59) && (p.getDireccion()=='N' || p.getDireccion()=='S')){
				latitud=p;
				if(gps.size()==0){
					gps.add(latitud.getGps());
					gps.add(0.0);
				}
				else{
					gps.set(0,latitud.getGps());
				}
				latitudActualizada=true;
			}
		}
		return latitudActualizada;
	}
	/*Metodo encargado de actualizar el objeto "longitud", de tipo Coordenada, de la clase.
	Solo se produce en caso de que no exista previamente y que de los datos del objeto pasado por parámetro sean correctos.*/
	public boolean setLongitud(Coordenada p){
		boolean longitudActualizada=false;
		if(longitud==null){
			if((p.getGrados()>=0 && p.getGrados()<=180) && (p.getMinutos()>=0 && p.getMinutos()<=59) && (p.getDireccion()=='O' || p.getDireccion()=='E')){
				longitud=p;
				if(gps.size()==0){
					gps.add(0.0);
					gps.add(longitud.getGps());
				}
				else{
					gps.set(1,longitud.getGps());
				}
				longitudActualizada=true;
			}
		}
		return longitudActualizada;
	}

	/*Metodo encargado de comparar dos objetos de tipo PLoc y establecer el orden entre estos.
	Usa para dicha comparacion su variable "longitud".*/
	public int compareTo(PLoc p){
		int resultadoComparar;
		int ciudadComparadas=ciudad.compareTo(p.getCiudad());
		if(longitud.getGps()<p.getLongitud().getGps() || longitud.getGps()==p.getLongitud().getGps() && ciudadComparadas<0){
			resultadoComparar=-1;
		}
		else if(longitud.getGps()>p.getLongitud().getGps() || longitud.getGps()==p.getLongitud().getGps() && ciudadComparadas>0){
			resultadoComparar=1;
		}
		else{
			resultadoComparar=0;
		}
		return resultadoComparar;
	}

	/*Metodos encargados para devolver los valores de las variables.*/
	public String getContinente(){
		return continente;
	}

	public String getPais(){
		return pais;
	}

	public String getCiudad(){
		return ciudad;
	}

	public Coordenada getLatitud(){
		return latitud;
	}

	public Coordenada getLongitud(){
		return longitud;
	}

	public ArrayList<Double> getGps(){
		return gps;
	}

	/*Metodo encargado de mostrar por pantalla la informacion del objeto PLoc, usando grados para sus coordenadas.*/
	public void escribeInfoGrados(){
		if(continente==null){
			System.out.print("x - ");
		}
		else{
			System.out.print(continente+" - ");
		}
		
		if(pais==null){
			System.out.print("x - ");
		}
		else{
			System.out.print(pais+" - ");
		}
		
		if(ciudad==null){
			System.out.print("x - ");
		}
		else{
			System.out.print(ciudad+" - ");
		}
		
		
		System.out.println(latitud.getGrados()+" "+latitud.getMinutos()+" "+latitud.getDireccion()+" - "+
		longitud.getGrados()+" "+longitud.getMinutos()+" "+longitud.getDireccion());
	}

	/*Metodo encargado de mostrar por pantalla la informacion del objeto PLoc, usando decimales para sus coordenadas.*/
	public void escribeInfoGps(){
		if(continente==null){
			System.out.print("x - ");
		}
		else{
			System.out.print(continente+" - ");
		}
		
		if(pais==null){
			System.out.print("x - ");
		}
		else{
			System.out.print(pais+" - ");
		}
		
		if(ciudad==null){
			System.out.print("x - ");
		}
		else{
			System.out.print(ciudad+" - ");
		}
		
		System.out.println(mrf(gps.get(0))+" - "+mrf(gps.get(1)));
	}
	
	/*Metodo auxiliar extra implementado para mostrar los decimales (con 2 cifras) de nuestras variables.*/
	private String mrf(double db){
		Locale lengua=new Locale("en");
		DecimalFormatSymbols chars=new DecimalFormatSymbols(lengua);
		DecimalFormat formato=new DecimalFormat("0.00", chars);
		return formato.format(db);
	} 
}
