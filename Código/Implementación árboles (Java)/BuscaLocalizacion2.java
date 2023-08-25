//~author: JONATAN SEGURA
import java.util.*;
import java.io.*;

public class BuscaLocalizacion2{
	/*Detectamos los parametros de la aplicacion pasados por parametro y actuamos en funcion del numero de ellos.
	Tras esto, abrimos el fichero pasado y lo leemos para crear el arbol.
	En funcion del numero de parametros detectados, tenemos dos busquedas de datos distintas:
	4 parametros- Si hay 4 parametros significa que debemos realizar una busqueda por latitud y longitud, en el rango
				especificado por los parametros pasados, mostrando todos los datos de las ciudades de los objetos PLoc
				dentro de dicho rango.
	2 parametros- Si hay 2 parametros significa que debemos realizar una busqueda por pais, mostrando todas las ciudades
				en el arbol correspondientes a ese pais pasado por parametro.*/
	public static void main(String args[]){
		if(args.length==4){
			String nomFichero=args[0];
			Arbol nuevoArbol=new Arbol();
			nuevoArbol.leeArbol(nomFichero);
			busquedaPLocs(nuevoArbol,Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]));
			
		}
		else if(args.length==2){
			String nomFichero=args[0];
			Arbol nuevoArbol=new Arbol();
			nuevoArbol.leeArbol(nomFichero);
			busquedaPais(nuevoArbol,args[1]);
		}
	}
	
	/*Metodo encargado de la busqueda en caso de haber 4 parametros detectados.*/
	private static void busquedaPLocs(Arbol arbol, double latitud, double longitud, double rango){
		ArrayList<PLoc> plocsEncontrados=new ArrayList<>();
		ArrayList<PLoc> plocsArbol=new ArrayList<>();
		ArrayList<Double> rangoLatitud=new ArrayList<>(2);
		ArrayList<Double> rangoLongitud=new ArrayList<>(2);
		rangoLatitud.add(latitud-rango);
		rangoLatitud.add(latitud+rango);
		rangoLongitud.add(longitud-rango);
		rangoLongitud.add(longitud+rango);
		
		plocsArbol=arbol.plocsInorden();
		for(int i=0;i<plocsArbol.size();i++){
			if((plocsArbol.get(i).getGps().get(0)>=rangoLatitud.get(0) && plocsArbol.get(i).getGps().get(0)<=rangoLatitud.get(1)) && (plocsArbol.get(i).getGps().get(1)>=rangoLongitud.get(0) && plocsArbol.get(i).getGps().get(1)<=rangoLongitud.get(1))){
				plocsEncontrados.add(plocsArbol.get(i));
			}
		}
		for(int j=0;j<plocsEncontrados.size();j++){
			for(int k=j;k<plocsEncontrados.size();k++){
				if(plocsEncontrados.get(j).compareTo(plocsEncontrados.get(k))==1){
					PLoc aux=new PLoc();
					aux=plocsEncontrados.get(j);
					plocsEncontrados.set(j,plocsEncontrados.get(k));
					plocsEncontrados.set(k,aux);
				}
			}
		}
		if(plocsEncontrados.size()==0){
			System.out.println("NO HAY SALIDA");
		}
		else{
			for(int t=0;t<plocsEncontrados.size();t++){
				plocsEncontrados.get(t).escribeInfoGps();
			}
		}
	}
	
	/*Metodo encargado de la busqueda en caso de haber 2 parametros detectados.*/
	private static void busquedaPais(Arbol arbol, String paisBuscado){
		ArrayList<PLoc> plocsEncontrados=new ArrayList<>();
		ArrayList<PLoc> plocsArbol=new ArrayList<>();
		
		plocsArbol=arbol.plocsInorden();
		for(int i=0;i<plocsArbol.size();i++){
			if(plocsArbol.get(i).getPais().equalsIgnoreCase(paisBuscado)==true){
				plocsEncontrados.add(plocsArbol.get(i));
			}
		}
		if(plocsEncontrados.size()==0){
			System.out.println("NO EXISTE EL PAIS EN LA BASE DE DATOS");
		}
		else{
			for(int j=0;j<plocsEncontrados.size();j++){
				for(int k=j;k<plocsEncontrados.size();k++){
					if(plocsEncontrados.get(j).getCiudad().compareTo(plocsEncontrados.get(k).getCiudad())==1){
						PLoc aux=new PLoc();
						aux=plocsEncontrados.get(j);
						plocsEncontrados.set(j,plocsEncontrados.get(k));
						plocsEncontrados.set(k,aux);
					}
				}
			}
			System.out.print(paisBuscado+" ("+plocsEncontrados.size()+"): ");
			for(int t=0;t<plocsEncontrados.size();t++){
				if(t+1!=plocsEncontrados.size()){
					System.out.print(plocsEncontrados.get(t).getCiudad());
					System.out.print(" - ");
				}
				else{
					System.out.println(plocsEncontrados.get(t).getCiudad());
				}
			}
		}
	}
}
