//~author: JONATAN SEGURA
import java.util.*;
import java.io.*;

public class Arbol{
	private class Nodo{
		private PLoc pd;
		private Nodo no;
		private Nodo so;
		private Nodo ne;
		private Nodo se;
		
		/*Constructor que inicializa las variables a su valor por defecto (null).*/
		public Nodo(){
			no=null;
			so=null;
			ne=null;
			se=null;
		}

		/*Constructor que inicializa el objeto PLoc con el pasado por parametro, y el resto a su valor defecto (null).*/
		public Nodo(PLoc p){
			pd=p;
			no=null;
			so=null;
			ne=null;
			se=null;
		}
	}
	private Nodo pr;
	
	/*Constructor que inicializa las variables a su valor por defecto (null).*/
	public Arbol(){
		pr=null;
	}

	/*Metodo encargado de crear un objeto Nodo con el PLoc por parametro e introducirlo al arbol.
	Se basa para ello en el siguiente criterio:
	1- Eleccion de subarbol en funcion de la comparacion de la variable "longitud" del objeto PLoc pasado con la del
		Nodo actual.
	2- Si el subarbol elegido esta vacio se introduce el nodo, de lo contrario se repite recursivamente el proceso en
		el siguiente nivel del arbol.*/
	public boolean inserta(PLoc p){
		boolean insertado=false;
		insertado=metodoInserta(p,pr);
		return insertado;
	}

	/*Metodo encargado de la lectura de un fichero que contiene un arbol.
	Insertara la informacion leida en las respectivas variables para implementar dicho arbol.*/
	public void leeArbol(String f){
		FileReader fichero=null;
		BufferedReader lectura=null;
		
		try{
			fichero=new FileReader(f);
			lectura=new BufferedReader(fichero);
			String linea=lectura.readLine();
			
			while(linea!=null){
				String separador="#";
				String[] datos=linea.split(separador);
				PLoc nuevoObjeto=new PLoc(datos[0],datos[1],datos[2]);
				
				separador=" ";
				String[] datosLatitud=datos[3].split(separador);
				String[] datosLongitud=datos[4].split(separador);
				Coordenada nuevaLatitud=new Coordenada(Integer.parseInt(datosLatitud[0]),Integer.parseInt(datosLatitud[1]),datosLatitud[2].charAt(0));
				Coordenada nuevaLongitud=new Coordenada(Integer.parseInt(datosLongitud[0]),Integer.parseInt(datosLongitud[1]),datosLongitud[2].charAt(0));
				
				nuevoObjeto.setLatitud(nuevaLatitud);
				nuevoObjeto.setLongitud(nuevaLongitud);
				inserta(nuevoObjeto);
				linea=lectura.readLine();
			}
		}catch(IOException e){
			System.err.println(e);
			System.exit(0);
		}
		
		try{
			if(fichero!=null){
				fichero.close();
			}
			if(lectura!=null){
				lectura.close();
			}
		}catch(IOException ex){
			System.err.println(ex);
		}
	}

	/*Metodo encargado de indicar si el arbol esta vacio*/
	public boolean esVacio(){
		boolean estaVacio=false;
		if(pr==null){
			estaVacio=true;
		}
		return estaVacio;
	}

	/*Metodo encargado de buscar algun PLoc cuya variable "ciudad" coincida con el String pasado por parametro.*/
	public boolean ciudadEnArbol(String v){
		boolean encontradaCiudad=false;
		encontradaCiudad=CiudadEnArbol(v,pr);
		return encontradaCiudad;
	}

	/*Metodo encargado de buscar todas las ciudades asociadas a la variable "pais" del PLoc pasado por parametro.*/
	public TreeSet<String> getCiudades(PLoc p){
		TreeSet<String> ciudadesPais=new TreeSet<String>();
		ciudadesPais=GetCiudades(p,pr);
		return ciudadesPais;
	}

	/*Metodo encargado de buscar el objeto PLoc mas alejado en la direccion pasado por parametro (NO, NE, SO, SE):
	NO- Mayor latitud y menor longitud.
	NE- Mayor latitud y mayor longitud.
	SO- Menor latitud y menor longitud.
	SE- Menor latitud y mayor longitud.*/
	public PLoc busquedaLejana(String s){
		PLoc masLejana=null;
		masLejana=BusquedaLejana(s,pr);
		return masLejana;
	}

	/*Metodo encargado en devolver los nombres de las ciudades de los PLoc del arbol, siguiendo el algoritmo de recorrido inorden.
	El recorrido inorden tiene el siguiente orden de lectura de los Nodos: no, so, Raiz, ne, se*/
	public ArrayList<String> recorridoInorden(){
		ArrayList<String> ciudadesInorden=new ArrayList<>();
		ciudadesInorden=metodoInorden(pr,ciudadesInorden);
		return ciudadesInorden;
	}
	
	/*Metodo encargado en devolver los nombres de las ciudades de los PLoc que se encuentran en las hojas del arbol.*/
	public TreeSet<String> hojas(){
		TreeSet<String> ciudadesHojas=new TreeSet<String>();
		ciudadesHojas=getHojas(pr);
		return ciudadesHojas;
	}

