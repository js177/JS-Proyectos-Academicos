//~author: JONATAN SEGURA

public class Coordenada{
	private int grados;
	private int minutos;
	private char direccion;
	private double gps;
	
	/*Constructor que inicializa las variables con los valores pasados por parametro.*/
	public Coordenada(int g, int m, char p){
		grados=g;
		minutos=m;
		direccion=p;
		gps=grados+(minutos/60.0);
		if(direccion=='S' || direccion=='O'){
			gps=-1*gps;
		}
	}

	/*Metodos encargados para devolver los valores de las variables.*/
	public int getGrados(){
		return grados;
	}

	public int getMinutos(){
		return minutos;
	}

	public char getDireccion(){
		return direccion;
	}
	
	public double getGps(){
		return gps;
	}
}