	/*Metodo encargado en devolver los objetos PLoc cuya variable "continente" coincida con el String pasado por parametro.*/
	public TreeSet<PLoc> getContinente(String s){
		TreeSet<PLoc> localidadesContinente=new TreeSet<PLoc>();
		localidadesContinente=GetContinente(s,pr);
		return localidadesContinente;
	}

	/*Metodos auxiliares extra implementados para calculos en los metodos anteriores o para realizar sus recursividades.*/
	private boolean metodoInserta(PLoc p, Nodo nodo){
		boolean insertado=false;
		Nodo nuevoNodo=new Nodo(p);
		if(pr==null){
			pr=nuevoNodo;
			insertado=true;
		}
		else{
			if(p.getGps().get(1)<nodo.pd.getGps().get(1)){
				if(p.getGps().get(0)<nodo.pd.getGps().get(0)){
					if(nodo.so==null){
						nodo.so=nuevoNodo;
					}
					else{
						metodoInserta(p,nodo.so);
					}
				}
				else{
					if(nodo.no==null){
						nodo.no=nuevoNodo;
					}
					else{
						metodoInserta(p,nodo.no);
					}
				}
				insertado=true;
			}
			else{
				if(p.getGps().get(0)<nodo.pd.getGps().get(0)){
					if(nodo.se==null){
						nodo.se=nuevoNodo;
					}
					else{
						metodoInserta(p,nodo.se);
					}
				}
				else{
					if(nodo.ne==null){
						nodo.ne=nuevoNodo;
					}
					else{
						metodoInserta(p,nodo.ne);
					}
				}
				insertado=true;
			}
		}
		return insertado;
	}

	private boolean CiudadEnArbol(String v, Nodo nodo){
		boolean encontradaCiudad=false;
		if(v!="" && esVacio()==false){
			if(nodo.pd.getCiudad().equalsIgnoreCase(v)){
				encontradaCiudad=true;
			}
			else{
				if(nodo.no!=null){
					encontradaCiudad=CiudadEnArbol(v,nodo.no);
				}
				if(nodo.so!=null && encontradaCiudad!=true){
					encontradaCiudad=CiudadEnArbol(v,nodo.so);
				}
				if(nodo.ne!=null && encontradaCiudad!=true){
					encontradaCiudad=CiudadEnArbol(v,nodo.ne);
				}
				if(nodo.se!=null && encontradaCiudad!=true){
					encontradaCiudad=CiudadEnArbol(v,nodo.se);
				}
			}
		}
		return encontradaCiudad;
	}

	private TreeSet<String> GetCiudades(PLoc p, Nodo nodo){
		TreeSet<String> ciudadesPais=new TreeSet<String>();
		if(p.getPais()!="" && esVacio()==false){
			if(nodo.pd.getPais().equalsIgnoreCase(p.getPais())){
				ciudadesPais.add(nodo.pd.getCiudad());
			}
			if(nodo.no!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=GetCiudades(p,nodo.no);
				if(aux.isEmpty()==false){
					ciudadesPais.addAll(aux);
				}
			}
			if(nodo.so!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=GetCiudades(p,nodo.so);
				if(aux.isEmpty()==false){
					ciudadesPais.addAll(aux);
				}
			}
			if(nodo.ne!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=GetCiudades(p,nodo.ne);
				if(aux.isEmpty()==false){
					ciudadesPais.addAll(aux);
				}
			}
			if(nodo.se!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=GetCiudades(p,nodo.se);
				if(aux.isEmpty()==false){
					ciudadesPais.addAll(aux);
				}
			}
		}
		return ciudadesPais;
	}

	private double distanciaEuclidea(ArrayList<Double> d1, ArrayList<Double> d2){
		double distancia;
		distancia=Math.sqrt((Math.pow(Math.abs(d1.get(0)-d2.get(0)),2)+Math.pow(Math.abs(d1.get(1)-d2.get(1)),2)));
		return distancia;
	}

	private PLoc BusquedaLejana(String s, Nodo nodo){
		PLoc masLejana=null;
		if((s.equalsIgnoreCase("NO") || s.equalsIgnoreCase("SO") || s.equalsIgnoreCase("NE") || s.equalsIgnoreCase("SE")) && esVacio()==false){
			ArrayList<Double> coorExtremas=new ArrayList<>(2);
			if(s.equalsIgnoreCase("NO")){
				coorExtremas.add(90.0);
				coorExtremas.add(-180.0);
			}
			else if(s.equalsIgnoreCase("SO")){
				coorExtremas.add(-90.0);
				coorExtremas.add(-180.0);
			}
			else if(s.equalsIgnoreCase("NE")){
				coorExtremas.add(90.0);
				coorExtremas.add(180.0);
			}
			else{
				coorExtremas.add(-90.0);
				coorExtremas.add(180.0);
			}
			double distLejana;
			distLejana=distanciaEuclidea(nodo.pd.getGps(),coorExtremas);
			masLejana=nodo.pd;
			if(nodo.no!=null){
				PLoc aux=new PLoc();
				aux=BusquedaLejana(s,nodo.no);
				if(distanciaEuclidea(aux.getGps(),coorExtremas)<distLejana){
					distLejana=distanciaEuclidea(aux.getGps(),coorExtremas);
					masLejana=aux;
				}
			}
			if(nodo.so!=null){
				PLoc aux=new PLoc();
				aux=BusquedaLejana(s,nodo.so);
				if(distanciaEuclidea(aux.getGps(),coorExtremas)<distLejana){
					distLejana=distanciaEuclidea(aux.getGps(),coorExtremas);
					masLejana=aux;
				}
			}
			if(nodo.ne!=null){
				PLoc aux=new PLoc();
				aux=BusquedaLejana(s,nodo.ne);
				if(distanciaEuclidea(aux.getGps(),coorExtremas)<distLejana){
					distLejana=distanciaEuclidea(aux.getGps(),coorExtremas);
					masLejana=aux;
				}
			}
			if(nodo.se!=null){
				PLoc aux=new PLoc();
				aux=BusquedaLejana(s,nodo.se);
				if(distanciaEuclidea(aux.getGps(),coorExtremas)<distLejana){
					distLejana=distanciaEuclidea(aux.getGps(),coorExtremas);
					masLejana=aux;
				}
			}
		}
		return masLejana;
	}

	private TreeSet<String> getHojas(Nodo nodo){
		TreeSet<String> ciudadesHojas=new TreeSet<String>();
		if(esVacio()==false){
			if(nodo.no==null && nodo.so==null && nodo.ne==null && nodo.se==null){
				ciudadesHojas.add(nodo.pd.getCiudad());
			}
			if(nodo.no!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=getHojas(nodo.no);
				if(aux.isEmpty()==false){
					ciudadesHojas.addAll(aux);
				}
			}
			if(nodo.so!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=getHojas(nodo.so);
				if(aux.isEmpty()==false){
					ciudadesHojas.addAll(aux);
				}
			}
			if(nodo.ne!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=getHojas(nodo.ne);
				if(aux.isEmpty()==false){
					ciudadesHojas.addAll(aux);
				}
			}
			if(nodo.se!=null){
				TreeSet<String> aux=new TreeSet<String>();
				aux=getHojas(nodo.se);
				if(aux.isEmpty()==false){
					ciudadesHojas.addAll(aux);
				}
			}
		}
		return ciudadesHojas;
	}

	private TreeSet<PLoc> GetContinente(String s, Nodo nodo){
		TreeSet<PLoc> localidadesContinente=new TreeSet<PLoc>();
		if(s!="" && esVacio()==false){
			if(nodo.pd.getContinente().equalsIgnoreCase(s)){
				localidadesContinente.add(nodo.pd);
			}
			if(nodo.no!=null){
				TreeSet<PLoc> aux=new TreeSet<PLoc>();
				aux=GetContinente(s,nodo.no);
				if(aux.isEmpty()==false){
					localidadesContinente.addAll(aux);
				}
			}
			if(nodo.so!=null){
				TreeSet<PLoc> aux=new TreeSet<PLoc>();
				aux=GetContinente(s,nodo.so);
				if(aux.isEmpty()==false){
					localidadesContinente.addAll(aux);
				}
			}
			if(nodo.ne!=null){
				TreeSet<PLoc> aux=new TreeSet<PLoc>();
				aux=GetContinente(s,nodo.ne);
				if(aux.isEmpty()==false){
					localidadesContinente.addAll(aux);
				}
			}
			if(nodo.se!=null){
				TreeSet<PLoc> aux=new TreeSet<PLoc>();
				aux=GetContinente(s,nodo.se);
				if(aux.isEmpty()==false){
					localidadesContinente.addAll(aux);
				}
			}
		}
		return localidadesContinente;
	}

	private ArrayList<String> metodoInorden(Nodo nodo, ArrayList<String> ciudadesInorden){
		if(nodo!=null){
			metodoInorden(nodo.no,ciudadesInorden);
			
			metodoInorden(nodo.so,ciudadesInorden);
			
			ciudadesInorden.add(nodo.pd.getCiudad());
			
			metodoInorden(nodo.ne,ciudadesInorden);
			
			metodoInorden(nodo.se,ciudadesInorden);
		}
		return ciudadesInorden;
	}

	public ArrayList<PLoc> plocsInorden(){
		ArrayList<PLoc> plocs=new ArrayList<>();
		plocs=metodoPlocsInorden(pr,plocs);
		return plocs;
	}
	
	private ArrayList<PLoc> metodoPlocsInorden(Nodo nodo, ArrayList<PLoc> plocs){
		if(nodo!=null){
			metodoPlocsInorden(nodo.no,plocs);
			
			metodoPlocsInorden(nodo.so,plocs);
			
			plocs.add(nodo.pd);
			
			metodoPlocsInorden(nodo.ne,plocs);
			
			metodoPlocsInorden(nodo.se,plocs);
		}
		return plocs;
	}
}
